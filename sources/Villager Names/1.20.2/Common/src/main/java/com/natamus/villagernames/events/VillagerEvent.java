/*
 * This is the latest source code of Villager Names.
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

package com.natamus.villagernames.events;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective.functions.EntityFunctions;
import com.natamus.villagernames.config.ConfigHandler;
import com.natamus.villagernames.data.Variables;
import com.natamus.villagernames.util.Names;
import com.natamus.villagernames.util.Reference;
import com.natamus.villagernames.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class VillagerEvent {
	public static void onSpawn(Level level, Entity entity) {
		if (level.isClientSide) {
			return;
		}

		if (entity.getTags().contains(Reference.MOD_ID + ".named")) {
			return;
		}

		if (!(entity instanceof Villager)) {
			boolean goname = false;
			if (ConfigHandler.nameModdedVillagers) {
				if (EntityFunctions.isModdedVillager(entity)) {
					goname = true;
				}
			}
			
			if (!goname) {
				return;
			}
		}

		if (entity.hasCustomName()) {
			if (!Util.shouldOverwriteName(entity)) {
				return;
			}
		}
		

		EntityFunctions.nameEntity(entity, Names.getRandomName());
		entity.getTags().add(Reference.MOD_ID + ".named");
	}

	public static InteractionResult onVillagerInteract(Player player, Level level, InteractionHand hand, Entity entity, EntityHitResult hitResult) {
		if (!level.isClientSide) { // Client side only!
			return InteractionResult.PASS;
		}

		if (!entity.getClass().equals(Villager.class)) {
			return InteractionResult.PASS;
		}

		if (!entity.hasCustomName()) {
			return InteractionResult.PASS;
		}

		if (player.isCrouching()) {
			return InteractionResult.PASS;
		}

		if (!hand.equals(InteractionHand.MAIN_HAND)) {
			return InteractionResult.PASS;
		}

		Villager villager = (Villager)entity;
		VillagerData d = villager.getVillagerData();

		String rawProfession = d.getProfession().toString();
		if (rawProfession.equals("none") || rawProfession.equals("nitwit")) {
			return InteractionResult.PASS;
		}

		if (rawProfession.contains(":")) {
			rawProfession = rawProfession.split(":")[1];
		}
		if (rawProfession.contains("-")) {
			rawProfession = rawProfession.split("-")[0];
		}

		String translatableInput = "entity.minecraft.villager." + rawProfession.trim();
		Component profession = Component.translatable(translatableInput);

		if (translatableInput.equals(profession.getString())) {
			profession = Component.literal(rawProfession.substring(0, 1).toUpperCase() + rawProfession.substring(1));
		}

		Variables.tradedVillagerPair = new Pair<Component, Component>(villager.getName(), profession);
		return InteractionResult.PASS;
	}
}
