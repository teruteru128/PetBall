package com.github.albatross256.PetBall.LoreWriter.factory;

import org.bukkit.entity.EntityType;

import com.github.albatross256.PetBall.LoreWriter.BatLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.BeeLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.CatLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.ChickenLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.CodLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.CowLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.DolphinLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.DonkeyLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.FoxLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.HorseLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.IronGolemLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.LlamaLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.LoreWriter;
import com.github.albatross256.PetBall.LoreWriter.MuleLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.MushroomLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.OcelotLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.PandaLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.ParrotLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.PigLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.PiglinLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.PolarBearLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.PufferFishLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.RabbitLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.SalmonLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.SheepLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.SkeletonHorseLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.SnowmanLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.SquidLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.StriderLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.TraderLlamaLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.TropicalFishLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.TurtleLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.VillagerLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.WanderingTraderLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.WolfLoreWriter;
import com.github.albatross256.PetBall.LoreWriter.ZombieHorseLoreWriter;

public class LoreWriterFactory {

	public LoreWriter newLoreWriter(EntityType entityType) {
		switch(entityType.toString()) {
		case "VILLAGER":
			return new VillagerLoreWriter();
		case "PARROT":
			return new ParrotLoreWriter();
		case "CAT":
			return new CatLoreWriter();
		case "OCELOT":
			return new OcelotLoreWriter();
		case "WOLF":
			return new WolfLoreWriter();
		case "COW":
			return new CowLoreWriter();
		case "MUSHROOM_COW":
			return new MushroomLoreWriter();
		case "PIG":
			return new PigLoreWriter();
		case "SHEEP":
			return new SheepLoreWriter();
		case "CHICKEN":
			return new ChickenLoreWriter();
		case "RABBIT":
			return new RabbitLoreWriter();
		case "POLAR_BEAR":
			return new PolarBearLoreWriter();
		case "TURTLE":
			return new TurtleLoreWriter();
		case "DOLPHIN":
			return new DolphinLoreWriter();
		case "SQUID":
			return new SquidLoreWriter();
		case "IRON_GOLEM":
			return new IronGolemLoreWriter();
		case "SNOWMAN":
			return new SnowmanLoreWriter();
		case "BAT":
			return new BatLoreWriter();
		case "WANDERING_TRADER":
			return new WanderingTraderLoreWriter();
		case "PANDA":
			return new PandaLoreWriter();
		case "FOX":
			return new FoxLoreWriter();
		case "LLAMA":
			return new LlamaLoreWriter();
		case "MULE":
			return new MuleLoreWriter();
		case "DONKEY":
			return new DonkeyLoreWriter();
		case "COD":
			return new CodLoreWriter();
		case "SALMON":
			return new SalmonLoreWriter();
		case "TROPICAL_FISH":
			return new TropicalFishLoreWriter();
		case "PUFFERFISH":
			return new PufferFishLoreWriter();
		case "BEE":
			return new BeeLoreWriter();
		case "HORSE":
			return new HorseLoreWriter();
		case "ZOMBIE_HORSE":
			return new ZombieHorseLoreWriter();
		case "SKELETON_HORSE":
			return new SkeletonHorseLoreWriter();
		case "TRADER_LLAMA":
			return new TraderLlamaLoreWriter();
		case "PIGLIN":
			return new PiglinLoreWriter();
		case "STRIDER":
			return new StriderLoreWriter();
		}
		return null;
	}
}
