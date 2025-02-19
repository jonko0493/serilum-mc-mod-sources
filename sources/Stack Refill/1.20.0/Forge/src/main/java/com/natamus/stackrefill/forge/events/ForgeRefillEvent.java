/*
 * This is the latest source code of Stack Refill.
 * Minecraft version: 1.20.0.
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

package com.natamus.stackrefill.forge.events;

import com.natamus.stackrefill.events.RefillEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.HashMap;

@EventBusSubscriber
public class ForgeRefillEvent {
	private static final HashMap<String, InteractionHand> lasthandused = new HashMap<String, InteractionHand>();

	@SubscribeEvent
	public void onWorldTick(TickEvent.ServerTickEvent e) {
		if (!e.phase.equals(Phase.START)) {
			return;
		}

		RefillEvent.processTick(false);
	}

	@SubscribeEvent
	public void onItemUse(LivingEntityUseItemEvent.Start e) {
		Entity livingEntity = e.getEntity();
		if (!(livingEntity instanceof Player)) {
			return;
		}

		Player player = (Player)livingEntity;
		ItemStack mainstack = player.getMainHandItem();
		ItemStack used = e.getItem();

		InteractionHand hand = InteractionHand.MAIN_HAND;
		if (!(mainstack.getItem().equals(used.getItem()) && mainstack.getCount() == used.getCount())) {
			hand = InteractionHand.OFF_HAND;
		}

		lasthandused.put(player.getName().getString(), hand);
	}

	@SubscribeEvent
	public void onItemUse(LivingEntityUseItemEvent.Finish e) {
		Entity livingEntity = e.getEntity();
		if (!(livingEntity instanceof Player)) {
			return;
		}
		
		Player player = (Player)livingEntity;
		String playername = player.getName().getString();
		if (!lasthandused.containsKey(playername)) {
			return;
		}

		RefillEvent.onItemUse(player, e.getItem(), null, lasthandused.get(playername));
	}
	
	@SubscribeEvent
	public void onItemBreak(PlayerDestroyItemEvent e) {
		RefillEvent.onItemBreak(e.getEntity(), e.getOriginal(), e.getHand());
	}
	
	@SubscribeEvent
	public void onItemToss(ItemTossEvent e) {
		RefillEvent.onItemToss(e.getPlayer(), e.getEntity().getItem());
	}
	
	@SubscribeEvent
	public void onItemRightClick(PlayerInteractEvent.RightClickItem e) {
		RefillEvent.onItemRightClick(e.getEntity(), e.getLevel(), e.getHand());
	}
	
	@SubscribeEvent
	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock e) {
		RefillEvent.onBlockRightClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec());
	}
}
