package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class TraderLlamaBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.TRADER_LLAMA;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.TRADER_LLAMA_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.TRADER_LLAMA;
  }
}
