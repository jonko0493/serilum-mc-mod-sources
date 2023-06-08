/*
 * This is the latest source code of Village Spawn Point.
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

package com.natamus.villagespawnpoint;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.fabric.callbacks.CollectiveMinecraftServerEvents;
import com.natamus.collective.fabric.data.GlobalFabricObjects;
import com.natamus.villagespawnpoint.events.VillageSpawnEvent;
import com.natamus.villagespawnpoint.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		CollectiveMinecraftServerEvents.WORLD_SET_SPAWN.register((ServerLevel serverLevel, ServerLevelData serverLevelData) -> {
			VillageSpawnEvent.onWorldLoad(serverLevel, serverLevelData);
		});
	}

	private static void setGlobalConstants() {
		GlobalFabricObjects.fabricLoader.isModLoaded("biomespawnpoint");
	}
}
