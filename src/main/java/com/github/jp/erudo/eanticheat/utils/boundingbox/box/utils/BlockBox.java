package com.github.jp.erudo.eanticheat.utils.boundingbox.box.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.jp.erudo.eanticheat.utils.boundingbox.box.BoundingBox;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BlockBox {
    private Material material;
    private BoundingBox original;

    BlockBox(Material material, BoundingBox boundingBox) {
        this.material = material;
        this.original = boundingBox;
    }

    abstract List<BoundingBox> getBox(Block block);
}