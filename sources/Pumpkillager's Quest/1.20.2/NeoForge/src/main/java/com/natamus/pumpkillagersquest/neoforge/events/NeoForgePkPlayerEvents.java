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

import com.natamus.pumpkillagersquest.events.PkPlayerEvents;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class NeoForgePkPlayerEvents {
	@SubscribeEvent
	public static void onCharacterInteract(PlayerInteractEvent.EntityInteract e) {
		if (PkPlayerEvents.onCharacterInteract(e.getEntity(), e.getLevel(), e.getHand(), e.getTarget(), null).equals(InteractionResult.SUCCESS)) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
		PkPlayerEvents.onRightClickItem(e.getEntity(), e.getLevel(), e.getHand());
	}
}
