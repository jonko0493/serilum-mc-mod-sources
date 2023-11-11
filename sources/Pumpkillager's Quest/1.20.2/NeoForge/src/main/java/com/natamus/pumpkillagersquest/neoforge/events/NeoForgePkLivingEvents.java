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

import com.natamus.pumpkillagersquest.events.PkLivingEvents;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber
public class NeoForgePkLivingEvents {
	@SubscribeEvent
	public static void onDamagePumpkillager(LivingHurtEvent e) {
		LivingEntity livingEntity = e.getEntity();

		float damageAmount = e.getAmount();
		float newAmount = PkLivingEvents.onDamagePumpkillager(livingEntity.level(), livingEntity, e.getSource(), damageAmount);

		if (damageAmount != newAmount) {
			e.setAmount(newAmount);

			if (newAmount == 0F) {
				e.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent e) {
		if (!PkLivingEvents.onLivingDeath(e.getEntity(), e.getSource(), 0F)) {
			e.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onEntityItemDrop(LivingDropsEvent e) {
		LivingEntity livingEntity = e.getEntity();
		PkLivingEvents.onEntityItemDrop(livingEntity.level(), livingEntity, e.getSource());
	}
}
