package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class ZombieHorseBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.ZOMBIE_HORSE;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.ZOMBIE_HORSE_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.ZOMBIE_HORSE;
  }
}
