package com.jeremy.flail.math;

public class Bounds {

	public final Vector2 position;
	public final Vector2 size;

	public Bounds(float x, float y, float width, float height) {
		position = new Vector2(x, y);
		size = new Vector2(width, height);
	}

	public Bounds() {
		this(0, 0, 0, 0);
	}

	public Bounds(Vector2 position, Vector2 size) {
		this.position = new Vector2(position);
		this.size = new Vector2(size);
	}

	public Bounds(Bounds bounds) {
		this(bounds.position, bounds.size);
	}

	public Bounds set(Bounds bounds) {
		position.set(bounds.position);
		size.set(bounds.size);
		return this;
	}

	public void getRelativeTo(Vector2 position, Bounds result) {
		result.position.x += position.x;
		result.position.y += position.y;
	}

	public Bounds getRelativeTo(Vector2 position) {
		Bounds bounds = new Bounds(this);
		getRelativeTo(position, bounds);
		return bounds;
	}

	public boolean isOverlapping(Bounds bounds) {
		return position.x < bounds.position.x + bounds.size.x && position.x + size.x > bounds.position.x
				&& position.y < bounds.position.y + bounds.size.y && position.y + size.y > bounds.position.y;
	}

	@Override
	public String toString() {
		return String.format("[p: %s, s: %s]", position, size);
	}

}
