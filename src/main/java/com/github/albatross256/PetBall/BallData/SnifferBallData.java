package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SnifferBallData extends BallData{

	@Override
	public EntityType getEntityType() {
		return EntityType.SNIFFER;
	}

	@Override
	public Material getFilledBallMaterial() {
		return Material.SNIFFER_SPAWN_EGG;
	}

	@Override
	public EntityType getFilledBallEntityType() {
		return EntityType.SNIFFER;
	}
}
