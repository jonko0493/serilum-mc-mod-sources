/*
 * This is the latest source code of Nutritious Milk.
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

package com.natamus.nutritiousmilk.neoforge.events;

import com.natamus.nutritiousmilk.events.MilkEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeMilkEvent {
	@SubscribeEvent
	public static void onDrink(LivingEntityUseItemEvent.Finish e) {
		Entity entity = e.getEntity();
		if (!(entity instanceof Player)) {
			return;
		}

		MilkEvent.onDrink((Player)e.getEntity(), e.getItem(), null, null);
	}
}