/*
 * This is the latest source code of Tree Harvester.
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

package com.natamus.treeharvester.events;

import com.natamus.treeharvester.processing.AxeBlacklist;
import net.minecraft.world.level.Level;

public class WorldEvents {
	public static void onWorldLoad(Level level) {
		AxeBlacklist.attemptProcessingAxeBlacklist(level);
	}
}