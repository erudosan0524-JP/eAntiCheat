package com.github.jp.erudo.eanticheat.utils.boundingbox.box.utils;

import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.jp.erudo.eanticheat.utils.boundingbox.box.BoundingBox;

public class RegularBox  extends BlockBox{

	public RegularBox(Material material, BoundingBox original) {
        super(material, original);
    }

    @Override
    List<BoundingBox> getBox(Block block) {
        return Collections.singletonList(getOriginal().add(block.getLocation().toVector()));
    }
}
