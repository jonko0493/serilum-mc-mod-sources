/*
 * This is the latest source code of Cycle Paintings.
 * Minecraft version: 1.20.0.
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

package com.natamus.cyclepaintings.events;

import com.natamus.cyclepaintings.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Collections;
import java.util.List;

public class PaintingEvent {
	public static InteractionResult onClick(Player player, Level level, InteractionHand hand, Entity target, EntityHitResult hitResult) {
		ItemStack handstack = player.getItemInHand(hand);
		if (!handstack.getItem().equals(Items.PAINTING)) {
			return InteractionResult.PASS;
		}
		
		if (!(target instanceof Painting)) {
			return InteractionResult.PASS;
		}
		
		Painting painting = (Painting)target;
		Holder<PaintingVariant> currentVariant = painting.getVariant();
		
		Holder<PaintingVariant> newVariant = null;
		
		List<Holder<PaintingVariant>> similarPaintingVariants = Util.getSimilarArt(currentVariant.value());
		if (similarPaintingVariants.size() == 0) {
			return InteractionResult.PASS;
		}

		if (player.isCrouching()) {
			Collections.reverse(similarPaintingVariants);
		}
		
		if (similarPaintingVariants.get(similarPaintingVariants.size()-1).equals(currentVariant)) {
			newVariant = similarPaintingVariants.get(0);
		}
		else {
			boolean choosenext = false;
			for (Holder<PaintingVariant> similarVariant : similarPaintingVariants) {
				if (choosenext) {
					newVariant = similarVariant;
					break;
				}
				if (similarVariant.equals(currentVariant)) {
					choosenext = true;
				}
			}
		}
		
		if (newVariant == null) {
			return InteractionResult.PASS;
		}

		painting.setVariant(newVariant);
		return InteractionResult.SUCCESS;
	}
}
