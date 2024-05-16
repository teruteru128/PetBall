package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ParrotBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.PARROT;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.PARROT_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.PARROT;
  }
}
