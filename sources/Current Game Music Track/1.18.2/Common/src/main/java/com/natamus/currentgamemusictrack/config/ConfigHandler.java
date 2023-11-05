/*
 * This is the latest source code of Current Game Music Track.
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

package com.natamus.currentgamemusictrack.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.currentgamemusictrack.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 0, max = 1200) public static int durationTitleShownInTicks = 80;
	@Entry public static boolean titleShouldFadeIn = true;
	@Entry public static boolean titleShouldFadeOut = true;
	@Entry public static boolean musicGUIPositionIsLeft = false;
	@Entry public static boolean musicGUIPositionIsCenter = true;
	@Entry public static boolean musicGUIPositionIsRight = false;
	@Entry(min = 0, max = 3000) public static int musicGUIBottomHeightOffset = 72;
	@Entry(min = -3000, max = 3000) public static int musicGUIWidthOffset = 0;
	@Entry public static boolean drawTextShadow = true;
	@Entry(min = 0, max = 255) public static int RGB_R = 13;
	@Entry(min = 0, max = 255) public static int RGB_G = 120;
	@Entry(min = 0, max = 255) public static int RGB_B = 26;

	public static void initConfig() {
		configMetaData.put("durationTitleShownInTicks", Arrays.asList(
			"How long the music track title should be visible on the screen. 20 ticks = 1 second."
		));
		configMetaData.put("titleShouldFadeIn", Arrays.asList(
			"Whether the music track title should fade in."
		));
		configMetaData.put("titleShouldFadeOut", Arrays.asList(
			"Whether the music track title should fade out."
		));
		configMetaData.put("musicGUIPositionIsLeft", Arrays.asList(
			"Places the GUI music GUI on the left."
		));
		configMetaData.put("musicGUIPositionIsCenter", Arrays.asList(
			"Places the GUI music GUI in the middle."
		));
		configMetaData.put("musicGUIPositionIsRight", Arrays.asList(
			"Places the GUI music GUI on the right."
		));
		configMetaData.put("musicGUIBottomHeightOffset", Arrays.asList(
			"The vertical offset (y coord) for the music GUI. This determines how far up the text should be on the screen. Can be changed to prevent GUIs from overlapping."
		));
		configMetaData.put("musicGUIWidthOffset", Arrays.asList(
			"The horizontal offset (x coord) for the music GUI."
		));
		configMetaData.put("drawTextShadow", Arrays.asList(
			"If the text displayed should have a shadow drawn below it."
		));
		configMetaData.put("RGB_R", Arrays.asList(
			"The red RGB value for the music GUI text."
		));
		configMetaData.put("RGB_G", Arrays.asList(
			"The green RGB value for the music GUI text."
		));
		configMetaData.put("RGB_B", Arrays.asList(
			"The blue RGB value for the music GUI text."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}