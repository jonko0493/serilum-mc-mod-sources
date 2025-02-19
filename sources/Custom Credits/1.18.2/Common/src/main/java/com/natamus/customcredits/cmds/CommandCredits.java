/*
 * This is the latest source code of Custom Credits.
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

package com.natamus.customcredits.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.natamus.customcredits.data.Constants;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;

public class CommandCredits {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("credits")
			.then(Commands.literal("show")
			.executes((command) -> {
				return show();
			}))
		);
	}

	public static int show() {
		Player player = Constants.mc.player;
		if (player == null) {
			return 0;
		}

		player.displayClientMessage(new TextComponent("Showing credits"), true);

		Constants.mc.tell(() -> Constants.mc.setScreen(new WinScreen(false, () -> {
			Constants.mc.setScreen(null);
		})));
		return 1;
	}
}