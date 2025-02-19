/*
 * This is the latest source code of Cycle Paintings.
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

package com.natamus.cyclepaintings.forge.events;

import com.natamus.cyclepaintings.data.Constants;
import com.natamus.cyclepaintings.events.PaintingEvent;
import com.natamus.cyclepaintings.util.Reference;
import com.natamus.cyclepaintings.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgePaintingEvent {
	@SubscribeEvent
	public void onServerStart(ServerStartedEvent e) {
		try {
			Util.setPaintings(e.getServer().registryAccess().registryOrThrow(Registries.PAINTING_VARIANT));
		}
		catch (Exception ex) {
			Constants.logger.warn("[" + Reference.NAME + "] Something went wrong while loading all paintings.");
		}
	}

	@SubscribeEvent
	public void onClick(PlayerInteractEvent.EntityInteract e) {
		Player player = e.getEntity();
		if (PaintingEvent.onClick(player, e.getLevel(), e.getHand(), e.getTarget(), null).equals(InteractionResult.SUCCESS)) {
			player.swing(e.getHand());
		}
	}
}
