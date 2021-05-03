package com.jeremy.flail.world;

import java.awt.Color;

import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.math.Anchor;
import com.jeremy.flail.math.Bounds;

public class Tile {

	public final TileType type;
	private Bounds bounds;

	public Tile(TileType type, Bounds bounds) {
		this.type = type;
		this.bounds = bounds;
	}

	public void tick() {

	}

	public void render(Renderer renderer) {
		renderer.draw(type.texture, bounds, Anchor.CENTER);
		renderer.setColor(Color.WHITE);
//		renderer.draw(String.format("(%.0f, %.0f)", bounds.position.x, bounds.position.y), new Vector2(bounds.position.x - 0.25f, bounds.position.y));
	}

	public boolean isSolid() {
		return type.solid;
	}

	public Bounds getBounds() {
		return new Bounds(bounds);
	}
}
