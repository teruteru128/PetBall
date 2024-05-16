package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class GlowSquidBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.GLOW_SQUID;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.GLOW_SQUID_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.GLOW_SQUID;
  }
}
