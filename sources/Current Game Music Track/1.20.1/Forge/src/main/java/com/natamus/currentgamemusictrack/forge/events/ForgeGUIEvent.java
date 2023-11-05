/*
 * This is the latest source code of Current Game Music Track.
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

package com.natamus.currentgamemusictrack.forge.events;

import com.natamus.currentgamemusictrack.data.Constants;
import com.natamus.currentgamemusictrack.events.GUIEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ForgeGUIEvent {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void renderOverlay(RenderGuiOverlayEvent.Post e) {
		GUIEvent.renderOverlay(e.getGuiGraphics(), e.getPartialTick());
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent e) {
		GUIEvent.onClientTick(Constants.mc.level);
	}
}