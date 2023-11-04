/*
 * This is the latest source code of Collective.
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

package com.natamus.collective.globalcallbacks;

import com.google.gson.JsonElement;
import com.natamus.collective.implementations.event.Event;
import com.natamus.collective.implementations.event.EventFactory;

import javax.annotation.Nullable;

public class JSONCallback {
	private JSONCallback() { }

    public static final Event<On_Json_File_Available> JSON_FILE_AVAILABLE = EventFactory.createArrayBacked(On_Json_File_Available.class, callbacks -> (folder, fileName, isCreated, jsonElement) -> {
        for (On_Json_File_Available callback : callbacks) {
        	callback.onJsonFileAvailable(folder, fileName, isCreated, jsonElement);
        }
    });

    public static final Event<All_Json_Files_Available> ALL_JSON_FILES_AVAILABLE = EventFactory.createArrayBacked(All_Json_Files_Available.class, callbacks -> (folder) -> {
        for (All_Json_Files_Available callback : callbacks) {
        	callback.onAllJsonFilesAvailable(folder);
        }
    });

	@FunctionalInterface
	public interface On_Json_File_Available {
		 void onJsonFileAvailable(String folder, String fileName, boolean isCreated, @Nullable JsonElement jsonElement);
	}

	@FunctionalInterface
	public interface All_Json_Files_Available {
		 void onAllJsonFilesAvailable(String folder);
	}
}
