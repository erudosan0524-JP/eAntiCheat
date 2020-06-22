package com.github.jp.erudo.eanticheat.utils.boundingbox.box;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface BlockBox {
	List<BoundingBox> getCollidingBoxes(World world, BoundingBox box);

	List<BoundingBox> getSpecificBox(Location location);

	boolean isChunkLoaded(Location loc);

	boolean isUsingItem(Player player);

	boolean isRiptiding(LivingEntity entity);

	float getMovementFactor(Player player);

	@Deprecated
	int getTrackerId(Player player);

	float getAiSpeed(Player player);
}
