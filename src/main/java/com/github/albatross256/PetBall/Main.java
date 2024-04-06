

package com.github.albatross256.PetBall;

import com.github.albatross256.PetBall.EventListener.EventListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public BallManager ballManager;
	public WorldManager worldManager;
	private BallRecipeManager ballRecipeManager;

	@Override
    public void onEnable() {
		this.ballManager = new BallManager();
		this.worldManager = new WorldManager(this);
		this.getServer().getPluginManager().registerEvents(new EventListener(ballManager, worldManager, this), this);

		ballRecipeManager = new BallRecipeManager(this, ballManager.getAllBallDatas());
		ballRecipeManager.init();
    }

    @Override
    public void onDisable() {
		super.onDisable();
		ballRecipeManager.disable();
    }
}
