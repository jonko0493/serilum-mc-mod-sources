/*
 * This is the latest source code of Respawning Shulkers.
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

package com.natamus.respawningshulkers.forge.events;

import com.natamus.respawningshulkers.events.ShulkerEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeShulkerEvent {
	@SubscribeEvent
	public void onWorldTick(LevelTickEvent e) {
		Level level = e.level;
		if (level.isClientSide || !e.phase.equals(Phase.START)) {
			return;
		}
		
		ShulkerEvent.onWorldTick((ServerLevel)level);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onShulkerDeath(LivingDeathEvent e) {
		Entity entity = e.getEntity();
		ShulkerEvent.onShulkerDeath(entity.level(), entity, e.getSource());
	}
	
	@SubscribeEvent
	public void onServerShutdown(ServerStoppingEvent e) {
		ShulkerEvent.onServerShutdown(e.getServer());
	}
}
