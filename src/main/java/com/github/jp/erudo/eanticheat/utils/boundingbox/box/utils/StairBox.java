package com.github.jp.erudo.eanticheat.utils.boundingbox.box.utils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;

import com.github.jp.erudo.eanticheat.utils.boundingbox.box.BoundingBox;
import com.github.jp.erudo.eanticheat.utils.boundingbox.box.ReflectionUtils;

public class StairBox extends BlockBox {

	public StairBox(Material material) {
		super(material, new BoundingBox(0, 0, 0, 1, 1, 1));
	}

	@Override
	List<BoundingBox> getBox(Block block) {

		Object vBlock = ReflectionUtils.getVanillaBlock(block);
		Object world = ReflectionUtils.getWorldHandle(block.getWorld());
		Method voxelShapeMethod = ReflectionUtils.getMethod(ReflectionUtils.getNMSClass("BlockStairs"), "a",
				ReflectionUtils.iBlockData, ReflectionUtils.iBlockAccess, ReflectionUtils.blockPosition);
		Object voxelShape = ReflectionUtils.getMethodValue(voxelShapeMethod, vBlock, ReflectionUtils.getBlockData(block),
				world, ReflectionUtils.getBlockPosition(block.getLocation()));

		return Collections.singletonList(ReflectionUtils
				.toBoundingBox(ReflectionUtils.getMethodValue(
						ReflectionUtils.getMethod(ReflectionUtils.getNMSClass("VoxelShape"), "a"), voxelShape))
				.add(block.getLocation().toVector()));
	}

}
