/*
 * This is the latest source code of Flower Mimics.
 * Minecraft version: 1.20.1.
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

package com.natamus.flowermimics.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.natamus.collective.functions.DataFunctions;
import com.natamus.collective.functions.ItemFunctions;
import com.natamus.flowermimics.util.Reference;
import com.natamus.flowermimics.util.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MimicData {
	public static List<Block> allFlowers = new ArrayList<Block>();
	private static HashMap<Block, EntityType<?>> flowerToMimic = new HashMap<Block, EntityType<?>>();
	private static HashMap<Block, ItemStack> flowerMimicDrops = new HashMap<Block, ItemStack>();

	public static EntityType<?> getMimicFromFlower(Block block) {
		if (!flowerToMimic.containsKey(block)) {
			return EntityType.ZOMBIE;
		}
		return flowerToMimic.get(block);
	}

	public static ItemStack getFlowerMimicDrop(Block block) {
		if (!flowerMimicDrops.containsKey(block)) {
			return new ItemStack(Blocks.AIR, 1);
		}
		return flowerMimicDrops.get(block).copy();
	}

	// Default Data
	private static final HashMap<Block, EntityType<?>> defaultFlowerToMimic = new HashMap<Block, EntityType<?>>();
	private static final HashMap<Block, ItemStack> defaultFlowerMimicDrops = new HashMap<Block, ItemStack>();

	public static void loadDefaultMimicData() {
		defaultFlowerToMimic.put(Blocks.ALLIUM, EntityType.ILLUSIONER);
		defaultFlowerToMimic.put(Blocks.AZURE_BLUET, EntityType.WANDERING_TRADER);
		defaultFlowerToMimic.put(Blocks.BLUE_ORCHID, EntityType.DROWNED);
		defaultFlowerToMimic.put(Blocks.CORNFLOWER, EntityType.PHANTOM);
		defaultFlowerToMimic.put(Blocks.DANDELION, EntityType.ZOMBIE_VILLAGER);
		defaultFlowerToMimic.put(Blocks.LILAC, EntityType.SHULKER);
		defaultFlowerToMimic.put(Blocks.LILY_OF_THE_VALLEY, EntityType.GHAST);
		defaultFlowerToMimic.put(Blocks.ORANGE_TULIP, EntityType.SNOW_GOLEM);
		defaultFlowerToMimic.put(Blocks.OXEYE_DAISY, EntityType.STRAY);
		defaultFlowerToMimic.put(Blocks.PEONY, EntityType.VINDICATOR);
		defaultFlowerToMimic.put(Blocks.PINK_TULIP, EntityType.SLIME);
		defaultFlowerToMimic.put(Blocks.POPPY, EntityType.CAVE_SPIDER);
		defaultFlowerToMimic.put(Blocks.RED_TULIP, EntityType.SPIDER);
		defaultFlowerToMimic.put(Blocks.ROSE_BUSH, EntityType.MAGMA_CUBE);
		defaultFlowerToMimic.put(Blocks.SUNFLOWER, EntityType.CREEPER);
		defaultFlowerToMimic.put(Blocks.TORCHFLOWER, EntityType.BLAZE);
		defaultFlowerToMimic.put(Blocks.WHITE_TULIP, EntityType.SKELETON);
		defaultFlowerToMimic.put(Blocks.WITHER_ROSE, EntityType.WITHER_SKELETON);
	}

	public static void loadDefaultFlowerMimicDrops() {
		defaultFlowerMimicDrops.put(Blocks.ALLIUM, new ItemStack(Items.CHORUS_FRUIT, 2));
		defaultFlowerMimicDrops.put(Blocks.AZURE_BLUET, new ItemStack(Items.EMERALD, 1));
		defaultFlowerMimicDrops.put(Blocks.BLUE_ORCHID, new ItemStack(Items.SOUL_LANTERN, 1));
		defaultFlowerMimicDrops.put(Blocks.CORNFLOWER, new ItemStack(Items.FEATHER, 32));
		defaultFlowerMimicDrops.put(Blocks.DANDELION, new ItemStack(Items.GOLDEN_APPLE, 1));
		defaultFlowerMimicDrops.put(Blocks.LILAC, new ItemStack(Items.END_CRYSTAL, 1));
		defaultFlowerMimicDrops.put(Blocks.LILY_OF_THE_VALLEY, new ItemStack(Items.IRON_INGOT, 8));
		defaultFlowerMimicDrops.put(Blocks.ORANGE_TULIP, new ItemStack(Items.POWDER_SNOW_BUCKET, 1));
		defaultFlowerMimicDrops.put(Blocks.OXEYE_DAISY, new ItemStack(Items.SPECTRAL_ARROW, 16));
		defaultFlowerMimicDrops.put(Blocks.PEONY, new ItemStack(Items.CAULDRON, 1));
		defaultFlowerMimicDrops.put(Blocks.PINK_TULIP, new ItemStack(Items.POISONOUS_POTATO, 3));
		defaultFlowerMimicDrops.put(Blocks.POPPY, new ItemStack(Items.COBWEB, 6));
		defaultFlowerMimicDrops.put(Blocks.RED_TULIP, new ItemStack(Items.RABBIT_FOOT, 1));
		defaultFlowerMimicDrops.put(Blocks.ROSE_BUSH, new ItemStack(Items.MAGMA_BLOCK, 14));
		defaultFlowerMimicDrops.put(Blocks.SUNFLOWER, new ItemStack(Items.TNT, 5));
		defaultFlowerMimicDrops.put(Blocks.TORCHFLOWER, new ItemStack(Items.FIRE_CHARGE, 7));
		defaultFlowerMimicDrops.put(Blocks.WHITE_TULIP, new ItemStack(Items.CAKE, 1));
		defaultFlowerMimicDrops.put(Blocks.WITHER_ROSE, new ItemStack(Items.NETHERITE_SCRAP, 2));
	}

	public static EntityType<?> getDefaultMimicFromFlower(Block block) {
		if (!defaultFlowerToMimic.containsKey(block)) {
			return EntityType.ZOMBIE;
		}
		return defaultFlowerToMimic.get(block);
	}

	// Config File
	private static final String dirPath = DataFunctions.getConfigDirectory() + File.separator + Reference.MOD_ID;
	private static final File dir = new File(dirPath);
	private static final File flowerMimicsFile = new File(dirPath + File.separator + "flower_mimics.txt");
	private static final File flowerItemDropsFile = new File(dirPath + File.separator + "flower_item_drops.txt");

	public static void attemptMimicConfigProcessing(Level level) {
		if (!Variables.processedMimicConfig) {
			try {
				setMimicConfig(level);
				Variables.processedMimicConfig = true;
			} catch (Exception ex) {
				System.out.println("[" + Reference.NAME + "] Error: Unable to generate flower mimic config list.");
			}
		}
	}

	public static void setMimicConfig(Level level) throws IOException {
		Registry<Block> blockRegistry = level.registryAccess().registryOrThrow(Registries.BLOCK);
		Registry<EntityType<?>> entityTypeRegistry = level.registryAccess().registryOrThrow(Registries.ENTITY_TYPE);

		allFlowers = new ArrayList<Block>();

		PrintWriter flowerMimicsWriter = null;
		PrintWriter flowerItemDropsWriter = null;
		if (!dir.isDirectory() || !flowerMimicsFile.isFile() || !flowerItemDropsFile.isFile()) {
			if (dir.mkdirs()) {
				flowerMimicsWriter = new PrintWriter(dirPath + File.separator + "flower_mimics.txt", StandardCharsets.UTF_8);
				flowerItemDropsWriter = new PrintWriter(dirPath + File.separator + "flower_item_drops.txt", StandardCharsets.UTF_8);
			}

			flowerToMimic = new HashMap<Block, EntityType<?>>();
			flowerMimicDrops = new HashMap<Block, ItemStack>();
		}
		else {
			String flowerMimicsContent = new String(Files.readAllBytes(Paths.get(dirPath + File.separator + "flower_mimics.txt")));
			for (String line : flowerMimicsContent.split("\n")) {
				if (line.strip().equals("")) {
					continue;
				}

				String[] lspl = line.split(";");
				if (lspl.length < 2) {
					Variables.logger.warn("[" + Reference.NAME + "] Unable to parse flower_mimics.txt line: " + line);
					continue;
				}

				String flowerBlockRlString = lspl[0].strip();
				ResourceLocation flowerBlockResourceLocation = new ResourceLocation(flowerBlockRlString);

				Block flowerBlock = level.registryAccess().registryOrThrow(BuiltInRegistries.BLOCK.key()).get(flowerBlockResourceLocation);

				if (flowerBlock == null) {
					Variables.logger.warn("[" + Reference.NAME + "] Unable to find flower '" + flowerBlockRlString + "' in registry.");
					continue;
				}

				String mimicEntityTypeString = lspl[1].strip();
				ResourceLocation mimicEntityTypeResourceLocation = new ResourceLocation(mimicEntityTypeString);

				EntityType<?> mimicEntityType = level.registryAccess().registryOrThrow(BuiltInRegistries.ENTITY_TYPE.key()).get(mimicEntityTypeResourceLocation);

				if (mimicEntityType == null) {
					Variables.logger.warn("[" + Reference.NAME + "] Unable to find entity type '" + mimicEntityTypeString + "' in registry.");
					continue;
				}

				flowerToMimic.put(flowerBlock, mimicEntityType);
			}

			String flowerItemDropsContent = new String(Files.readAllBytes(Paths.get(dirPath + File.separator + "flower_item_drops.txt")));
			for (String line : flowerItemDropsContent.split("\n")) {
				if (line.strip().equals("")) {
					continue;
				}

				String[] lspl = line.split(";");
				if (lspl.length < 2) {
					Variables.logger.warn("[" + Reference.NAME + "] Unable to parse flower_item_drops.txt line: " + line);
					continue;
				}

				String flowerBlockRlString = lspl[0].strip();
				ResourceLocation flowerBlockResourceLocation = new ResourceLocation(flowerBlockRlString);

				Block flowerBlock = level.registryAccess().registryOrThrow(BuiltInRegistries.BLOCK.key()).get(flowerBlockResourceLocation);

				if (flowerBlock == null) {
					Variables.logger.warn("[" + Reference.NAME + "] Unable to find flower '" + flowerBlockRlString + "' in registry.");
					continue;
				}

				String itemStackNBTString = lspl[1].strip();

				try {
					CompoundTag newnbt = TagParser.parseTag(itemStackNBTString);
					ItemStack itemStack = ItemStack.of(newnbt);

					flowerMimicDrops.put(flowerBlock, itemStack.copy());
				}
				catch (CommandSyntaxException ex) {
					Variables.logger.warn("[" + Reference.NAME + "] Unable to find itemstack from NBT: " + itemStackNBTString);
				}
			}
		}

		List<String> flowerRLStrings = new ArrayList<String>();
		HashMap<String, Block> flowerBlocks = new HashMap<String, Block>();

		for (Block block : blockRegistry) {
			if (Util.isFlower(block)) {
				ResourceLocation rl = blockRegistry.getKey(block);
				if (rl == null) {
					continue;
				}

				String flowerRLString = rl.toString();
				flowerRLStrings.add(flowerRLString);
				flowerBlocks.put(flowerRLString, block);
			}
		}

		Collections.sort(flowerRLStrings);

		for (String flowerRLString : flowerRLStrings) {
			Block flowerBlock = flowerBlocks.get(flowerRLString);

			allFlowers.add(flowerBlock);

			if (flowerMimicsWriter != null) {
				EntityType<?> defaultMimic = getDefaultMimicFromFlower(flowerBlock);
				ResourceLocation mimicRl = entityTypeRegistry.getKey(defaultMimic);

				String mimicRlString = " ";
				if (mimicRl != null) {
					mimicRlString = mimicRl.toString();
				}

				flowerMimicsWriter.println(flowerRLString + " ; " + mimicRlString);
				flowerToMimic.put(flowerBlock, defaultMimic);
			}

			if (flowerItemDropsWriter != null) {
				ItemStack defaultFlowerMimicDropStack = new ItemStack(Blocks.AIR, 1);

				if (defaultFlowerMimicDrops.containsKey(flowerBlock)) {
					defaultFlowerMimicDropStack = defaultFlowerMimicDrops.get(flowerBlock).copy();
				}

				String nBTString = ItemFunctions.getNBTStringFromItemStack(defaultFlowerMimicDropStack);

				flowerItemDropsWriter.println(flowerRLString + " ; " + nBTString);
				flowerMimicDrops.put(flowerBlock, defaultFlowerMimicDropStack.copy());
			}
		}

		if (flowerMimicsWriter != null) {
			flowerMimicsWriter.close();
		}
		if (flowerItemDropsWriter != null) {
			flowerItemDropsWriter.close();
		}
	}
}
