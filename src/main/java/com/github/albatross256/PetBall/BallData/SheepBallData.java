package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SheepBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.SHEEP;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.SHEEP_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.SHEEP;
  }
}
