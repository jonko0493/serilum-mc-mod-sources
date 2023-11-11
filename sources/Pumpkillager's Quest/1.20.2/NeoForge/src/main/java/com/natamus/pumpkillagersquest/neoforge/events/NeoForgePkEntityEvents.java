/*
 * This is the latest source code of Pumpkillager's Quest.
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

package com.natamus.pumpkillagersquest.neoforge.events;

import com.natamus.pumpkillagersquest.events.PkEntityEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforged.neoforge.event.entity.player.EntityItemPickupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber
public class NeoForgePkEntityEvents {
	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent e) {
		Level level = e.getLevel();
		if (level.isClientSide) {
			return;
		}

		if (!PkEntityEvents.onEntityJoin(e.getEntity(), (ServerLevel)level)) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityLeave(EntityLeaveLevelEvent e) {
		PkEntityEvents.onEntityLeave(e.getEntity(), e.getLevel());
	}

	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent e) {
		Player player = e.getEntity();
		PkEntityEvents.onItemPickup(player.level(), player, e.getItem().getItem());
	}

	@SubscribeEvent
	public static void onEntityHitByLightning(EntityStruckByLightningEvent e) {
		Entity entity = e.getEntity();
		if (!PkEntityEvents.onEntityHitByLightning(entity.level(), entity, e.getLightning())) {
			e.setCanceled(true);
		}
	}
}
