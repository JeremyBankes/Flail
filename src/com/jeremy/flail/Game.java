package com.jeremy.flail;

import com.jeremy.flail.assets.Assets;
import com.jeremy.flail.graphics.GameWindow;
import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.input.KeyInput;
import com.jeremy.flail.level.LevelUtilities;
import com.jeremy.flail.state.GameState;
import com.jeremy.flail.state.StateManager;
import com.jeremy.flail.ui.Overlay;

public class Game {

	public static final String TITLE = "Flail";
	public static final short TPS = 60; /* Ticks Per Second (How many times the game logic is updated per second) */
	public static final short FPS = 60; /* Frames per second */
	public static final short WIDTH = 384;
	public static final short HEIGHT = 288;
	public static final float ASPECT_RATIO = (float) WIDTH / HEIGHT;
	public static final float TICK_TIME = 1.0f / Game.TPS;

	private static Game instance; /* Singleton Game Instance */

	private GameWindow window;

	private boolean running;
	private Thread thread;

	private Game() {
		window = new GameWindow(TITLE, WIDTH, HEIGHT);
	}

	private void initialize() {
		Assets.getInstance().loadFont("/fonts/FreePixel.ttf");
		Assets.getInstance().loadImage("player", "/textures/entities/player.png");
		Assets.getInstance().loadImage("midground", "/textures/midground.png");
		Assets.getInstance().loadImage("background", "/textures/background.png");
		
		LevelUtilities.initiate();

		StateManager.getInstance().register(new GameState());
		StateManager.getInstance().enter(GameState.class);

		window.start();
	}

	private void terminate() {
		LevelUtilities.terminate();
		window.stop();
	}

	public void start() {
		if (!running) {
			thread = new Thread(this::run, "game-thread");
			running = true;
			thread.start();
		}
	}

	private void run() {
		initialize();
		final long second = 1000000000;
		long elapsedSinceLastTick, elapsedSinceLastFrame;
		long currentTime = System.nanoTime();
		long lastSecondTime = currentTime;
		long lastTickTime = lastSecondTime;
		long lastFrameTime = lastSecondTime;
		long tickTime = second / TPS;
		long frameTime = second / FPS;
		short ticks = 0, frames = 0;
		while (isRunning()) {
			if (!window.isValid()) {
				stop();
				continue;
			}
			currentTime = System.nanoTime();
			elapsedSinceLastTick = currentTime - lastTickTime;
			if (elapsedSinceLastTick > tickTime) {
				if (elapsedSinceLastTick > tickTime * 2) {
					System.out.printf("Game running behind. Skipped %.2f ticks.%n",
							(double) elapsedSinceLastTick / tickTime);
					lastTickTime = currentTime;
				} else {
					lastTickTime += tickTime;
				}
				tick();
				ticks++;
			}
			elapsedSinceLastFrame = currentTime - lastFrameTime;
			if (elapsedSinceLastFrame > frameTime) {
				lastFrameTime += frameTime;
				render();
				frames++;
			}
			if (currentTime - lastSecondTime > second) {
				System.out.printf("TPS: %d, FPS: %d%n", ticks, frames);
				ticks = frames = 0;
				lastSecondTime = currentTime;
			}
		}
		terminate();
	}

	private void tick() {
		StateManager.getInstance().tick();
		Overlay.getInstance().tick();
		getKeyInput().tick();
	}

	private void render() {
		final Renderer renderer = window.getRenderer();
		do {
			do {
				renderer.begin();
				StateManager.getInstance().render(renderer);
				Overlay.getInstance().render(renderer);
				renderer.end();
			} while (renderer.isRestored());
			renderer.display();
		} while (renderer.isLost());
	}

	public void stop() {
		if (running) {
			running = false;
		}
	}

	public boolean isRunning() {
		return running;
	}

	public GameWindow getWindow() {
		return window;
	}

	public KeyInput getKeyInput() {
		return getWindow().getKeyInput();
	}

	public static Game getInstance() {
		return instance == null ? instance = new Game() : instance;
	}

}
