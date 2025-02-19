/*
 * This is the latest source code of Areas.
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

package com.natamus.areas.fabric.services;

import com.natamus.areas.fabric.network.PacketToClientShowGUI;
import com.natamus.areas.objects.Variables;
import com.natamus.areas.services.helpers.PacketToClientHelper;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

public class FabricPacketToClientHelper implements PacketToClientHelper {
    @Override
    public void createGUIMessageBuffer(ServerPlayer serverPlayer, String message, String rgb) {
        ServerPlayNetworking.send(serverPlayer, Variables.networkchannel, PacketToClientShowGUI.createBuffer(message, rgb));
    }
}