/*
 * This is the latest source code of Flower Mimics.
 * Minecraft version: 1.19.2.
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

package com.natamus.flowermimics.events;

import com.natamus.collective.functions.CompareBlockFunctions;
import com.natamus.flowermimics.config.ConfigHandler;
import com.natamus.flowermimics.util.Reference;
import com.natamus.flowermimics.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;
import java.util.WeakHashMap;

public class MimicEvent {
	private static final WeakHashMap<Level, Boolean> wasItDay = new WeakHashMap<Level, Boolean>();
	public static void onLevelTick(ServerLevel level) {
		if (!ConfigHandler.resetMimicsBeforeNightTime) {
			return;
		}

		boolean isDay = level.isDay();
		if (!wasItDay.containsKey(level)) {
			wasItDay.put(level, isDay);
			Util.flowersChecked.put(level, new ArrayList<BlockPos>());
			Util.flowerIsAMimic.put(level, new ArrayList<BlockPos>());
		}

		if (!isDay && wasItDay.get(level)) {
			Util.resetFlowerMimics(level);
		}

		wasItDay.put(level, isDay);
	}

	public static void onPlayerTick(ServerLevel level, ServerPlayer player) {
		if (player.tickCount % ConfigHandler.checkForMimicFlowersDelayInTicks != 0) {
			return;
		}

		if (level.getLevelData().getDifficulty().equals(Difficulty.PEACEFUL)) {
			return;
		}

		if (ConfigHandler.onlyTransformMimicsAtNight && !level.isNight()) {
			return;
		}

		BlockPos playerPos = player.blockPosition();

		// TODO: Change transform range to check range, add particle effect and transform when close.
		int checkRange = ConfigHandler.checkForMimicAroundBlockRange;
		int transformRange = ConfigHandler.mimicTransformAroundPlayerRange;

		for (BlockPos aroundPos : BlockPos.betweenClosed(playerPos.getX()-transformRange, playerPos.getY()-3, playerPos.getZ()-transformRange, playerPos.getX()+transformRange, playerPos.getY()+3, playerPos.getZ()+transformRange)) {
			BlockState aroundState = level.getBlockState(aroundPos);
			Block aroundBlock = aroundState.getBlock();

			if (CompareBlockFunctions.blockIsInRegistryHolder(aroundBlock, BlockTags.FLOWERS)) {
				if (Util.isFlowerAMimic(level, aroundPos.immutable())) {
					Util.transformFlower(level, player, aroundPos.immutable(), aroundBlock);
				}
			}
		}
	}

	public static void mobItemDrop(Level level, Entity entity, DamageSource damageSource) {
		if (level.isClientSide) {
			return;
		}

		Set<String> tags = entity.getTags();
		if (!tags.contains(Reference.MOD_ID + ".mimic")) {
			return;
		}

		String flowerTag = "";
		String locationTag = "";
		for (String tag : tags) {
			if (tag.startsWith(Reference.MOD_ID + ".flower.")) {
				flowerTag = tag;
			}
			else if (tag.startsWith(Reference.MOD_ID + ".location.")) {
				locationTag = tag;
			}
		}

		Vec3 mimicVec = entity.position();
		if (ConfigHandler.dropExtraItemsOnMimicDeath || ConfigHandler.dropFlowerItemOnMimicDeath) {
			Util.dropMimicItems(level, mimicVec, flowerTag);
		}

		if (ConfigHandler.replaceOriginalFlowerBlockOnMimicDeath || ConfigHandler.placeFlowerBlockWhereMimicDies) {
			Util.placeFlowerBlockOnMimicDeath(level, mimicVec, flowerTag, locationTag);
		}

		int extraExperience = ConfigHandler.extraExperienceOnMimicDeath;
		if (extraExperience > 0) {
			ExperienceOrb.award((ServerLevel)level, mimicVec, extraExperience);
		}
	}

	public static void onBonemeal(Level level, BlockPos blockPos, BlockState blockState, ItemStack itemStack) {
		if (!ConfigHandler.preventMimicsOnPlacedAndBoneMealFlowers) {
			return;
		}

		if (level.isClientSide) {
			return;
		}

		level.getServer().tell(new TickTask(level.getServer().getTickCount(), () -> {
			for (BlockPos aroundPos : BlockPos.betweenClosed(blockPos.getX() - 6, blockPos.getY(), blockPos.getZ() - 6, blockPos.getX() + 6, blockPos.getY() + 1, blockPos.getZ() + 6)) {
				Block aroundBlock = level.getBlockState(aroundPos).getBlock();
				if (Util.isFlower(aroundBlock)) {
					if (!Util.flowersChecked.get(level).contains(aroundPos)) {
						Util.flowersChecked.get(level).add(aroundPos.immutable());
					}
				}
			}
		}));
	}

	public static void onNeighbourNotice(Level level, BlockPos blockPos, BlockState blockState, EnumSet<Direction> notifiedSides, boolean forceRedstoneUpdate) {
		if (!ConfigHandler.preventMimicsOnPlacedAndBoneMealFlowers) {
			return;
		}

		if (level.isClientSide) {
			return;
		}

		Block block = blockState.getBlock();
		if (Util.isFlower(block)) {
			if (!Util.flowersChecked.get(level).contains(blockPos)) {
				Util.flowersChecked.get(level).add(blockPos.immutable());
			}
		}
	}
}
