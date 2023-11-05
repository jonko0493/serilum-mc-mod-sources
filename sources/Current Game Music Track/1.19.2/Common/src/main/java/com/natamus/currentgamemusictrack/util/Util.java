/*
 * This is the latest source code of Current Game Music Track.
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

package com.natamus.currentgamemusictrack.util;

import com.natamus.collective.functions.StringFunctions;
import com.natamus.currentgamemusictrack.config.ConfigHandler;
import com.natamus.currentgamemusictrack.data.Variables;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;

public class Util {
	public static String getMusicTitle(ResourceLocation musicResourceLocation) {
		if (musicResourceLocation == null) {
			return "";
		}

		String musicPath = musicResourceLocation.getPath();
		String[] mpspl = musicPath.split("\\.");

		String rawName = mpspl[mpspl.length-1];
		String name = rawName.replace("_", " ");

		return StringFunctions.capitalizeEveryWord(name);
	}

	public static void displaySongTitle(SoundInstance musicSoundInstance, ResourceLocation musicResourceLocation) {
		Variables.lastPlayedMusic = musicSoundInstance;
		Variables.lastMusicResourceLocation = musicResourceLocation;

		Variables.guiTicksLeft = ConfigHandler.durationTitleShownInTicks;
		Variables.fadeIn = true;
	}
}
