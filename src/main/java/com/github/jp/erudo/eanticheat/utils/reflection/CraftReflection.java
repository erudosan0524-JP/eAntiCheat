package com.github.jp.erudo.eanticheat.utils.reflection;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.erudo.eac.protocol.api.channel.reflections.Reflections;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedClass;
import io.github.erudo.eac.protocol.api.channel.reflections.WrappedMethod;

public class CraftReflection {
	public static WrappedClass craftHumanEntity;
    public static WrappedClass craftEntity;
    public static WrappedClass craftItemStack;
    public static WrappedClass craftBlock;
    public static WrappedClass craftWorld;
    public static WrappedClass craftInventoryPlayer;

    //Vanilla Instances
    public static WrappedMethod itemStackInstance;
    public static WrappedMethod humanEntityInstance;
    public static WrappedMethod entityInstance;
    public static WrappedMethod blockInstance;
    public static WrappedMethod worldInstance;
    public static WrappedMethod bukkitEntity;
    public static WrappedMethod getInventory;

    static {
    	try {
    		craftHumanEntity = Reflections.getCBClass("entity.CraftHumanEntity");
    		craftEntity = Reflections.getCBClass("entity.CraftEntity");
    		craftItemStack = Reflections.getCBClass("inventory.CraftItemStack");
    		craftBlock = Reflections.getCBClass("block.CraftBlock");
    		craftWorld = Reflections.getCBClass("CraftWorld");
    		craftInventoryPlayer = Reflections.getCBClass("inventory.CraftInventoryPlayer");
    		itemStackInstance = craftItemStack.getMethod("asNMSCopy", ItemStack.class);
    		humanEntityInstance = craftHumanEntity.getMethod("getHandle");
    		entityInstance = craftEntity.getMethod("getHandle");
    		blockInstance = craftBlock.getMethod("getNMSBlock");
    		worldInstance = craftWorld.getMethod("getHandle");
    		bukkitEntity = MinecraftReflection.entity.getMethod("getBukkitEntity");
    		getInventory = craftInventoryPlayer.getMethod("getInventory");
    	}catch(Throwable e) {
    		e.printStackTrace();
    	}
    }

    public static <T> T getVanillaItemStack(ItemStack stack) {
        return itemStackInstance.invoke(null, stack);
    }

    public static <T> T getEntityHuman(HumanEntity entity) {
        return humanEntityInstance.invoke(entity);
    }

    public static <T> T getEntity(Entity entity) {
        return entityInstance.invoke(entity);
    }

    public static <T> T getVanillaBlock(Block block) {
        return blockInstance.invoke(block);
    }

    public static <T> T getVanillaWorld(World world) {
        return worldInstance.invoke(world);
    }


    //TODO: なぜかここでnullが出るのでその改善
    public static Entity getBukkitEntity(Object vanillaEntity) {
        return bukkitEntity.invoke(vanillaEntity);
    }

    public static <T> T getVanillaInventory(Player player) {
        return getInventory.invoke(player.getInventory());
    }
}
