/*
 * This is the latest source code of Cycle Paintings.
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

package com.natamus.cyclepaintings.events;

import com.natamus.cyclepaintings.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.Collections;
import java.util.List;

public class PaintingEvent {
	public static InteractionResult onClick(Player player, Level world, InteractionHand hand, Entity target, EntityHitResult hitResult) {
		ItemStack handstack = player.getItemInHand(hand);
		if (!handstack.getItem().equals(Items.PAINTING)) {
			return InteractionResult.PASS;
		}

		if (!(target instanceof Painting)) {
			return InteractionResult.PASS;
		}

		Painting painting = (Painting)target;
		Motive currentVariant = painting.motive;

		Holder<Motive> newVariant = null;

		List<Holder<Motive>> similarPaintingVariants = Util.getSimilarArt(currentVariant);
		if (similarPaintingVariants.size() == 0) {
			return InteractionResult.PASS;
		}
		
		if (player.isCrouching()) {
			Collections.reverse(similarPaintingVariants);
		}

		if (similarPaintingVariants.get(similarPaintingVariants.size()-1).value().equals(currentVariant)) {
			newVariant = similarPaintingVariants.get(0);
		}
		else {
			boolean choosenext = false;
			for (Holder<Motive> similarVariant : similarPaintingVariants) {
				if (choosenext) {
					newVariant = similarVariant;
					break;
				}
				if (similarVariant.value().equals(currentVariant)) {
					choosenext = true;
				}
			}
		}

		if (newVariant == null) {
			return InteractionResult.PASS;
		}

		BlockPos ppos = painting.getPos();
		Painting newpainting = new Painting(world, ppos, painting.getMotionDirection(), newVariant.value());

		newpainting.setPos(ppos.getX(), ppos.getY(), ppos.getZ());

		painting.remove(Entity.RemovalReason.DISCARDED);
		world.addFreshEntity(newpainting);
		return InteractionResult.SUCCESS;
	}
}
