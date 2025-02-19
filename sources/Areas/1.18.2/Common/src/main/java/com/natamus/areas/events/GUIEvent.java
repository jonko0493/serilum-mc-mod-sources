/*
 * This is the latest source code of Areas.
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

package com.natamus.areas.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.areas.config.ConfigHandler;
import com.natamus.areas.data.ClientConstants;
import com.natamus.areas.data.GUIVariables;
import net.minecraft.client.gui.Font;
import net.minecraft.util.FastColor;

public class GUIEvent {
	public static void renderOverlay(PoseStack poseStack, float tickDelta) {
		if (!GUIVariables.hudMessage.equals("")) {
			Font font = ClientConstants.mc.font;
			Window scaled = ClientConstants.mc.getWindow();
			int width = scaled.getGuiScaledWidth();

			double stringWidth = font.width(GUIVariables.hudMessage);
			
			if (GUIVariables.guiOpacity <= 0) {
				GUIVariables.guiOpacity = 0;
				GUIVariables.hudMessage = "";
				return;
			}
			else if (GUIVariables.guiOpacity > 255) {
				GUIVariables.guiOpacity = 255;
			}

			int colour = FastColor.ARGB32.color(GUIVariables.guiOpacity, ConfigHandler.HUD_RGB_R, ConfigHandler.HUD_RGB_G, ConfigHandler.HUD_RGB_B);
			if (!GUIVariables.rgb.equals("")) {
				String[] rgbs = GUIVariables.rgb.split(",");
				if (rgbs.length == 3) {
					try {
						int r = Integer.parseInt(rgbs[0]);
						if (r < 0) {
							r = 0;
						}
						else if (r > 255) {
							r = 255;
						}
						
						int g = Integer.parseInt(rgbs[1]);
						if (g < 0) {
							g = 0;
						}
						else if (g > 255) {
							g = 255;
						}
						
						int b = Integer.parseInt(rgbs[2]);
						if (b < 0) {
							b = 0;
						}
						else if (b > 255) {
							b = 255;
						}
						
						colour = FastColor.ARGB32.color(GUIVariables.guiOpacity, r, g, b);
					}
					catch(IllegalArgumentException ex) {
						GUIVariables.rgb = "";
						GUIVariables.hudMessage = "";
					}
				}
			}

			poseStack.pushPose();
			
			float modifier = ((float)ConfigHandler.HUD_FontSizeScaleModifier + 0.5F);
			poseStack.scale(modifier, modifier, modifier);

			int x = (int)(Math.round((width / 2F) / modifier) - stringWidth / 2);
			if (ConfigHandler.showHUDTextShadow) {
				font.drawShadow(poseStack, GUIVariables.hudMessage, x, ConfigHandler.HUDMessageHeightOffset, colour);
			}
			else {
				font.draw(poseStack, GUIVariables.hudMessage, x, ConfigHandler.HUDMessageHeightOffset, colour);
			}

			poseStack.popPose();
			
			if (!GUIVariables.currentMessage.equals(GUIVariables.hudMessage)) {
				GUIVariables.currentMessage = GUIVariables.hudMessage;
				GUIVariables.ticksLeftBeforeFade = ConfigHandler.HUDMessageFadeDelayMs/50;
			}
		}
	}

	public static void tickHUDFade() {
		if (GUIVariables.ticksLeftBeforeFade == 0) {
			GUIVariables.ticksLeftBeforeFade = -1;
		}
		else if (GUIVariables.ticksLeftBeforeFade > 0) {
			GUIVariables.ticksLeftBeforeFade -= 1;
		}
		else if (!GUIVariables.hudMessage.equals("")) {
			if (GUIVariables.guiOpacity < 0) {
				GUIVariables.hudMessage = "";
				GUIVariables.rgb = "";
				return;
			}

			GUIVariables.guiOpacity -= 5;
		}
	}
}