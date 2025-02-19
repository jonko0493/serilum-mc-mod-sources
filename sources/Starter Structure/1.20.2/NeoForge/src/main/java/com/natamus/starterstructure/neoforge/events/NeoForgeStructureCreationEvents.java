/*
 * This is the latest source code of Starter Structure.
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

package com.natamus.starterstructure.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.starterstructure.events.StructureCreationEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeStructureCreationEvents {
	@SubscribeEvent(receiveCanceled = true, priority = EventPriority.LOWEST)
	public static void onLevelSpawn(LevelEvent.CreateSpawnPosition e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}
		
		if (StructureCreationEvents.onLevelSpawn((ServerLevel)level, (ServerLevelData)level.getLevelData()).equals(InteractionResult.SUCCESS)) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onLevelLoad(LevelEvent.Load e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		StructureCreationEvents.onLevelLoad((ServerLevel)level);
	}
}
