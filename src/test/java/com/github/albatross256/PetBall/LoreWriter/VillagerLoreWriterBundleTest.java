package com.github.albatross256.PetBall.LoreWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Test;

public class VillagerLoreWriterBundleTest {

  private ResourceBundle bundle = ResourceBundle.getBundle("com.github.albatross256.PetBall.LoreWriter.VillagerLoreWriter", Locale.JAPANESE);

  @Test
  void farmerTest() {
    assertEquals(bundle.getString("FARMER"), "農民");
  }

}
