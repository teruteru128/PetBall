package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class CatBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.CAT;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.CAT_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.CAT;
  }
}
