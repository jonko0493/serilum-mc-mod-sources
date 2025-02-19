/*
 * This is the latest source code of Sleep Sooner.
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

package com.natamus.sleepsooner.neoforge.events;

import com.natamus.sleepsooner.events.PlayerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgePlayerEvent {
	@SubscribeEvent
	public static void playerClick(PlayerInteractEvent.RightClickBlock e) {  
		Player player = e.getEntity();
		Level level = player.level();
		if (!PlayerEvent.playerClick(level, player, e.getHand(), e.getPos(), e.getHitVec())) {
			e.setCanceled(true);
		}
	}
}
