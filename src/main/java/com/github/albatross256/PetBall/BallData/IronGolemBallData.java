package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class IronGolemBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.IRON_GOLEM;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.IRON_GOLEM_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.IRON_GOLEM;
  }
}
