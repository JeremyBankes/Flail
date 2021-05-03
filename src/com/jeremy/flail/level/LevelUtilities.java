package com.jeremy.flail.level;

import com.jeremy.flail.entity.Entity;
import com.jeremy.flail.entity.Player;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.world.ActionRegion;
import com.jeremy.flail.world.World;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LevelUtilities {
	
	private static ExecutorService executor;

	public static void initiate() {
		executor = Executors.newCachedThreadPool();
	}

	public static void terminate() {
		executor.shutdown();
	}

	public static final void loadWorld(String worldName) {
		World.getInstance().load(LevelUtilities.class
				.getResourceAsStream(String.format("/worlds/%s.world", new Object[] { worldName })));
	}

	public static final void async(Runnable runnable) {
		executor.execute(runnable);
	}

	public static final void delay(float seconds) {
		try {
			Thread.sleep(Math.round(1000.0F * seconds));
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}

	public static final void actionRegion(float x, float y, float width, float height, Runnable enter, Runnable exit) {
		World.getInstance().addActionRegion(new ActionRegion(new Bounds(x, y, width, height), enter, exit));
	}

	public static final void enterActionRegion(float x, float y, float width, float height, Runnable enter) {
		actionRegion(x, y, width, height, enter, null);
	}

	public static final void spawnPlayer() {
		World.getInstance().spawn((Entity) Player.getInstance());
	}
	
}
