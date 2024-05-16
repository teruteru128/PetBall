package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ArmadilloBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.ARMADILLO;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.ARMADILLO_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.ARMADILLO;
  }
}
