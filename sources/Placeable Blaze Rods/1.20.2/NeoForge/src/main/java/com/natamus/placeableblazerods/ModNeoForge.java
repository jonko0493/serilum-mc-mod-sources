/*
 * This is the latest source code of Placeable Blaze Rods.
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

package com.natamus.placeableblazerods;

import com.natamus.collective.check.RegisterMod;
import com.natamus.placeableblazerods.blocks.BlazeRodBlock;
import com.natamus.placeableblazerods.data.Constants;
import com.natamus.placeableblazerods.neoforge.events.NeoForgeBlazeRodEvent;
import com.natamus.placeableblazerods.util.Reference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

@Mod(Reference.MOD_ID)
public class ModNeoForge {

	public static final DeferredRegister<Block> BLOCKS_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MOD_ID);

	public static final RegistryObject<Block> BLAZE_ROD_BLOCK_OBJECT = BLOCKS_REGISTER.register("blaze_rod", () -> new BlazeRodBlock(BlockBehaviour.Properties.of().strength(0.0F).lightLevel((p_235454_0_) -> { return 14; }).sound(SoundType.WOOD).noOcclusion()));

	public static Block BLAZE_ROD_BLOCK;
	
	public ModNeoForge() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::loadComplete);

		BLOCKS_REGISTER.register(modEventBus);

		setGlobalConstants();
		ModCommon.init();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		Constants.BLAZE_ROD_BLOCK = (BlazeRodBlock)BLAZE_ROD_BLOCK_OBJECT.get();

		NeoForge.EVENT_BUS.register(NeoForgeBlazeRodEvent.class);
	}

	private static void setGlobalConstants() {

	}
}