/*
 * This is the latest source code of Placeable Blaze Rods.
 * Minecraft version: 1.19.4.
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

package com.natamus.placeableblazerods.forge.events;

import com.natamus.placeableblazerods.events.BlazeRodEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgeBlazeRodEvent {
	@SubscribeEvent
	public void onBlockClick(PlayerInteractEvent.RightClickBlock e) {
		BlazeRodEvent.onBlockClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec());
	}
}