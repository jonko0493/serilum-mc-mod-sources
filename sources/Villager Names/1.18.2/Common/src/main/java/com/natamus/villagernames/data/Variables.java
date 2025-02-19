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

package com.natamus.villagernames.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.slf4j.Logger;

public class Variables {
	public static Pair<Component, Component> tradedVillagerPair = new Pair<Component, Component>(TextComponent.EMPTY.copy(), TextComponent.EMPTY.copy());
	public static final Logger logger = LogUtils.getLogger();
}
