/*
 * This is the latest source code of Better Spawner Control.
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

package com.natamus.betterspawnercontrol.forge.events;

import com.natamus.betterspawnercontrol.events.MobSpawnerEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeMobSpawnerEvent {
	@SubscribeEvent
	public void onMobSpawn(MobSpawnEvent.FinalizeSpawn e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		BaseSpawner msbl = e.getSpawner();
		if (msbl != null) {
			BlockEntity spawnerEntity = msbl.getSpawnerBlockEntity();
			if (spawnerEntity == null) {
				return;
			}

			BlockPos spawnerPos = spawnerEntity.getBlockPos();
			if (!(MobSpawnerEvent.onMobSpawn(e.getEntity(), (ServerLevel)level, spawnerPos, null))) {
				e.setSpawnCancelled(true);
				e.setCanceled(true);
			}
		}
	}
}
