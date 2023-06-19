/*
 * This is the latest source code of Pumpkillager's Quest.
 * Minecraft version: 1.20.1.
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

package com.natamus.pumpkillagersquest.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.pumpkillagersquest.events.PkBlockEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForgePkBlockEvents {
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (level.isClientSide) {
            return;
        }

        if (!PkBlockEvents.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onCandleClick(PlayerInteractEvent.RightClickBlock e) {
        if (!PkBlockEvents.onCandleClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec())) {
            e.setCanceled(true);
            e.setCancellationResult(InteractionResult.FAIL);
        }
    }

    @SubscribeEvent
    public void onBlockPlace(BlockEvent.EntityPlaceEvent e) {
        Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
        if (level == null) {
            return;
        }

        Entity entity = e.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        PkBlockEvents.onBlockPlace(level, e.getPos(), e.getPlacedBlock(), (LivingEntity)entity, null);
    }
}
