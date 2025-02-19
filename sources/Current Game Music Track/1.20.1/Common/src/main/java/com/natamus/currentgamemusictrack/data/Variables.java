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

package com.natamus.currentgamemusictrack.data;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;

public class Variables {
	public static SoundInstance lastPlayedMusic = null;
	public static ResourceLocation lastMusicResourceLocation = null; // .getPath()

	public static boolean fadeIn = false;
	public static boolean fadeOut = false;
	public static int guiOpacity = 0;
	public static int guiTicksLeft = 0;
}
