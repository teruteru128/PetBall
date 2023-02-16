package com.github.albatross256.PetBall.LoreWriter.factory;

import com.github.albatross256.PetBall.LoreWriter.*;
import org.bukkit.entity.EntityType;

public class LoreWriterFactory {

	public LoreWriter newLoreWriter(EntityType entityType) {
		switch(entityType) {
		case VILLAGER:
			return new VillagerLoreWriter();
		case PARROT:
			return new ParrotLoreWriter();
		case CAT:
			return new CatLoreWriter();
		case OCELOT:
			return new OcelotLoreWriter();
		case WOLF:
			return new WolfLoreWriter();
		case COW:
			return new CowLoreWriter();
		case MUSHROOM_COW:
			return new MushroomLoreWriter();
		case PIG:
			return new PigLoreWriter();
		case SHEEP:
			return new SheepLoreWriter();
		case CHICKEN:
			return new ChickenLoreWriter();
		case RABBIT:
			return new RabbitLoreWriter();
		case POLAR_BEAR:
			return new PolarBearLoreWriter();
		case TURTLE:
			return new TurtleLoreWriter();
		case DOLPHIN:
			return new DolphinLoreWriter();
		case SQUID:
			return new SquidLoreWriter();
		case IRON_GOLEM:
			return new IronGolemLoreWriter();
		case SNOWMAN:
			return new SnowmanLoreWriter();
		case BAT:
			return new BatLoreWriter();
		case WANDERING_TRADER:
			return new WanderingTraderLoreWriter();
		case PANDA:
			return new PandaLoreWriter();
		case FOX:
			return new FoxLoreWriter();
		case LLAMA:
			return new LlamaLoreWriter();
		case MULE:
			return new MuleLoreWriter();
		case DONKEY:
			return new DonkeyLoreWriter();
		case COD:
			return new CodLoreWriter();
		case SALMON:
			return new SalmonLoreWriter();
		case TROPICAL_FISH:
			return new TropicalFishLoreWriter();
		case PUFFERFISH:
			return new PufferFishLoreWriter();
		case BEE:
			return new BeeLoreWriter();
		case HORSE:
			return new HorseLoreWriter();
		case ZOMBIE_HORSE:
			return new ZombieHorseLoreWriter();
		case SKELETON_HORSE:
			return new SkeletonHorseLoreWriter();
		case TRADER_LLAMA:
			return new TraderLlamaLoreWriter();
		case PIGLIN:
			return new PiglinLoreWriter();
		case STRIDER:
			return new StriderLoreWriter();
		case AXOLOTL:
			return new AxolotlLoreWriter();
		case GOAT:
			return new GoatLoreWriter();
		case GLOW_SQUID:
			return new GlowSquidLoreWriter();
		case ALLAY:
			return new AllayLoreWriter();
		case FROG:
			return new FrogLoreWriter();
		case TADPOLE:
			return new TadpoleLoreWriter();
		default:
			return null;
		}
	}
}
