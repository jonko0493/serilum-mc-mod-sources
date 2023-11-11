/*
 * This is the latest source code of Cycle Paintings.
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

package com.natamus.cyclepaintings.neoforge.events;

import com.natamus.cyclepaintings.data.Constants;
import com.natamus.cyclepaintings.events.PaintingEvent;
import com.natamus.cyclepaintings.util.Reference;
import com.natamus.cyclepaintings.util.Util;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgePaintingEvent {
	@SubscribeEvent
	public static void onServerStart(ServerStartedEvent e) {
		try {
			Util.setPaintings(e.getServer().registryAccess().registryOrThrow(ForgeRegistries.PAINTING_VARIANTS.getRegistryKey()));
		}
		catch (Exception ex) {
			Constants.logger.warn("[" + Reference.NAME + "] Something went wrong while loading all paintings.");
		}
	}

	@SubscribeEvent
	public static void onClick(PlayerInteractEvent.EntityInteract e) {
		Player player = e.getEntity();
		if (PaintingEvent.onClick(player, e.getLevel(), e.getHand(), e.getTarget(), null).equals(InteractionResult.SUCCESS)) {
			player.swing(e.getHand());
		}
	}
}
