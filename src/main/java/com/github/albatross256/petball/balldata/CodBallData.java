package com.github.albatross256.petball.balldata;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class CodBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.COD;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.COD_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.COD;
  }
}
