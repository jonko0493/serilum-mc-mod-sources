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

import com.natamus.starterstructure.events.StructureSpawnPointEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeStructureSpawnPointEvents {
	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e) {
		Player player = e.getEntity();
		Level world = player.level();
		if (world.isClientSide) {
			return;
		}

		StructureSpawnPointEvents.onPlayerRespawn(null, (ServerPlayer)player, true);
	}

	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent e) {
		StructureSpawnPointEvents.onEntityJoin(e.getLevel(), e.getEntity());
	}
}
