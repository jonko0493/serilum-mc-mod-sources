/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.19.4.
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

package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;

public class CollectiveWorldEvents {
	private CollectiveWorldEvents() { }

    public static final Event<World_Unload> WORLD_UNLOAD = EventFactory.createArrayBacked(World_Unload.class, callbacks -> (serverLevel) -> {
        for (World_Unload callback : callbacks) {
        	callback.onWorldUnload(serverLevel);
        }
    });

	@FunctionalInterface
	public interface World_Unload {
		 void onWorldUnload(ServerLevel serverLevel);
	}
}
