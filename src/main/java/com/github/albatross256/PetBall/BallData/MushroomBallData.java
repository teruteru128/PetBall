package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class MushroomBallData extends BallData{

	@Override
	public EntityType getEntityType() {
		return EntityType.MUSHROOM_COW;
	}

	@Override
	public Material getFilledBallMaterial() {
		return Material.MOOSHROOM_SPAWN_EGG;
	}

	@Override
	public EntityType getFilledBallEntityType() {
		return EntityType.MUSHROOM_COW;
	}
}
