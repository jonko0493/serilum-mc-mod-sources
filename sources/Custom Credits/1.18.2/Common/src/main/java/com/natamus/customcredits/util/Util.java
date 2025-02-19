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

package com.natamus.customcredits.util;

import com.natamus.collective.functions.DataFunctions;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;

public class Util {
	private static final Minecraft mc = Minecraft.getInstance();

	private static final String dirpath = DataFunctions.getConfigDirectory() + File.separator + Reference.MOD_ID;
	private static final File dir = new File(dirpath);

	public static final String creditsFilePath = dirpath + File.separator + "credits.json";
	public static final File creditsFile = new File(creditsFilePath);

	public static final String poemFilePath = dirpath + File.separator + "poem.txt";
	public static final File poemFile = new File(poemFilePath);

	public static final String postCreditsFilePath = dirpath + File.separator + "postcredits.txt";
	public static final File postCreditsFile = new File(postCreditsFilePath);

	public static void createDefaultFiles() throws IOException {
		if (!dir.isDirectory() || !creditsFile.isFile() || !poemFile.isFile() || !postCreditsFile.isFile()) {
			dir.mkdirs();

			if (!creditsFile.isFile()) {
				Resource creditsResource = mc.getResourceManager().getResource(new ResourceLocation("minecraft", "texts/credits.json"));
				byte[] bytes = IOUtils.toByteArray(creditsResource.getInputStream());

				File file = new File(creditsFilePath);
				FileUtils.writeByteArrayToFile(file, bytes);
			}

			if (!poemFile.isFile()) {
				Resource poemResource = mc.getResourceManager().getResource(new ResourceLocation("minecraft", "texts/end.txt"));
				byte[] bytes = IOUtils.toByteArray(poemResource.getInputStream());

				File file = new File(poemFilePath);
				FileUtils.writeByteArrayToFile(file, bytes);
			}

			if (!postCreditsFile.isFile()) {
				Resource postCreditsResource = mc.getResourceManager().getResource(new ResourceLocation("minecraft", "texts/postcredits.txt"));
				byte[] bytes = IOUtils.toByteArray(postCreditsResource.getInputStream());

				File file = new File(postCreditsFilePath);
				FileUtils.writeByteArrayToFile(file, bytes);
			}
		}
	}
}
