package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SkeletonHorseBallData extends BallData {

  @Override
  public EntityType getEntityType() {
    return EntityType.SKELETON_HORSE;
  }

  @Override
  public Material getFilledBallMaterial() {
    return Material.SKELETON_HORSE_SPAWN_EGG;
  }

  @Override
  public EntityType getFilledBallEntityType() {
    return EntityType.SKELETON_HORSE;
  }
}
