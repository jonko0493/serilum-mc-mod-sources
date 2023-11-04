/*
 * This is the latest source code of Villager Names.
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

package com.natamus.villagernames.util;

import com.natamus.villagernames.config.ConfigHandler;
import com.natamus.villagernames.data.Variables;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
	private static final List<String> namesToOverwrite = new ArrayList<String>(Arrays.asList("Bowman", "Crossbowman", "Horseman", "Nomad", "Recruit", "Shieldman"));
	public static boolean shouldOverwriteName(Entity entity) {
		return namesToOverwrite.contains(entity.getName().getString());
	}

	public static MutableComponent getTradeScreenTitle() {
		Component name = Variables.tradedVillagerPair.getFirst();
		Component profession = Variables.tradedVillagerPair.getSecond();

		if (!ConfigHandler.showProfessionOnTradeScreen) {
			return name.copy();
		}

		if (name.equals(Component.empty()) || profession.equals(Component.empty())) {
			return null;
		}

		MutableComponent newTitle = Component.empty();
		if (!ConfigHandler.switchNameAndProfessionTradeScreen) {
			newTitle = newTitle.append(name).append(" | ").append(profession);
		}
		else {
			newTitle = newTitle.append(profession).append(" | ").append(name);
		}
		return newTitle;
	}
}
