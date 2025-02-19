/*
 * This is the latest source code of No Hostiles Around Campfire.
 * Minecraft version: 1.18.2.
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

package com.natamus.nohostilesaroundcampfire.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.nohostilesaroundcampfire.events.CampfireEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeCampfireEvent {
	@SubscribeEvent
	public void onWorldTick(WorldTickEvent e) {
		Level level = e.world;
		if (level.isClientSide || !e.phase.equals(Phase.END)) {
			return;
		}
		
		CampfireEvent.onWorldTick((ServerLevel)level);
	}
	
	@SubscribeEvent
	public void onEntityCheckSpawn(LivingSpawnEvent.CheckSpawn e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
		if (level == null) {
			return;
		}

		Entity entity = e.getEntity();
		if (!(entity instanceof Mob)) {
			return;
		}

		if (!CampfireEvent.onEntityCheckSpawn((Mob)entity, (ServerLevel)level, null, e.getSpawnReason())) {
			e.setResult(Result.DENY);
		}
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onCampfirePlace(BlockEvent.EntityPlaceEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
		if (level == null) {
			return;
		}

		Entity entity = e.getEntity();
		if (!(entity instanceof LivingEntity)) {
			return;
		}

		if (e.isCanceled()) {
			return;
		}

		CampfireEvent.onCampfirePlace(level, e.getPos(), e.getPlacedBlock(), (LivingEntity)entity, null);
	}
	
	@SubscribeEvent
	public void onRightClickCampfireBlock(PlayerInteractEvent.RightClickBlock e) {
		CampfireEvent.onRightClickCampfireBlock(e.getWorld(), e.getPlayer(), e.getHand(), e.getPos(), e.getHitVec());
	}
	
	@SubscribeEvent
	public void onCampfireBreak(BlockEvent.BreakEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getWorld());
		if (level == null) {
			return;
		}

		CampfireEvent.onCampfireBreak(level, e.getPlayer(), e.getPos(), e.getState(), null, null);
	}
}