package com.github.albatross256.petball.lorewriter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bukkit.entity.Villager;
import org.junit.jupiter.api.Test;

public class VillagerLoreWriterBundleTest {

  private static String BASE_NAME = "com.github.albatross256.petball.lorewriter.VillagerLoreWriter";
  private ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, Locale.JAPANESE);

  @Test
  void noneTest() {
    assertEquals(bundle.getString(Villager.Profession.NONE.name()), "ニート");
  }

  @Test
  void armorerTest() {
    assertEquals(bundle.getString(Villager.Profession.ARMORER.name()), "防具鍛冶");
  }

  @Test
  void butcherTest() {
    assertEquals(bundle.getString(Villager.Profession.BUTCHER.name()), "肉屋");
  }

  @Test
  void cartographerTest() {
    assertEquals(bundle.getString(Villager.Profession.CARTOGRAPHER.name()), "製図家");
  }

  @Test
  void clericTest() {
    assertEquals(bundle.getString(Villager.Profession.CLERIC.name()), "聖職者");
  }

  @Test
  void farmerTest() {
    assertEquals(bundle.getString(Villager.Profession.FARMER.name()), "農民");
  }

  @Test
  void fishermanTest() {
    assertEquals(bundle.getString(Villager.Profession.FISHERMAN.name()), "釣り人");
  }

  @Test
  void fletcherTest() {
    assertEquals(bundle.getString(Villager.Profession.FLETCHER.name()), "矢師");
  }

  @Test
  void leatherworkerTest() {
    assertEquals(bundle.getString(Villager.Profession.LEATHERWORKER.name()), "革細工師");
  }

  @Test
  void librarianTest() {
    assertEquals(bundle.getString(Villager.Profession.LIBRARIAN.name()), "司書");
  }

  @Test
  void masonTest() {
    assertEquals(bundle.getString(Villager.Profession.MASON.name()), "石工職人");
  }

  @Test
  void nitwitTest() {
    assertEquals(bundle.getString(Villager.Profession.NITWIT.name()), "ニート");
  }

  @Test
  void shepherdTest() {
    assertEquals(bundle.getString(Villager.Profession.SHEPHERD.name()), "羊飼い");
  }

  @Test
  void weaponsmithTest() {
    assertEquals(bundle.getString(Villager.Profession.WEAPONSMITH.name()), "武器鍛冶");
  }

  @Test
  void toolsmithTest() {
    assertEquals(bundle.getString(Villager.Profession.TOOLSMITH.name()), "道具鍛冶");
  }

  @Test
  public void checkAllEnums() {
    for (Locale locale : List.of(Locale.JAPANESE, Locale.ENGLISH)) {
      ResourceBundle b = ResourceBundle.getBundle(BASE_NAME, locale);
      for (Villager.Profession iterable_element : Villager.Profession.values()) {
        assertDoesNotThrow(() -> {
          b.getString(iterable_element.name());
        });
      }
    }
  }

}
