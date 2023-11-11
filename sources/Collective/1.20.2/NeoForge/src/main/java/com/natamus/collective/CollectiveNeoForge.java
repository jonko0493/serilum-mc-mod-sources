/*
 * This is the latest source code of Collective.
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

package com.natamus.collective;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.neoforge.config.CollectiveConfigScreen;
import com.natamus.collective.neoforge.events.RegisterCollectiveNeoForgeEvents;
import com.natamus.collective.util.CollectiveReference;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;

@Mod(CollectiveReference.MOD_ID)
public class CollectiveNeoForge {
	public static CollectiveNeoForge instance;
	
    public CollectiveNeoForge() {
        instance = this;

        setGlobalConstants();
        CollectiveCommon.init();
        CollectiveConfigScreen.registerScreen(ModLoadingContext.get());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    	
        modEventBus.addListener(this::loadComplete);
        
        RegisterMod.register(CollectiveReference.NAME, CollectiveReference.MOD_ID, CollectiveReference.VERSION, CollectiveReference.ACCEPTED_VERSIONS);
    }
	
    private void loadComplete(final FMLLoadCompleteEvent event) {
    	NeoForge.EVENT_BUS.register(RegisterCollectiveNeoForgeEvents.class);
	}

    private static void setGlobalConstants() {

    }
}