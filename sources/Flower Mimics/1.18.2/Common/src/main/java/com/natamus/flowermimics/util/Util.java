/*
 * This is the latest source code of Flower Mimics.
 * Minecraft version: 1.18.2.
 *
 * Please don't distribute without permission.
 * For all Minecraft modding projects, feel free to visit my profile page on CurseForge or Modrinth.
 *  CurseForge: https://curseforge.com/members/serilum/projects
 *  Modrinth: https://modrinth.com/user/serilum
 *  Overview: https://serilum.com/
 *
 * If you are feeling generous and would like to support the development of the mods, you can!
 *  https://ricksouth.com/donate contains all the information. <3
 *
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

package com.natamus.flowermimics.util;

import com.natamus.collective.functions.HashMapFunctions;
import com.natamus.flowermimics.config.ConfigHandler;
import com.natamus.flowermimics.data.MimicData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class Util {
	public static final WeakHashMap<Level, List<BlockPos>> flowersChecked = new WeakHashMap<Level, List<BlockPos>>();
	public static final WeakHashMap<Level, List<BlockPos>> flowerIsAMimic = new WeakHashMap<Level, List<BlockPos>>();

	public static boolean isFlowerAMimic(Level level, BlockPos blockPos) {
		if (HashMapFunctions.computeIfAbsent(flowersChecked, level, k -> new ArrayList<BlockPos>()).contains(blockPos)) {
			return false;
		}
		if (HashMapFunctions.computeIfAbsent(flowerIsAMimic, level, k -> new ArrayList<BlockPos>()).contains(blockPos)) {
			return true;
		}
		flowersChecked.get(level).add(blockPos);

		boolean isMimic = Math.random() <= ConfigHandler.flowerIsMimicChance;
		if (isMimic) {
			flowerIsAMimic.get(level).add(blockPos);
		}

		return isMimic;
	}

	// SHOULD TRANSFORM FUNCTION

	public static boolean isFlower(Block block) {
		return block instanceof FlowerBlock || block instanceof TallFlowerBlock;
	}

	public static void transformFlower(Level level, Player player, BlockPos flowerPos, Block flowerBlock) {
		if (!flowerIsAMimic.get(level).contains(flowerPos)) {
			return;
		}

		EntityType<?> mimicEntityType = MimicData.getMimicFromFlower(flowerBlock);
		if (mimicEntityType == null) {
			flowerIsAMimic.get(level).remove(flowerPos);
			return;
		}

		Entity mimic = mimicEntityType.create(level);
		if (mimic == null) {
			return;
		}

		mimic.setPos(flowerPos.getX()+0.5, flowerPos.getY(), flowerPos.getZ()+0.5);
		mimic.addTag(Reference.MOD_ID + ".mimic");

		MutableComponent mimicNameComponent = flowerBlock.getName().append(" Mimic");
		mimic.setCustomName(mimicNameComponent.withStyle(ChatFormatting.GOLD));

		if (ConfigHandler.removeFlowerBlockOnMimicSpawn) {
			ResourceLocation resourceLocation = level.registryAccess().registryOrThrow(Registry.BLOCK.key()).getKey(flowerBlock);

			mimic.addTag(Reference.MOD_ID + ".flower." + resourceLocation);
			mimic.addTag(Reference.MOD_ID + ".location." + flowerPos.getX() + "," + flowerPos.getY() + "," + flowerPos.getZ());
		}

		if (ConfigHandler.removeFlowerBlockOnMimicSpawn) {
			if (flowerBlock instanceof TallFlowerBlock) {
				level.setBlock(flowerPos.immutable().above(), Blocks.AIR.defaultBlockState(), 3);
			}
			level.setBlock(flowerPos, Blocks.AIR.defaultBlockState(), 3);
		}

		if (ConfigHandler.showLightningOnMimicSpawn) {
			spawnLightning(level, flowerPos, player);
		}

		level.addFreshEntity(mimic);
	}

	public static void dropMimicItems(Level level, Vec3 mimicVec, String flowerTag) {
		dropMimicItems(level, mimicVec, flowerTag, false);
	}
	public static void dropMimicItems(Level level, Vec3 mimicVec, String flowerTag, boolean flowerOnly) {
		String flowerBlockRlString = flowerTag.split(".flower.")[1];

		ResourceLocation flowerBlockResourceLocation = new ResourceLocation(flowerBlockRlString);

		Block flowerBlock = level.registryAccess().registryOrThrow(Registry.BLOCK.key()).get(flowerBlockResourceLocation);
		if (flowerBlock != null) {
			if (ConfigHandler.dropExtraItemsOnMimicDeath && !flowerOnly) {
				ItemStack mimicItemDropStack = MimicData.getFlowerMimicDrop(flowerBlock);
				if (!mimicItemDropStack.getItem().equals(Items.AIR)) {
					level.addFreshEntity(new ItemEntity(level, mimicVec.x, mimicVec.y + 1, mimicVec.z, mimicItemDropStack));
				}
			}

			if (ConfigHandler.dropFlowerItemOnMimicDeath) {
				ItemStack flowerItemStack = new ItemStack(flowerBlock, 1);
				level.addFreshEntity(new ItemEntity(level, mimicVec.x, mimicVec.y+1, mimicVec.z, flowerItemStack));
			}
		}
	}

	public static void placeFlowerBlockOnMimicDeath(Level level, Vec3 mimicVec, String flowerTag, String locationTag) {
		BlockPos mimicPos = new BlockPos(mimicVec);
		String flowerBlockRlString = flowerTag.split(".flower.")[1];

		ResourceLocation flowerBlockResourceLocation = new ResourceLocation(flowerBlockRlString);
		Block flowerBlock = level.registryAccess().registryOrThrow(Registry.BLOCK.key()).get(flowerBlockResourceLocation);

		boolean flowerPlaced = false;

		if (flowerBlock != null) {
			if (ConfigHandler.placeFlowerBlockWhereMimicDies) {
				if (level.getBlockState(mimicPos).getBlock().equals(Blocks.AIR)) {
					flowersChecked.get(level).add(mimicPos.immutable());
					flowerIsAMimic.get(level).remove(mimicPos);

					BlockState flowerBlockState = flowerBlock.defaultBlockState();
					if (flowerBlock instanceof TallFlowerBlock) {
						TallFlowerBlock.placeAt(level, flowerBlockState, mimicPos, 3);
					}
					else {
						level.setBlock(mimicPos, flowerBlockState, 3);
					}

					if (ConfigHandler.showLightningOnFlowerBlockReturn) {
						spawnLightning(level, mimicPos, null);
					}

					flowerPlaced = true;
				}
			}

			if (ConfigHandler.replaceOriginalFlowerBlockOnMimicDeath && !locationTag.equals("")) {
				String rawCoordinates = locationTag.split(".location.")[1];
				String[] cSpl = rawCoordinates.split(",");

				try {
					BlockPos mimicSpawnBlockPos = new BlockPos(Integer.parseInt(cSpl[0]), Integer.parseInt(cSpl[1]), Integer.parseInt(cSpl[2]));

					if (level.getBlockState(mimicSpawnBlockPos).getBlock().equals(Blocks.AIR)) {
						flowersChecked.get(level).add(mimicSpawnBlockPos.immutable());
						flowerIsAMimic.get(level).remove(mimicSpawnBlockPos);

						BlockState flowerBlockState = flowerBlock.defaultBlockState();
						if (flowerBlock instanceof TallFlowerBlock) {
							TallFlowerBlock.placeAt(level, flowerBlockState, mimicSpawnBlockPos, 3);
						}
						else {
							level.setBlock(mimicSpawnBlockPos, flowerBlockState, 3);
						}

						if (ConfigHandler.showLightningOnFlowerBlockReturn) {
							spawnLightning(level, mimicSpawnBlockPos, null);
						}

						flowerPlaced = true;
					}
				} catch (NumberFormatException ignored) {
				}
			}
		}

		if (!flowerPlaced) {
			dropMimicItems(level, mimicVec, flowerTag, true);
		}
	}

    public static void spawnLightning(Level level, BlockPos pos, Player player) {
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
		if (lightningbolt == null) {
			return;
		}

        lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
		lightningbolt.getTags().add("visualonly");
        level.addFreshEntity(lightningbolt);

        if (player != null) {
            player.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 5.0F, 1.0F);
        }
    }

	public static void resetFlowerMimics(Level level) {
		flowersChecked.put(level, new ArrayList<BlockPos>());
		flowerIsAMimic.put(level, new ArrayList<BlockPos>());
	}
}