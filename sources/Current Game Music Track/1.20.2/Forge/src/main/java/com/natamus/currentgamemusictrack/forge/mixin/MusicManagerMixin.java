/*
 * This is the latest source code of Current Game Music Track.
 * Minecraft version: 1.20.2.
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

package com.natamus.currentgamemusictrack.forge.mixin;

import com.natamus.currentgamemusictrack.data.Variables;
import com.natamus.currentgamemusictrack.util.Util;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MusicManager.class, priority = 1001)
public class MusicManagerMixin {
	@Shadow private SoundInstance currentMusic;

	@Inject(method = "tick()V", at = @At(value = "HEAD"))
	public void tick(CallbackInfo ci) {
		if (currentMusic != null) {
			ResourceLocation currentMusicRl = currentMusic.getLocation();
			if (currentMusicRl != Variables.lastMusicResourceLocation) {
				Util.displaySongTitle(currentMusic, currentMusicRl);
			}
		}
		else if (Variables.lastMusicResourceLocation != null) {
			Variables.lastMusicResourceLocation = null;
		}
	}
}