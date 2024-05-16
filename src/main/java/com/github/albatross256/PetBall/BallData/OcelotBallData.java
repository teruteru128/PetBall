package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class OcelotBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.OCELOT;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.OCELOT_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.OCELOT;
  }
}
