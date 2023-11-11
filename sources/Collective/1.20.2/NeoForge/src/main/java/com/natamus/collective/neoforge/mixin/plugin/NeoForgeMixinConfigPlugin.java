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

package com.natamus.collective.neoforge.mixin.plugin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class NeoForgeMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !(mixinClassName.contains(".neoforge.") && !isNeoForge()||
                mixinClassName.contains(".forge.") && !isForge() ||
                mixinClassName.contains(".fabric.") && !isFabric());
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    // Because NeoForge attempts to load Forge mixins, and can't have Forge code. Yep, it's hack-ish:
    private static boolean isFabric() {
        try { Class.forName("net.fabricmc.api.EnvType"); return true;
        } catch(ClassNotFoundException e) { return false; }
    }

    private static boolean isForge() {
        try { Class.forName("net.minecraftforge.fml.loading.FMLEnvironment"); return true;
        } catch(ClassNotFoundException e) { return false; }
    }

    private static boolean isNeoForge() {
        try { Class.forName("net.neoforged.fml.loading.FMLEnvironment"); return true;
        } catch(ClassNotFoundException e) { return false; }
    }
}