/*
 * This is the latest source code of Collective.
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

package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerList.class, priority = 1001)
public class PlayerListMixin {
	@Inject(method = "placeNewPlayer", at = @At(value= "TAIL"))
	public void PlayerList_placeNewPlayer(Connection connection, ServerPlayer player, CallbackInfo ci) {
		CollectivePlayerEvents.PLAYER_LOGGED_IN.invoker().onPlayerLoggedIn(player.level(), player);
	}
	
	@Inject(method = "remove", at = @At(value= "HEAD"))
	public void PlayerList_remove(ServerPlayer player, CallbackInfo ci) {
		CollectivePlayerEvents.PLAYER_LOGGED_OUT.invoker().onPlayerLoggedOut(player.level(), player);
	}
}
