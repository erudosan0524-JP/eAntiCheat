package com.github.jp.erudo.eanticheat.event.events.packetevents;

import org.bukkit.inventory.ItemStack;

import com.github.jp.erudo.eanticheat.event.EACEvent;

import io.github.erudo.eac.protocol.packet.types.BaseBlockPosition;

public class PlaceEvent extends EACEvent {

    private final BaseBlockPosition blockPos;
    private final ItemStack itemStack;

    public PlaceEvent(BaseBlockPosition blockPos, ItemStack itemStack) {
        this.blockPos = blockPos;
        this.itemStack = itemStack;
    }

    public BaseBlockPosition getBlockPos() {
        return blockPos;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
