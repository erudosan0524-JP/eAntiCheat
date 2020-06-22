package com.github.jp.erudo.eanticheat.event.events.bukkitevents;

import org.bukkit.inventory.ItemStack;

import com.github.jp.erudo.eanticheat.event.EACEvent;

public class ItemConsumeEvent extends EACEvent {

    private ItemStack item;

    public ItemConsumeEvent(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

}
