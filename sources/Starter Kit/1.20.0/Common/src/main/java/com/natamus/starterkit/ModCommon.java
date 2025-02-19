/*
 * This is the latest source code of Starter Kit.
 * Minecraft version: 1.20.0.
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

package com.natamus.starterkit;

import com.natamus.starterkit.config.ConfigHandler;
import com.natamus.starterkit.util.Util;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		try {
			Util.getOrCreateGearConfig(true);
		} catch (Exception ignored) {
			System.out.println("[Starter Kit] Unable to get or create the gear config file.");
		}
	}
}