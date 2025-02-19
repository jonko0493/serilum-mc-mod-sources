/*
 * This is the latest source code of Collective.
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

package com.natamus.collective.data;

import com.natamus.collective.objects.SAMObject;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GlobalVariables {
	public static Random random = new Random();
	public static RandomSource randomSource = RandomSource.create();
	
	// Spawn A Mob variables
	public static CopyOnWriteArrayList<EntityType<?>> activesams = new CopyOnWriteArrayList<EntityType<?>>();
	public static CopyOnWriteArrayList<SAMObject> samobjects = new CopyOnWriteArrayList<SAMObject>();
	
	// Villager names; 1: guard-villagers, 2-4: farlanders
	public static List<String> moddedvillagers = new ArrayList<String>(Arrays.asList("Villager", "WanderingTrader", "Guard", "Farlander", "ElderFarlander", "Wanderer", "ThiefWanderingTrader", "PlagueDoctor", "GoblinTrader", "VeinGoblinTrader", "RedMerchant", "WanderingWizard", "WanderingWinemaker", "WanderingBaker", "Bowman", "Crossbowman", "Horseman", "Nomad", "Recruit", "RecruitShieldman", "Gatekeeper", "VillagerEntityMCA"));

	// Names
	public static List<String> areaNames = new ArrayList<String>();
	public static List<String> femaleNames = new ArrayList<String>();
	public static List<String> maleNames = new ArrayList<String>();

	// Linger Messages
	public static List<String> lingerMessages = new ArrayList<String>();

	// Mob drops
	public static HashMap<EntityType<?>, List<Item>> entitydrops = null;
	
	// URLS
	public static String playerdataurl = "https://api.mojang.com/users/profiles/minecraft/";
	public static String skindataurl = "https://sessionserver.mojang.com/session/minecraft/profile/";
	
	// HashMaps to generate
	public static Map<Block, BlockEntityType<?>> blocksWithTileEntity = new HashMap<Block, BlockEntityType<?>>();
	
	public static void generateHashMaps() {
		// FAB tile entities.
		blocksWithTileEntity.put(Blocks.CAMPFIRE, BlockEntityType.CAMPFIRE);
		blocksWithTileEntity.put(Blocks.OAK_SIGN, BlockEntityType.SIGN);
	}
		
	// Block and item collections
	public static List<Block> growblocks = Arrays.asList(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.PODZOL, Blocks.SAND, Blocks.RED_SAND);
	public static List<Block> stoneblocks = Arrays.asList(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE, Blocks.STONE, Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS, Blocks.SMOOTH_STONE, Blocks.GRAVEL, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, Blocks.DIORITE, Blocks.POLISHED_DIORITE, Blocks.GRANITE, Blocks.POLISHED_GRANITE, Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE, Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.NETHERRACK, Blocks.NETHER_BRICKS, Blocks.RED_NETHER_BRICKS);
	public static List<Item> stoneblockitems = Arrays.asList(Items.COBBLESTONE, Items.MOSSY_COBBLESTONE, Items.STONE, Items.STONE_BRICKS, Items.CHISELED_STONE_BRICKS, Items.CRACKED_STONE_BRICKS, Items.SMOOTH_STONE, Items.GRAVEL, Items.ANDESITE, Items.POLISHED_ANDESITE, Items.DIORITE, Items.POLISHED_DIORITE, Items.GRANITE, Items.POLISHED_GRANITE, Items.SANDSTONE, Items.CHISELED_SANDSTONE, Items.CUT_SANDSTONE, Items.SMOOTH_SANDSTONE, Items.RED_SANDSTONE, Items.CHISELED_RED_SANDSTONE, Items.CUT_RED_SANDSTONE, Items.SMOOTH_RED_SANDSTONE, Items.END_STONE, Items.END_STONE_BRICKS, Items.NETHERRACK, Items.NETHER_BRICKS, Items.RED_NETHER_BRICKS);
	public static List<MapColor> surfacematerials = Arrays.asList(MapColor.WATER, MapColor.ICE);
}
