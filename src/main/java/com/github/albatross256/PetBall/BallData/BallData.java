package com.github.albatross256.PetBall.BallData;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public abstract class BallData {

  public static final String ENTITYBALL_CONTENT_KEY = "EntityBall";
  public static final String ENTITYBALL_CONTENT_EMPTY = "Empty";
  public static final String ENTITYBALL_NBT_KEY = "EntityBall_NBT";
  public static final String ENTITYBALL_ISC_KEY = "EntityBall_ISC";
  public static final String ENTITYBALL_TIMESTAMP_KEY = "EntityBallTimeStump";

  public static final String BALL_NAME = "Pet_Ball";

  public boolean hasInventory = false;

  public abstract EntityType getEntityType();

  public Material getEmptyBallMaterial() {
    return Material.ENDERMAN_SPAWN_EGG;
  }

  public abstract Material getFilledBallMaterial();

  public EntityType getEmptyBallEntityType() {
    return EntityType.ENDERMAN;
  }

  public abstract EntityType getFilledBallEntityType();

  public String getDisplayName() {
    return BALL_NAME;
  }

  public Material getRecipeMaterialA() {
    return Material.DRAGON_EGG;
  }

  public Material getRecipeMaterialB() {
    return Material.EMERALD_BLOCK;
  }

  public Material getRecipeMaterialC() {
    return Material.AIR;
  }

}