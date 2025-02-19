/*
 * This is the latest source code of Surface Mushrooms.
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

package com.natamus.surfacemushrooms.forge.events;

import com.natamus.surfacemushrooms.events.MushroomBlockEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeMushroomBlockEvent {
    @SubscribeEvent
    public void onMushroomPlace(PlayerInteractEvent.RightClickBlock e) {
        if (!MushroomBlockEvent.onMushroomPlace(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec())) {
            e.setCanceled(true);
        }
    }
}