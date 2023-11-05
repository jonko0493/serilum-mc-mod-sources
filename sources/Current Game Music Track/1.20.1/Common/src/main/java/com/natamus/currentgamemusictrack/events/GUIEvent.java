/*
 * This is the latest source code of Current Game Music Track.
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

package com.natamus.currentgamemusictrack.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.currentgamemusictrack.config.ConfigHandler;
import com.natamus.currentgamemusictrack.data.Constants;
import com.natamus.currentgamemusictrack.data.Variables;
import com.natamus.currentgamemusictrack.util.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class GUIEvent {
	public static void renderOverlay(GuiGraphics guiGraphics, float tickDelta) {
		if (Constants.mc.options.renderDebug) {
			return;
		}

		if (Variables.guiOpacity <= 0) {
			return;
		}

		String musicGUIString = "";
		if (Variables.lastMusicResourceLocation != null) {
			musicGUIString = "🎵 " + Util.getMusicTitle(Variables.lastMusicResourceLocation) + " 🎵";
		}

		if (musicGUIString.equals("")) {
			return;
		}

		PoseStack poseStack = guiGraphics.pose();
		poseStack.pushPose();
		
		Font font = Constants.mc.font;
		Window scaled = Constants.mc.getWindow();

		int width = scaled.getGuiScaledWidth();
		int height = scaled.getGuiScaledHeight();

		if (Variables.guiOpacity > 255) {
			Variables.guiOpacity = 255;
		}

		Color colour = new Color(ConfigHandler.RGB_R, ConfigHandler.RGB_G, ConfigHandler.RGB_B, Variables.guiOpacity);

		int titleStringWidth = font.width(musicGUIString);

		int titleXCoord = 0;
		if (ConfigHandler.musicGUIPositionIsLeft) {
			titleXCoord = 5;
		}
		else if (ConfigHandler.musicGUIPositionIsCenter) {
			titleXCoord = (width/2) - (titleStringWidth/2);
		}
		else {
			titleXCoord = width - titleStringWidth - 5;
		}

		drawText(font, guiGraphics, musicGUIString, titleXCoord, height - ConfigHandler.musicGUIBottomHeightOffset, colour, ConfigHandler.drawTextShadow);
		
		poseStack.popPose();
	}

	public static void onClientTick(ClientLevel clientLevel) {
		if (Variables.fadeIn) {
			if (!ConfigHandler.titleShouldFadeIn) {
				Variables.guiOpacity = 255;
			}
			else if (Variables.guiOpacity < 255) {
				Variables.guiOpacity += 5;
				return;
			}

			Variables.fadeIn = false;
			return;
		}

		if (Variables.guiTicksLeft > 0) {
			Variables.guiTicksLeft -= 1;
			Variables.fadeOut = true;
			return;
		}

		if (Variables.fadeOut) {
			if (!ConfigHandler.titleShouldFadeIn) {
				Variables.guiOpacity = 0;
			}
			else if (Variables.guiOpacity > 0) {
				Variables.guiOpacity -= 5;
				return;
			}

			Variables.fadeOut = false;
		}
	}

	private static void drawText(Font font, GuiGraphics guiGraphics, String content, int x, int y, Color colour, boolean drawShadow) {
		if (Variables.guiOpacity > 10 && !content.equals("")) {
			guiGraphics.drawString(font, Component.literal(content), x, y, colour.getRGB(), drawShadow);
		}
	}
}