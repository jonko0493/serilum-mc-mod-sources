/*
 * This is the latest source code of Nether Portal Spread.
 * Minecraft version: 1.19.4.
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

package com.natamus.netherportalspread.util;

import com.natamus.collective.functions.*;
import com.natamus.collective.objects.RandomCollection;
import com.natamus.netherportalspread.config.ConfigHandler;
import com.natamus.netherportalspread.data.Variables;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Util {
	public static HashMap<Level, CopyOnWriteArrayList<BlockPos>> portals = new HashMap<Level, CopyOnWriteArrayList<BlockPos>>();
	public static HashMap<Level, HashMap<BlockPos, Boolean>> preventedportals = new HashMap<Level, HashMap<BlockPos, Boolean>>();

	private static final HashMap<Block, HashMap<Block, Double>> convertinto = new HashMap<Block, HashMap<Block, Double>>();
	private static final List<Block> convertblocks = new ArrayList<Block>();
	private static final List<Block> convertedblocks = new ArrayList<Block>();
	private static Block preventSpreadBlock = null;

	public static void attemptSpreadBlockProcess(Level level) {
		if (!Variables.processedSpreadBlockLoad) {
			try {
				loadSpreadBlocks(level);
				Variables.processedSpreadBlockLoad = true;
			} catch (IOException ex) {
				System.out.println("[" + Reference.NAME + "] Something went wrong loading the nether spread block config.");
			}
		}
	}

	public static void loadSpreadBlocks(Level level) throws IOException {
		Registry<Block> blockRegistry = level.registryAccess().registryOrThrow(Registries.BLOCK);

		String dirpath = DataFunctions.getConfigDirectory() + File.separator + "netherportalspread";
		File dir = new File(dirpath);
		File file = new File(dirpath + File.separator + "spreadsettings.txt");

		if (dir.isDirectory() && file.isFile()) {
			String spreadsettings = new String(Files.readAllBytes(Paths.get(dirpath + File.separator + "spreadsettings.txt", new String[0])));
			spreadsettings = spreadsettings.replace("\n", "").replace("\r", "").replace(" ", ""); // remove newlines, tabs and spaces

			for (String line : spreadsettings.split(",")) {
				if (line.length() < 4) {
					System.out.println("The Nether Portal Spread spread settings contains an empty line. Ignoring it.");
					continue;
				}

				String[] linespl = line.split(";");
				if (linespl.length != 2) {
					System.out.println("[Nether Portal Spread] The spread settings line '" + line + "' contains errors. Ignoring it.");
					continue;
				}

				String fromblockstr = linespl[0];
				if (!fromblockstr.contains(":")) {
					fromblockstr = "minecraft:" + fromblockstr;
				}
				ResourceLocation frl = new ResourceLocation(fromblockstr);
				if (!blockRegistry.keySet().contains(frl)) {
					System.out.println("[Nether Portal Spread] Unable to find from-block '" + fromblockstr + "' in the Forge block registry. Ignoring it.");
					continue;
				}
				Block fromblock = blockRegistry.get(frl);

				String toblocks = linespl[1].replace("[", "").replace("]", "");

				double totalweight = 0;
				HashMap<Block, Double> tempmap = new HashMap<Block, Double>();
				for (String tb : toblocks.split("\\+")) {
					String[] tbspl = tb.split(">");
					if (tbspl.length < 2) {
						continue;
					}

					String toblockstr = tbspl[0];
					if (!toblockstr.contains(":")) {
						toblockstr = "minecraft:" + toblockstr;
					}

					double weight = 1.0;
					try {
						weight = Double.parseDouble(tbspl[1]);
					}
					catch (NumberFormatException ignored) { }
					totalweight += weight;

					ResourceLocation trl = new ResourceLocation(toblockstr);
					if (blockRegistry.keySet().contains(trl)) {
						tempmap.put(blockRegistry.get(trl), weight);
					}
					else {
						System.out.println("[Nether Portal Spread] Unable to find to-block '" + toblockstr + "' in the Forge block registry. Ignoring it.");
					}
				}

				if (tempmap.size() == 0 || totalweight == 0) {
					System.out.println("[Nether Portal Spread] The spread settings line '" + line + "' contains errors, no convert blocks were found. Ignoring it.");
				}
				else {
					for (Block key : tempmap.keySet()) {
						Double weightvalue = tempmap.get(key);
						tempmap.put(key, (1/totalweight)*weightvalue);
					}
					convertinto.put(fromblock, tempmap);
				}
			}

			for (Block b0 : convertinto.keySet()) {
				convertblocks.add(b0);
				convertedblocks.addAll(convertinto.get(b0).keySet());
			}
		}
		else {
			dir.mkdirs();

			List<String> defaultconfig = DefaultConfigs.getDefaultConfigForVersion(Reference.ACCEPTED_VERSIONS);

			PrintWriter writer = new PrintWriter(dirpath + File.separator + "spreadsettings.txt", StandardCharsets.UTF_8);
			for (String line : defaultconfig) {
				writer.println(line);
			}
			writer.close();

			loadSpreadBlocks(level);
		}

		if (preventSpreadBlock == null) {
			String psbstr = ConfigHandler.preventSpreadBlockString.strip().replaceAll("[^a-z0-9_.-:]", "");
			try {
				ResourceLocation psbrl = new ResourceLocation(psbstr);
				if (blockRegistry.keySet().contains(psbrl)) {
					preventSpreadBlock = blockRegistry.get(psbrl);
					return;
				}
			}
			catch (ResourceLocationException ignored) { }

			System.out.println("[Nether Portal Spread] Unable to get a prevent-spread-block from the string '" + psbstr + "'. Using the default coal block instead.");
			preventSpreadBlock = Blocks.COAL_BLOCK;
		}
	}

	public static void loadPortalsFromWorld(Level level) {
		String portalfolder = WorldFunctions.getWorldPath((ServerLevel)level) + File.separator + "data" + File.separator + "netherportalspread" + File.separator + "portals";
		String specificportalfolder = portalfolder + File.separator + DimensionFunctions.getSimpleDimensionString(level);

		if (specificportalfolder.endsWith("overworld")) {
			final File sourceDir = new File(portalfolder);
			final File destinationDir = new File(specificportalfolder);
			destinationDir.mkdirs();

			File[] files = sourceDir.listFiles((File pathname) -> pathname.getName().endsWith(".portal"));

			for (File f : files ) {
				Path sourcePath = Paths.get(sourceDir.getAbsolutePath() + File.separator + f.getName());
				Path destinationPath = Paths.get(destinationDir.getAbsolutePath() + File.separator + f.getName());

				try {
					Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException ignored) { }
			}
		}

		File dir = new File(specificportalfolder);
		dir.mkdirs();

		File[] listOfFiles = dir.listFiles();
		if (listOfFiles == null) {
			return;
		}

		int r = ConfigHandler.portalSpreadRadius;
		int psamount = ConfigHandler.preventSpreadBlockAmountNeeded;
		for (File listOfFile : listOfFiles) {
			String filename = listOfFile.getName();
			if (filename.endsWith(".portal")) {
				String[] cs = filename.replace(".portal", "").split("_");
				if (cs.length == 3) {
					if (NumberFunctions.isNumeric(cs[0]) && NumberFunctions.isNumeric(cs[1]) && NumberFunctions.isNumeric(cs[2])) {
						int x = Integer.parseInt(cs[0]);
						int y = Integer.parseInt(cs[1]);
						int z = Integer.parseInt(cs[2]);

						BlockPos portal = new BlockPos(x, y, z);
						HashMapFunctions.computeIfAbsent(portals, level, k -> new CopyOnWriteArrayList<BlockPos>()).add(portal);

						if (ConfigHandler.preventSpreadWithBlock) {
							int coalcount = 0;
							Iterator<BlockPos> it = BlockPos.betweenClosedStream(portal.getX() - r, portal.getY() - r, portal.getZ() - r, portal.getX() + r, portal.getY() + r, portal.getZ() + r).iterator();
							while (it.hasNext()) {
								try {
									BlockPos np = it.next();
									if (level.getBlockState(np).getBlock().equals(preventSpreadBlock)) {
										coalcount++;
										if (coalcount >= psamount) {
											break;
										}
									}
								} catch (NullPointerException ignored) { }
							}

							HashMapFunctions.computeIfAbsent(preventedportals, level, k -> new HashMap<BlockPos, Boolean>()).put(portal, coalcount >= psamount);
						}
					}
				}
			}
		}
	}

	public static void savePortalToWorld(Level level, BlockPos portal) {
		String portalfolder = WorldFunctions.getWorldPath((ServerLevel)level) + File.separator + "data" + File.separator + "netherportalspread" + File.separator + "portals" + File.separator + DimensionFunctions.getSimpleDimensionString(level);
		File dir = new File(portalfolder);
		dir.mkdirs();

		String filename = portal.getX() + "_" + portal.getY() + "_" + portal.getZ() + ".portal";
		try {
			PrintWriter writer = new PrintWriter(portalfolder + File.separator + filename, StandardCharsets.UTF_8);
			writer.close();
		} catch (Exception e) {
			System.out.println("[Error] Nether Portal Spread: Something went wrong while saving a portal location.");
		}
	}

	private static Boolean portalExists(Level level, BlockPos pos) {
		for (BlockPos portalpos : HashMapFunctions.computeIfAbsent(portals, level, k -> new CopyOnWriteArrayList<BlockPos>())) {
			double distance = pos.distSqr(new Vec3i(portalpos.getX(), portalpos.getY(), portalpos.getZ()));
			if (distance < 10) {
				return true;
			}
		}
		return false;
	}

	public static void validatePortalAndAdd(Level level, BlockPos p) {
		BlockPos rawportal = null;

		int r = 3;
		Iterator<BlockPos> it = BlockPos.betweenClosedStream(p.getX()-r, p.getY()-r, p.getZ()-r, p.getX()+r, p.getY()+r, p.getZ()+r).iterator();
		while (it.hasNext()) {
			BlockPos nextpos = it.next();
			Block block = level.getBlockState(nextpos).getBlock();
			if (CompareBlockFunctions.isPortalBlock(block)) {
				rawportal = nextpos.immutable();
				break;
			}
		}

		if (rawportal == null) {
			return;
		}

		while (CompareBlockFunctions.isPortalBlock(level.getBlockState(rawportal.below()).getBlock())) {
			rawportal = rawportal.below().immutable();
		}
		while (CompareBlockFunctions.isPortalBlock(level.getBlockState(rawportal.west()).getBlock())) {
			rawportal = rawportal.west().immutable();
		}
		while (CompareBlockFunctions.isPortalBlock(level.getBlockState(rawportal.north()).getBlock())) {
			rawportal = rawportal.north().immutable();
		}

		if (HashMapFunctions.computeIfAbsent(portals, level, k -> new CopyOnWriteArrayList<BlockPos>()).contains(rawportal) || HashMapFunctions.computeIfAbsent(preventedportals, level, k -> new HashMap<BlockPos, Boolean>()).containsKey(rawportal)) {
			return;
		}

		if (portalExists(level, p)) {
			return;
		}

		sendSpreadingMessage(level, p);
		preventedportals.get(level).put(p, false);
		portals.get(level).add(rawportal);
		savePortalToWorld(level, rawportal);

		int netherblockcount = countNetherBlocks(level, p);
		if (netherblockcount < ConfigHandler.instantConvertAmount) {
			while (netherblockcount < ConfigHandler.instantConvertAmount) {
				if (!spreadNextBlock(level, p)) {
					break;
				}

				netherblockcount+=1;
			}
		}
	}

	public static void removePortal(Level level, BlockPos portal) {
		String portalfolder = WorldFunctions.getWorldPath((ServerLevel)level) + File.separator + "data" + File.separator + "netherportalspread" + File.separator + "portals";
		String filename = portal.getX() + "_" + portal.getY() + "_" + portal.getZ() + ".portal";
		File filepath = new File(portalfolder + File.separator + filename);
		try {
			Files.deleteIfExists(filepath.toPath());
		} catch (Exception e) {
			System.out.println("[Error] Nether Portal Spread: Something went wrong while removing an old portal location.");
		}

		HashMapFunctions.computeIfAbsent(portals, level, k -> new CopyOnWriteArrayList<BlockPos>()).remove(portal);
		HashMapFunctions.computeIfAbsent(preventedportals, level, k -> new HashMap<BlockPos, Boolean>()).remove(portal);
		sendBrokenPortalMessage(level, portal);
	}

	public static Boolean spreadNextBlock(Level level, BlockPos portal) {
		if (!level.hasChunk(portal.getX() >> 4, portal.getZ() >> 4)) {
			return true;
		}

		BlockState pbs = level.getBlockState(portal);
		if (pbs == null) {
			return false;
		}

		if (!CompareBlockFunctions.isPortalBlock(pbs.getBlock())) {
			removePortal(level, portal);
			return false;
		}

		int r = ConfigHandler.portalSpreadRadius;

		BlockPos closest = null;
		double nearestdistance = 100000;
		int coalcount = 0;

		int psamount = ConfigHandler.preventSpreadBlockAmountNeeded;
		Iterator<BlockPos> it = BlockPos.betweenClosedStream(portal.getX()-r, portal.getY()-r, portal.getZ()-r, portal.getX()+r, portal.getY()+r, portal.getZ()+r).iterator();
		while (it.hasNext()) {
			try {
				BlockPos np = it.next();
				if (ConfigHandler.preventSpreadWithBlock) {
					if (level.getBlockState(np).getBlock().equals(preventSpreadBlock)) {
						coalcount++;
						if (coalcount >= psamount) {
							break;
						}
					}
				}
				double npnd = portal.distSqr(new Vec3i(np.getX(), np.getY(), np.getZ()));
				if (npnd < nearestdistance) {
					if (isNetherTarget(level, np, false)) {
						nearestdistance = npnd;
						closest = np.immutable();
					}
				}
			}
			catch (NullPointerException ignored) { }
		}

		if (ConfigHandler.preventSpreadWithBlock) {
			if (coalcount >= psamount) {
				boolean prevented = false;
				if (HashMapFunctions.computeIfAbsent(preventedportals, level, k -> new HashMap<BlockPos, Boolean>()).containsKey(portal)) {
					prevented = preventedportals.get(level).get(portal);
				}

				if (!prevented) {
					sendPreventedMessage(level, portal);
				}

				preventedportals.get(level).put(portal, true);
				return true;
			}
		}

		if (closest != null) {
			boolean prevented = false;
			if (HashMapFunctions.computeIfAbsent(preventedportals, level, k -> new HashMap<BlockPos, Boolean>()).containsKey(portal)) {
				prevented = preventedportals.get(level).get(portal);
			}

			if (prevented) {
				sendSpreadingMessage(level, portal);
			}

			preventedportals.get(level).put(portal, false);
			spreadNetherToBlock(level, closest);
			return true;
		}
		return false;
	}

	public static int countNetherBlocks(Level level, BlockPos p) {
		int nethercount = 0;
		int r = ConfigHandler.portalSpreadRadius;

		Iterator<BlockPos> it = BlockPos.betweenClosedStream(p.getX()-r, p.getY()-r, p.getZ()-r, p.getX()+r, p.getY()+r, p.getZ()+r).iterator();
		while (it.hasNext()) {
			BlockPos np = it.next();
			if (isNetherTarget(level, np, true)) {
				nethercount+=1;
			}
		}
		return nethercount;
	}

	public static Boolean isNetherTarget(Level level, BlockPos pos, Boolean count) {
		if (level == null) {
			return false;
		}

		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();

		if (count) {
			return convertedblocks.contains(block);
		}
		return convertblocks.contains(block);
	}

	public static void spreadNetherToBlock(Level level, BlockPos pos) {
		if (level == null) {
			return;
		}

		BlockState newblockstate = null;

		BlockState curstate = level.getBlockState(pos);
		Block curblock = curstate.getBlock();

		if (convertblocks.contains(curblock)) {
			// key: from-block, value: HashMap of to-block and weight. so if two had weight 10 and 5, (1/15)*10 == 66.65% and 33.35% chance.
			RandomCollection<Block> rc = new RandomCollection<>(); //.add(40, "a").add(35, "b").add(25, "c");
			HashMap<Block, Double> hashmap = convertinto.get(curblock);
			for (Block b0 : hashmap.keySet()) {
				Double weight = hashmap.get(b0);
				rc.add(weight*100, b0);
			}

			newblockstate = rc.next().defaultBlockState();
		}

		if (newblockstate != null) {
			level.setBlockAndUpdate(pos, newblockstate);
		}
	}

	private static void sendSpreadingMessage(Level level, BlockPos p) {
		if (!ConfigHandler.sendMessageOnPortalCreation) {
			return;
		}

		String message = ConfigHandler.messageOnPortalCreation;
		StringFunctions.sendMessageToPlayersAround(level, p, ConfigHandler.portalSpreadRadius, formatAroundString(message, ConfigHandler.preventSpreadBlockAmountNeeded, p), ChatFormatting.RED);
	}

	private static void sendPreventedMessage(Level level, BlockPos p) {
		if (!ConfigHandler.sendMessageOnPreventSpreadBlocksFound) {
			return;
		}

		String message = ConfigHandler.messageOnPreventSpreadBlocksFound;
		StringFunctions.sendMessageToPlayersAround(level, p, ConfigHandler.portalSpreadRadius, formatAroundString(message, ConfigHandler.preventSpreadBlockAmountNeeded, p), ChatFormatting.DARK_GREEN);
	}

	private static void sendBrokenPortalMessage(Level level, BlockPos p) {
		if (!ConfigHandler.sendMessageOnPortalBroken) {
			return;
		}

		String message = ConfigHandler.messageOnPortalBroken;
		StringFunctions.sendMessageToPlayersAround(level, p, ConfigHandler.portalSpreadRadius, formatAroundString(message, ConfigHandler.preventSpreadBlockAmountNeeded, p), ChatFormatting.DARK_GREEN);
	}
	
	private static String formatAroundString(String message, int amountneeded, BlockPos portal) {
		if (preventSpreadBlock == null) {
			preventSpreadBlock = Blocks.COAL_BLOCK;
		}
		
		String blockstring = BlockFunctions.blockToReadableString(preventSpreadBlock, amountneeded);

		message = message.replace("%preventSpreadBlockString%", blockstring);
		message = message.replace("%preventSpreadBlockAmountNeeded%", amountneeded + "");
		message = message.replace("%portalSpreadRadius%", ConfigHandler.portalSpreadRadius + "");
		if (ConfigHandler.prefixPortalCoordsInMessage) {
			message = "Portal {" + portal.getX() + ", " + portal.getY() + ", " + portal.getZ() + "}: " + message;
		}
		return message;
	}
}
