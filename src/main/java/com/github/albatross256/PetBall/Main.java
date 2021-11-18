

package com.github.albatross256.PetBall;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.albatross256.PetBall.EventListener.EventListener;

public class Main extends JavaPlugin{
	public BallManager ballManager;
	public WorldManager worldManager;

	@Override
    public void onEnable() {
		this.ballManager = new BallManager();
		this.worldManager = new WorldManager(this);
		this.getServer().getPluginManager().registerEvents(new EventListener(ballManager, worldManager), this);

		new BallRecipeManager(this, ballManager.getAllBallDatas()).init();
    }
}
