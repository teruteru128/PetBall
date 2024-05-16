package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class DonkeyBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.DONKEY;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.DONKEY_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.DONKEY;
  }
}
