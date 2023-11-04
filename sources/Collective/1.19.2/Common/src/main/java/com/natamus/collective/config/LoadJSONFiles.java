/*
 * This is the latest source code of Collective.
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

package com.natamus.collective.config;

import com.natamus.collective.data.GlobalVariables;
import com.natamus.collective.functions.JsonFunctions;
import com.natamus.collective.globalcallbacks.JSONCallback;

public class LoadJSONFiles {
	public static void startListening() {
		JSONCallback.ALL_JSON_FILES_AVAILABLE.register((String folder) -> {
			GlobalVariables.areaNames = JsonFunctions.getStringListFromJsonFile(folder, "area_names.json", "area_names");
			GlobalVariables.femaleNames = JsonFunctions.getStringListFromJsonFile(folder, "entity_names.json", "female_names");
			GlobalVariables.maleNames = JsonFunctions.getStringListFromJsonFile(folder, "entity_names.json", "male_names");
			GlobalVariables.lingerMessages = JsonFunctions.getStringListFromJsonFile(folder, "linger_messages.json", "linger_messages");
		});
	}
}
