/*
 * This is the latest source code of Crying Ghasts.
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

package com.natamus.cryingghasts.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.cryingghasts.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 1, max = 256) public static int maxDistanceToGhastBlocks = 32;
	@Entry(min = 1, max = 72000) public static int ghastTearDelayTicks = 1200;

	public static void initConfig() {
		configMetaData.put("maxDistanceToGhastBlocks", Arrays.asList(
			"The maximum distance in blocks the player can be away from the ghasts in order for them to start dropping tears periodically."
		));
		configMetaData.put("ghastTearDelayTicks", Arrays.asList(
			"The delay in between ghasts dropping a tear in ticks (1 second = 20 ticks)."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}