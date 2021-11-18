package com.github.albatross256.PetBall;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;

import com.github.albatross256.PetBall.BallData.BallData;
import com.github.albatross256.PetBall.BallData.BatBallData;
import com.github.albatross256.PetBall.BallData.BeeBallData;
import com.github.albatross256.PetBall.BallData.CatBallData;
import com.github.albatross256.PetBall.BallData.ChickenBallData;
import com.github.albatross256.PetBall.BallData.CodBallData;
import com.github.albatross256.PetBall.BallData.CowBallData;
import com.github.albatross256.PetBall.BallData.DolphinBallData;
import com.github.albatross256.PetBall.BallData.DonkeyBallData;
import com.github.albatross256.PetBall.BallData.FoxBallData;
import com.github.albatross256.PetBall.BallData.HorseBallData;
import com.github.albatross256.PetBall.BallData.IronGolemBallData;
import com.github.albatross256.PetBall.BallData.LlamaBallData;
import com.github.albatross256.PetBall.BallData.MuleBallData;
import com.github.albatross256.PetBall.BallData.MushroomBallData;
import com.github.albatross256.PetBall.BallData.OcelotBallData;
import com.github.albatross256.PetBall.BallData.PandaBallData;
import com.github.albatross256.PetBall.BallData.ParrotBallData;
import com.github.albatross256.PetBall.BallData.PigBallData;
import com.github.albatross256.PetBall.BallData.PiglinBallData;
import com.github.albatross256.PetBall.BallData.PolarBearBallData;
import com.github.albatross256.PetBall.BallData.PufferFishBallData;
import com.github.albatross256.PetBall.BallData.RabbitBallData;
import com.github.albatross256.PetBall.BallData.SalmonBallData;
import com.github.albatross256.PetBall.BallData.SheepBallData;
import com.github.albatross256.PetBall.BallData.SkeletonHorseBallData;
import com.github.albatross256.PetBall.BallData.SnowGolemBallData;
import com.github.albatross256.PetBall.BallData.SquidBallData;
import com.github.albatross256.PetBall.BallData.StriderBallData;
import com.github.albatross256.PetBall.BallData.TraderLlamaBallData;
import com.github.albatross256.PetBall.BallData.TropicalFishData;
import com.github.albatross256.PetBall.BallData.TurtleBallData;
import com.github.albatross256.PetBall.BallData.VillagerBallData;
import com.github.albatross256.PetBall.BallData.WanderingTraderBallData;
import com.github.albatross256.PetBall.BallData.WolfBallData;
import com.github.albatross256.PetBall.BallData.ZombieHorseBallData;

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
