package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class TropicalFishData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.TROPICAL_FISH;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.TROPICAL_FISH_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.TROPICAL_FISH;
  }
}
