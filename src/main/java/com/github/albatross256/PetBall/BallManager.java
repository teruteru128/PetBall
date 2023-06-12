package com.github.albatross256.PetBall;

import java.util.HashMap;
import java.util.Map;

import com.github.albatross256.PetBall.BallData.*;
import org.bukkit.entity.EntityType;

public class BallManager {

	Map<String, BallData> ballDatas;

	public BallManager() {
		this.ballDatas = new HashMap<String, BallData>();
		init();
	}

	private void init() {
		BallData[] ballDatas = {
				new VillagerBallData(),
				new CatBallData(),
				new OcelotBallData(),
				new ParrotBallData(),
				new WolfBallData(),
				new CowBallData(),
				new MushroomBallData(),
				new PigBallData(),
				new SheepBallData(),
				new ChickenBallData(),
				new RabbitBallData(),
				new PolarBearBallData(),
				new TurtleBallData(),
				new DolphinBallData(),
				new SquidBallData(),
				new IronGolemBallData(),
				new SnowGolemBallData(),
				new BatBallData(),
				new WanderingTraderBallData(),
				new PandaBallData(),
				new FoxBallData(),
				new LlamaBallData(),
				new MuleBallData(),
				new DonkeyBallData(),
				new CodBallData(),
				new SalmonBallData(),
				new TropicalFishData(),
				new PufferFishBallData(),
				new BeeBallData(),
				new HorseBallData(),
				new ZombieHorseBallData(),
				new SkeletonHorseBallData(),
				new TraderLlamaBallData(),
				new PiglinBallData(),
				new StriderBallData(),
				new AxolotlBallData(),
				new GoatBallData(),
				new GlowSquidBallData(),
				new AllayBallData(),
				new FrogBallData(),
				new TadpoleBallData(),
				new CamelBallData(),
				new SnifferBallData(),
		};

		for(BallData ballData : ballDatas) {
			registerBall(ballData);
		}
	}

	public void registerBall(BallData ballData) {
		this.ballDatas.put(ballData.getEntityType().toString(), ballData);
	}

	public Map<String, BallData> getAllBallDatas() {
		return this.ballDatas;
	}

	public BallData getBallData(EntityType entityType) {
		return this.ballDatas.get(entityType.toString());
	}


}
