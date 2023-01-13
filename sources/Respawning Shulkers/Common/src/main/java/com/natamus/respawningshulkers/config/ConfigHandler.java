/*
 * This is the latest source code of Respawning Shulkers.
 * Minecraft version: 1.19.3.
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

package com.natamus.respawningshulkers.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.respawningshulkers.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static int timeInTicksToRespawn = 1200;
	@Entry public static boolean shulkersFromSpawnersDoNotRespawn = true;

	public static void initConfig() {
		configMetaData.put("timeInTicksToRespawn", Arrays.asList(
			"The amount of time in ticks it takes for a shulker to respawn after it died. 20 ticks = 1 second. By default 60 seconds.",
			"min: 1, max: 72000"
		));
		configMetaData.put("shulkersFromSpawnersDoNotRespawn", Arrays.asList(
			"If enabled, shulkers which spawn from (modded) spawners will not be respawned after a death."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}