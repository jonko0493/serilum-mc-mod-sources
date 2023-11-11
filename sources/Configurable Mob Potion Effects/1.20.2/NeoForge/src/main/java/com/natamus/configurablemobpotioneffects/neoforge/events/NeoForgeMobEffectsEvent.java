/*
 * This is the latest source code of Configurable Mob Potion Effects.
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

package com.natamus.configurablemobpotioneffects.neoforge.events;

import com.natamus.configurablemobpotioneffects.cmd.CommandCmpe;
import com.natamus.configurablemobpotioneffects.events.MobEffectsEvent;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeMobEffectsEvent {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent e) {
		CommandCmpe.register(e.getDispatcher());
	}

	@SubscribeEvent
	public static void onEntityJoin(EntityJoinLevelEvent e) {
		MobEffectsEvent.onEntityJoin(e.getLevel(), e.getEntity());
	}
	
	@SubscribeEvent
	public static void onEntityDamage(LivingHurtEvent e) {
		LivingEntity livingEntity = e.getEntity();
		MobEffectsEvent.onEntityDamage(livingEntity.level(), livingEntity, e.getSource(), e.getAmount());
	}
}
