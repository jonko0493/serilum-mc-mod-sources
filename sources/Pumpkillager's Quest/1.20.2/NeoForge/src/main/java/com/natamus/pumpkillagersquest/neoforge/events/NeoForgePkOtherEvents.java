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

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.cmds.CommandPumpkillager;
import com.natamus.pumpkillagersquest.events.PkOtherEvents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.PistonEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber
public class NeoForgePkOtherEvents {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent e) {
		CommandPumpkillager.register(e.getDispatcher());
	}

	@SubscribeEvent
	public static void onTNTExplode(ExplosionEvent.Detonate e) {
		Explosion explosion = e.getExplosion();
		PkOtherEvents.onTNTExplode(e.getLevel(), explosion.getExploder(), explosion);
	}

	@SubscribeEvent
	public static void onPistonMove(PistonEvent.Pre e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		if (!PkOtherEvents.onPistonMove(level, e.getPos(), e.getDirection(), e.getPistonMoveType().isExtend)) {
			e.setCanceled(true);
		}
	}
}
