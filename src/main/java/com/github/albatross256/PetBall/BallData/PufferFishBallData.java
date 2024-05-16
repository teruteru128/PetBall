package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class PufferFishBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.PUFFERFISH;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.PUFFERFISH_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.PUFFERFISH;
  }
}
