/*
 * This is the latest source code of Flower Mimics.
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

package com.natamus.flowermimics;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import com.natamus.collective.fabric.callbacks.CollectiveEntityEvents;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import com.natamus.flowermimics.data.MimicData;
import com.natamus.flowermimics.events.MimicEvent;
import com.natamus.flowermimics.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		ServerWorldEvents.LOAD.register((MinecraftServer server, ServerLevel level) -> {
			MimicData.attemptMimicConfigProcessing(level);
		});

		ServerTickEvents.START_WORLD_TICK.register((ServerLevel level) -> {
			MimicEvent.onLevelTick(level);
		});

		CollectivePlayerEvents.PLAYER_TICK.register((ServerLevel serverLevel, ServerPlayer serverPlayer) -> {
			MimicEvent.onPlayerTick(serverLevel, serverPlayer);
		});

		CollectiveEntityEvents.ON_ENTITY_IS_DROPPING_LOOT.register((Level level, Entity entity, DamageSource damageSource) -> {
			MimicEvent.mobItemDrop(level, entity, damageSource);
		});

		CollectiveBlockEvents.NEIGHBOUR_NOTIFY.register((Level world, BlockPos pos, BlockState state, EnumSet<Direction> notifiedSides, boolean forceRedstoneUpdate) -> {
			MimicEvent.onNeighbourNotice(world, pos, state, notifiedSides, forceRedstoneUpdate);
			return true;
		});
	}

	private static void setGlobalConstants() {

	}
}
