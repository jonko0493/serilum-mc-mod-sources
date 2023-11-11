/*
 * This is the latest source code of Hidden Recipe Book.
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

package com.natamus.hiddenrecipebook.neoforge.events;

import com.natamus.hiddenrecipebook.data.Variables;
import com.natamus.hiddenrecipebook.events.BookGUIEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(Dist.CLIENT)
public class NeoForgeBookGUIEvent {
	@SubscribeEvent
	public static void onGUIScreen(ScreenEvent.Init.Post e) {
		BookGUIEvent.onGUIScreen(Variables.mc, e.getScreen(), 0, 0);
	}

	@SubscribeEvent
	public static void onKey(ScreenEvent.KeyPressed.Pre e) {
		if (e.getKeyCode() == Variables.hotkey.getKey().getValue()) {
			BookGUIEvent.onHotkeyPress();
		}
	}
}
