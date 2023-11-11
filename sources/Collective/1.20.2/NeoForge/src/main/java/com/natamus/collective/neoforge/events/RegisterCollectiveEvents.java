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

package com.natamus.collective.neoforge.events;

import com.natamus.collective.config.GenerateJSONFiles;
import com.natamus.collective.events.CollectiveEvents;
import com.natamus.collective.functions.WorldFunctions;
import com.natamus.collective.util.CollectiveReference;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@EventBusSubscriber
public class RegisterCollectiveEvents {
    @SubscribeEvent
    public static void onServerStarted(ServerStartingEvent e) {
        GenerateJSONFiles.initGeneration(e.getServer());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent e) {
        Level level = e.level;
        if (level.isClientSide || !e.phase.equals(TickEvent.Phase.END)) {
            return;
        }

        CollectiveEvents.onWorldTick((ServerLevel)level);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.END)) {
            return;
        }

        CollectiveEvents.onServerTick(e.getServer());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onMobSpawnerSpecialSpawn(MobSpawnEvent.FinalizeSpawn e) {
        Level Level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (Level == null) {
            return;
        }

        if (!e.isSpawnCancelled()) {
            if (e.getSpawner() != null) {
                e.getEntity().addTag(CollectiveReference.MOD_ID + ".fromspawner");
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent e) {
        if (!CollectiveEvents.onEntityJoinLevel(e.getLevel(), e.getEntity())) {
            e.setCanceled(true);
        }
    }
}