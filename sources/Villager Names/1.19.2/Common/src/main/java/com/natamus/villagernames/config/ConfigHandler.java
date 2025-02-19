/*
 * This is the latest source code of Villager Names.
 * Minecraft version: 1.19.2.
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

package com.natamus.villagernames.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.villagernames.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean useCustomNames = true;
	@Entry public static boolean useDefaultFemaleNames = true;
	@Entry public static boolean useDefaultMaleNames = true;
	@Entry public static boolean useBothCustomAndDefaultNames = false;
	@Entry public static boolean nameModdedVillagers = true;
	@Entry public static boolean showProfessionOnTradeScreen = true;
	@Entry public static boolean switchNameAndProfessionTradeScreen = false;
	@Entry public static boolean hideMerchantLevelTradeScreen = false;

	public static void initConfig() {
		configMetaData.put("useCustomNames", Arrays.asList(
			"Use the custom name list, editable in ./mods/villagernames/customnames.txt, seperated by a comma. If custom names are found, the default name list is ignored."
		));
		configMetaData.put("useDefaultFemaleNames", Arrays.asList(
			"Use the list of pre-defined female names when naming villagers."
		));
		configMetaData.put("useDefaultMaleNames", Arrays.asList(
			"Use the list of pre-defined male names when naming villagers."
		));
		configMetaData.put("useBothCustomAndDefaultNames", Arrays.asList(
			"Disabled by default. Whether both custom and default names should be used to name villagers. Custom names will probably not be chosen often due to the amount of default names."
		));
		configMetaData.put("nameModdedVillagers", Arrays.asList(
			"If enabled, also gives modded villagers a name. If you've found a 'villager'-entity that isn't named let me know by opening an issue so I can add it in."
		));
		configMetaData.put("showProfessionOnTradeScreen", Arrays.asList(
			"Whether the profession should be added to the villager's trade screen next to their name."
		));
		configMetaData.put("switchNameAndProfessionTradeScreen", Arrays.asList(
			"If enabled, switches the name and profession on the villager trading screen. Result: <profession> - <name>."
		));
		configMetaData.put("hideMerchantLevelTradeScreen", Arrays.asList(
			"Whether the merchant level (novice, apprentice etc.) should be hidden on the trade screen."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}