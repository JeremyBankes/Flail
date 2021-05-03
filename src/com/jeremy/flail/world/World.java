package com.jeremy.flail.world;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.UUID;

import com.jeremy.flail.Game;
import com.jeremy.flail.assets.Assets;
import com.jeremy.flail.entity.Entity;
import com.jeremy.flail.entity.MagicalText;
import com.jeremy.flail.entity.Player;
import com.jeremy.flail.graphics.Camera;
import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.math.Anchor;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.math.Vector2;

public class World {

	private static World instance;

	private final LinkedHashMap<UUID, Entity> entities = new LinkedHashMap<>();
	private final HashSet<ActionRegion> actionRegions = new HashSet<>();

	private int worldWidth;
	private int worldHeight;
	private BufferedImage background;
	private BufferedImage midground;
	private Tile[] tiles;
	private long timeInTicks;

	private final Vector2 spawnPoint = new Vector2();

	public World() {
		background = Assets.getInstance().getImage("background");
		midground = Assets.getInstance().getImage("midground");
	}

	public synchronized void load(InputStream inputStream) {
		worldWidth = worldHeight = 0;
		entities.clear();
		actionRegions.clear();
		boolean readingTiles = true;
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.equals("-")) {
					readingTiles = false;
				} else {
					if (readingTiles) {
						lines.add(0, line);
						if (line.length() > worldWidth) {
							worldWidth = line.length();
						}
						worldHeight++;
					} else {
						if (line.startsWith("spawn")) {
							String[] pieces = line.split(" +", 3);
							if (pieces.length == 3) {
								setSpawnPoint(Float.parseFloat(pieces[1]), Float.parseFloat(pieces[2]));
							}
						} else if (line.startsWith("text")) {
							String[] pieces = line.split(" +", 4);
							if (pieces.length == 4) {
								String text = pieces[3].replace("\\n", "\n");
								spawnText(text, Float.parseFloat(pieces[1]), Float.parseFloat(pieces[2]));
							}
						}
					}
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		tiles = new Tile[worldWidth * worldHeight];
		for (int y = 0; y < worldHeight; y++) {
			for (int x = 0; x < worldWidth; x++) {
				String tileData = lines.get(y);
				Bounds bounds = new Bounds(x, y, 1.0f, 1.0f);
				if (tileData.length() > x) {
					tiles[y * worldWidth + x] = new Tile(TileType.get(tileData.charAt(x)), bounds);
				} else {
					tiles[y * worldWidth + x] = new Tile(TileType.AIR, bounds);
				}
			}
		}
		buildDeathRegions();
	}

	private void buildDeathRegions() {
		final Runnable deathAction = () -> Player.getInstance().die();
		Tile tile;
		ActionRegion region;
		for (int y = 0; y < worldHeight; y++) {
			for (int x = 0; x < worldWidth; x++) {
				tile = tiles[y * worldWidth + x];
				if (tile.type == TileType.SPIKES) {
					Bounds bounds = new Bounds(tile.getBounds());
					Anchor.CENTER.effect(tile.getBounds(), bounds);
					bounds.size.y /= 4;
					region = new ActionRegion(bounds);
					region.setEnter(deathAction);
					addActionRegion(region);
				}
			}
		}
	}

	public void spawn(Entity entity, float x, float y) {
		entities.put(entity.getId(), entity);
		entity.bounds.position.set(x, y);
	}

	public void spawn(Entity entity) {
		spawn(entity, getSpawnPoint().x, getSpawnPoint().y);
	}

	public void spawnText(String text, float x, float y) {
		spawn(new MagicalText(text), x, y);
	}

	public void addActionRegion(ActionRegion region) {
		actionRegions.add(region);
	}

	public Vector2 getSpawnPoint() {
		return spawnPoint;
	}

	public void setSpawnPoint(float x, float y) {
		this.spawnPoint.set(x, y);
	}

	public Tile getTile(int x, int y) {
		int index = y * worldWidth + x;
		if (index < 0 || index >= tiles.length) {
			return null;
		}
		return tiles[index];
	}

	public int getWidth() {
		return worldWidth;
	}

	public int getHeight() {
		return worldHeight;
	}

	public synchronized void tick() {
		for (int y = 0; y < worldHeight; y++) {
			for (int x = 0; x < worldWidth; x++) {
				tiles[y * worldWidth + x].tick();
			}
		}
		entities.values().forEach(Entity::tick);
		actionRegions.forEach(ActionRegion::tick);
		timeInTicks += 1;
	}

	public synchronized void render(Renderer renderer) {
		renderBackground(renderer);
		renderTiles(renderer);
		renderEntities(renderer);
		renderActionRegions(renderer);
	}

	private void renderBackground(Renderer renderer) {
		final Camera camera = renderer.getCamera();
		Point cameraPoint = camera.transform(camera.position, true);
		int backgroundOffset = -cameraPoint.x / 4;
		int midgroundOffset = -cameraPoint.x / 2 - (int) (timeInTicks / 32);
		renderer.draw(background, backgroundOffset % Game.WIDTH, 0, Game.WIDTH, Game.HEIGHT);
		renderer.draw(background, backgroundOffset % Game.WIDTH + Game.WIDTH, 0, Game.WIDTH, Game.HEIGHT);

		renderer.draw(midground, midgroundOffset % Game.WIDTH, 0, Game.WIDTH, Game.HEIGHT);
		renderer.draw(midground, midgroundOffset % Game.WIDTH + Game.WIDTH, 0, Game.WIDTH, Game.HEIGHT);
	}

	private void renderTiles(Renderer renderer) {
		Vector2 topLeft = renderer.getCamera().transform(new Point(0, 0), false);
		Vector2 bottomRight = renderer.getCamera().transform(new Point(renderer.getCanvasWidth(), renderer.getCanvasHeight()), false);
		for (int y = max(0, (int) floor(bottomRight.y)); y < min(worldHeight, ceil(topLeft.y + 1)); y++) {
			for (int x = max(0, (int) floor(topLeft.x)); x < min(worldWidth, ceil(bottomRight.x + 1)); x++) {
				tiles[y * worldWidth + x].render(renderer);
			}
		}
	}

	private void renderEntities(Renderer renderer) {
		entities.values().forEach(entity -> entity.render(renderer));
	}

	private void renderActionRegions(Renderer renderer) {
		actionRegions.forEach(region -> region.render(renderer));
	}

	public static World getInstance() {
		return instance == null ? instance = new World() : instance;
	}

}
