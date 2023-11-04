/*
 * This is the latest source code of Villager Names.
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

package com.natamus.villagernames.util;

import com.natamus.collective.data.GlobalVariables;
import com.natamus.collective.functions.DataFunctions;
import com.natamus.collective.functions.StringFunctions;
import com.natamus.villagernames.config.ConfigHandler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Names {
	public static List<String> customVillagerNames = new ArrayList<String>();
	
	public static void setCustomNames() throws IOException {
		String dirpath = DataFunctions.getConfigDirectory() + File.separator + "villagernames";
		File dir = new File(dirpath);
		File file = new File(dirpath + File.separator + "customnames.txt");
		
		if (dir.isDirectory() && file.isFile()) {
			String cn = Files.readString(Paths.get(dirpath + File.separator + "customnames.txt"));
			cn = cn.replace("\n", "").replace("\r", "");
			
			String[] cns = cn.split(",");
			customVillagerNames = Arrays.asList(cns);

			if (customVillagerNames.size() == 3) {
				if (customVillagerNames.contains("Rick") && customVillagerNames.contains("Bob") && customVillagerNames.contains("Eve"))	{ // prevent old default custom names config file from being used.
					customVillagerNames = new ArrayList<String>();
				}
			}
		}
		else {
			boolean ignored = dir.mkdirs();
			
			PrintWriter writer = new PrintWriter(dirpath + File.separator + "customnames.txt", StandardCharsets.UTF_8);
			writer.close();
		}
	}
	
	public static String getRandomName() {
		List<String> villagerNameList = new ArrayList<String>();
		if (ConfigHandler.useDefaultFemaleNames && ConfigHandler.useDefaultMaleNames) {
			villagerNameList = Stream.concat(GlobalVariables.femaleNames.stream(), GlobalVariables.maleNames.stream()).collect(Collectors.toList());
		}
		else if (ConfigHandler.useDefaultFemaleNames) {
			villagerNameList = GlobalVariables.femaleNames;
		}
		else if (ConfigHandler.useDefaultMaleNames) {
			villagerNameList = GlobalVariables.maleNames;
		}
		
		if (ConfigHandler.useCustomNames && !customVillagerNames.isEmpty()) {
			if (ConfigHandler.useBothCustomAndDefaultNames) {
				villagerNameList = Stream.concat(villagerNameList.stream(), customVillagerNames.stream()).collect(Collectors.toList());
			}
			else {
				villagerNameList = customVillagerNames;
			}
		}
		
		if (villagerNameList.isEmpty()) {
			return "";
		}
		
		String name = villagerNameList.get(GlobalVariables.random.nextInt(villagerNameList.size())).toLowerCase();
		return StringFunctions.capitalizeEveryWord(name);
	}
}
