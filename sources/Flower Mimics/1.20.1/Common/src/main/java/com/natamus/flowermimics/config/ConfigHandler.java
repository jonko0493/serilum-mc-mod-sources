/*
 * This is the latest source code of Flower Mimics.
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

package com.natamus.flowermimics.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.flowermimics.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 0, max = 1.0) public static double flowerIsMimicChance = 0.02;
	@Entry public static int extraExperienceOnMimicDeath = 30;
	@Entry public static boolean dropExtraItemsOnMimicDeath = true;
	@Entry public static boolean onlyTransformMimicsAtNight = true;
	@Entry public static boolean resetMimicsBeforeNightTime = true;
	@Entry public static boolean removeFlowerBlockOnMimicSpawn = true;
	@Entry public static boolean dropFlowerItemOnMimicDeath = false;
	@Entry public static boolean replaceOriginalFlowerBlockOnMimicDeath = true;
	@Entry public static boolean placeFlowerBlockWhereMimicDies = false;
	@Entry public static boolean showLightningOnMimicSpawn = true;
	@Entry public static boolean showLightningOnFlowerBlockReturn = true;
	@Entry public static boolean preventMimicsOnPlacedAndBoneMealFlowers = true;
	@Entry(min = 1, max = 30) public static int checkForMimicAroundBlockRange = 15;
	@Entry(min = 1, max = 30) public static int mimicTransformAroundPlayerRange = 5;
	@Entry(min = 1, max = 3600000) public static int checkForMimicFlowersDelayInTicks = 50;

	public static void initConfig() {
		configMetaData.put("flowerIsMimicChance", Arrays.asList(
			"The chance an encountered flower is a mimic, and transforms into a mob."
		));
		configMetaData.put("extraExperienceOnMimicDeath", Arrays.asList(
			"The amount of extra experience that should drop whenever a mimic is killed."
		));
		configMetaData.put("dropExtraItemsOnMimicDeath", Arrays.asList(
			"Whether the items specified in ./config/flowermimics/.. should be dropped whenever a mimic is killed."
		));
		configMetaData.put("onlyTransformMimicsAtNight", Arrays.asList(
			"Whether flowers should only transform into mobs when it is night time. When disabled, they can always transform."
		));
		configMetaData.put("resetMimicsBeforeNightTime", Arrays.asList(
			"Whether mimic flower possibilities should be reset when the day turns into night."
		));
		configMetaData.put("removeFlowerBlockOnMimicSpawn", Arrays.asList(
			"Whether the flower block from which the mimic transforms should be removed on spawn."
		));
		configMetaData.put("dropFlowerItemOnMimicDeath", Arrays.asList(
			"If the mimic flower should be dropped on death. Must have 'removeFlowerBlockOnMimicSpawn' enabled."
		));
		configMetaData.put("replaceOriginalFlowerBlockOnMimicDeath", Arrays.asList(
			"If the original flower block should be replaced when a mimic dies. Must have 'removeFlowerBlockOnMimicSpawn' enabled. Destination must have an air block."
		));
		configMetaData.put("placeFlowerBlockWhereMimicDies", Arrays.asList(
			"If the a flower block should be placed where a mimic dies. Must have 'removeFlowerBlockOnMimicSpawn' enabled. Destination must have an air block."
		));
		configMetaData.put("showLightningOnMimicSpawn", Arrays.asList(
			"If lightning should be shown whenever a mimic spawns."
		));
		configMetaData.put("showLightningOnFlowerBlockReturn", Arrays.asList(
			"If lightning should be shown if a flower block is re-placed into the world."
		));
		configMetaData.put("preventMimicsOnPlacedAndBoneMealFlowers", Arrays.asList(
			"Whether manually placed and bone mealed flowers should not spawn mimics."
		));
		configMetaData.put("checkForMimicAroundBlockRange", Arrays.asList(
			"How many blocks away from the player the mod should check for mimics. Increasing this will take more processing power."
		));
		configMetaData.put("mimicTransformAroundPlayerRange", Arrays.asList(
			"How close the player should be to a mimic flower in order for it to transform."
		));
		configMetaData.put("checkForMimicFlowersDelayInTicks", Arrays.asList(
			"How often the mod should check for flower mimics around the player. 20 ticks = 1 second"
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}