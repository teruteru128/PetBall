package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class LlamaBallData extends BallData {

  public boolean hasInventory = true;

  @Override
  public EntityType getEntityType() {
    return EntityType.LLAMA;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.LLAMA_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.LLAMA;
  }
}
