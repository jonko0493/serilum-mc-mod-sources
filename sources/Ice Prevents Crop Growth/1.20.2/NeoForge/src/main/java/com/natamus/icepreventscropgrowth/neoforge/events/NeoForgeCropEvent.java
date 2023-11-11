/*
 * This is the latest source code of Ice Prevents Crop Growth.
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

package com.natamus.icepreventscropgrowth.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.icepreventscropgrowth.events.CropEvent;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.level.BlockEvent.CropGrowEvent;
import net.neoforged.bus.api.Event.Result;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeCropEvent {
	@SubscribeEvent
	public static void mobItemDrop(CropGrowEvent.Pre e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}
		
		if (!CropEvent.onCropGrowth(level, e.getPos(), e.getState())) {
			e.setResult(Result.DENY);
		}
	}
}
