/*
 * This is the latest source code of Automatic Doors.
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

package com.natamus.automaticdoors.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.automaticdoors.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 0, max = 10000) public static int doorOpenTime = 2500;
	@Entry public static boolean shouldOpenIronDoors = true;
	@Entry public static boolean preventOpeningOnSneak = true;

	public static void initConfig() {
		configMetaData.put("doorOpenTime", Arrays.asList(
			"The time in ms the door should stay open."
		));
		configMetaData.put("shouldOpenIronDoors", Arrays.asList(
			"When enabled, iron doors will also be opened automatically."
		));
		configMetaData.put("preventOpeningOnSneak", Arrays.asList(
			"When enabled, doors won't be opened automatically when the player is sneaking."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}