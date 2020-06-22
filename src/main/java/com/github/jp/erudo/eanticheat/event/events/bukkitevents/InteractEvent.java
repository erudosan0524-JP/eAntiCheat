package com.github.jp.erudo.eanticheat.event.events.bukkitevents;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import com.github.jp.erudo.eanticheat.event.EACEvent;

public class InteractEvent extends EACEvent {

    private Action action;
    private ItemStack item;
    private Block blockClicked;
    private BlockFace blockFace;
    private org.bukkit.event.Event.Result useItemInHand;
    private org.bukkit.event.Event.Result useClickedBlock;

    public InteractEvent(Action action, ItemStack item, Block blockClicked, BlockFace blockFace, org.bukkit.event.Event.Result useItemInHand, org.bukkit.event.Event.Result useClickedBlock) {
        this.action = action;
        this.item = item;
        this.blockClicked = blockClicked;
        this.blockFace = blockFace;
        this.useItemInHand = useItemInHand;
        this.useClickedBlock = useClickedBlock;
    }

    public Action getAction() {
        return action;
    }

    public ItemStack getItem() {
        return item;
    }

    public Block getBlockClicked() {
        return blockClicked;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public org.bukkit.event.Event.Result getUseItemInHand() {
        return useItemInHand;
    }

    public org.bukkit.event.Event.Result getUseClickedBlock() {
        return useClickedBlock;
    }

}
