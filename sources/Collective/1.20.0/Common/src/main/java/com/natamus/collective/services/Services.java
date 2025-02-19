/*
 * This is the latest source code of Collective.
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

package com.natamus.collective.services;

import com.natamus.collective.services.helpers.EventTriggerHelper;
import com.natamus.collective.services.helpers.ModLoaderHelper;
import com.natamus.collective.services.helpers.ToolFunctionsHelper;

import java.util.ServiceLoader;

public class Services {
    public static final ModLoaderHelper MODLOADER = load(ModLoaderHelper.class);
    public static final ToolFunctionsHelper TOOLFUNCTIONS = load(ToolFunctionsHelper.class);
    public static final EventTriggerHelper EVENTTRIGGER = load(EventTriggerHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("[Collective] Failed to load service for " + clazz.getName() + "."));
    }
}