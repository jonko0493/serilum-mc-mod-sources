/*
 * This is the latest source code of Areas.
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

package com.natamus.areas.neoforge.events;

import com.natamus.areas.cmds.ClientCommandAreas;
import com.natamus.areas.data.ClientConstants;
import com.natamus.areas.events.ClientEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class NeoForgeClientEvent {
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent e) {
		if (e.phase.equals(TickEvent.Phase.START)) {
			ClientEvent.onClientTick(ClientConstants.mc);
		}
	}

	@SubscribeEvent
	public static void registerCommands(RegisterClientCommandsEvent e) {
		ClientCommandAreas.register(e.getDispatcher());
	}
}