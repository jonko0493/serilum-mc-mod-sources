/*
 * This is the latest source code of Areas.
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

package com.natamus.areas;

import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.integrations.BlueMapIntegration;
import com.natamus.areas.util.Reference;
import com.natamus.collective.config.GenerateJSONFiles;
import de.bluecolored.bluemap.api.BlueMapAPI;

import java.util.Optional;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		GenerateJSONFiles.requestJSONFile(Reference.MOD_ID, "area_names.json");
		try {
			BlueMapAPI.onEnable(BlueMapIntegration::updateBlueMap);
		} catch (NoClassDefFoundError | IllegalStateException ignored) {
			System.out.println("BlueMap is not loaded");
		}
	}
}