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

package com.natamus.collective.fabric.mixin;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective.fabric.callbacks.CollectiveChatEvents;
import com.natamus.collective.functions.MessageFunctions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public abstract class ServerGamePacketListenerImplMixin {
    @Shadow public ServerPlayer player;

    @Inject(method = "method_45064(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;Ljava/lang/Void;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void handleChat(PlayerChatMessage playerChatMessage, CompletableFuture<?> completableFuture, CompletableFuture<?> completableFuture2, Void arg3, CallbackInfo ci, PlayerChatMessage playerChatMessage2) {
        Component message = Component.literal("<" + this.player.getName().getString() + "> " + playerChatMessage2.decoratedContent().getString());

        Pair<Boolean, Component> pair = CollectiveChatEvents.SERVER_CHAT_RECEIVED.invoker().onServerChat(this.player, message, player.getUUID());
        if (pair != null) {
            Component newMessage = pair.getSecond();

            MessageFunctions.broadcastMessage(this.player.getCommandSenderWorld(), (MutableComponent) newMessage);
            ci.cancel();
        }
    }
}
