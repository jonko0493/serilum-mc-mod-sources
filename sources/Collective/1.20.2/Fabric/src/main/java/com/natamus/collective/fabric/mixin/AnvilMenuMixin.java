/*
 * This is the latest source code of Collective.
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

package com.natamus.collective.fabric.mixin;

import com.natamus.collective.fabric.callbacks.CollectiveAnvilEvents;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Triplet;

@Mixin(value = AnvilMenu.class, priority = 1001)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
	public AnvilMenuMixin(MenuType<?> menuType, int i, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
		super(menuType, i, inventory, containerLevelAccess);
	}
	
	@Shadow private String itemName;
	@Shadow private int repairItemCountCost;
	@Final @Shadow private DataSlot cost;
	
	@Inject(method = "createResult()V", at = @At(value= "TAIL"))
	public void onCreateAnvilResult(CallbackInfo info) {
		AnvilMenu anvilmenu = (AnvilMenu)(Object)this;
		Container inputslots = this.inputSlots;
		
		ItemStack left = inputslots.getItem(0);
		ItemStack right = inputslots.getItem(1);
		ItemStack output = this.resultSlots.getItem(0);
		
		int baseCost = left.getBaseRepairCost() + (right.isEmpty() ? 0 : right.getBaseRepairCost());
		
		Triplet<Integer, Integer, ItemStack> triple = CollectiveAnvilEvents.ANVIL_CHANGE.invoker().onAnvilChange(anvilmenu, left, right, output, itemName, baseCost, this.player);
		if (triple == null) {
			return;
		}
		
		if (triple.getA() >= 0) {
			cost.set(triple.getA());
		}
		
		if (triple.getB() >= 0) {
			repairItemCountCost = triple.getB();
		}
		
		if (triple.getC() != null) {
			this.resultSlots.setItem(0, triple.getC());
		}
	}
}
