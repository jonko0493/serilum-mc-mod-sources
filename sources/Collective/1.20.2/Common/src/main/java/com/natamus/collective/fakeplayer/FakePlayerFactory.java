/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.20.2.
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

package com.natamus.collective.fakeplayer;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;

import java.util.Map;
import java.util.UUID;

public class FakePlayerFactory {
    private static final GameProfile MINECRAFT = new GameProfile(UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77"), "[Minecraft]");
    // Map of all active fake player usernames to their entities
    private static final Map<FakePlayerKey, FakePlayer> fakePlayers = Maps.newHashMap();

    private record FakePlayerKey(ServerLevel level, GameProfile username) {}

    public static FakePlayer getMinecraft(ServerLevel level) {
        return get(level, MINECRAFT);
    }

    /**
     * Get a fake player with a given username,
     * Mods should either hold weak references to the return value, or listen for a
     * WorldEvent.Unload and kill all references to prevent worlds staying in memory,
     * or call this function every time and let Forge take care of the cleanup.
     */
    public static FakePlayer get(ServerLevel level, GameProfile username) {
        FakePlayerKey key = new FakePlayerKey(level, username);
        return fakePlayers.computeIfAbsent(key, k -> new FakePlayer(k.level(), k.username()));
    }

    public static void unloadLevel(ServerLevel level) {
        fakePlayers.entrySet().removeIf(entry -> entry.getValue().level() == level);
    }
}