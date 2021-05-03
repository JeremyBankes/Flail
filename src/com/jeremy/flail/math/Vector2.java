package com.jeremy.flail.math;

public class Vector2 {

	public float x, y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
		this(0, 0);
	}

	public Vector2(Vector2 vector) {
		this(vector.x, vector.y);
	}

	public Vector2 set(Vector2 vector) {
		return set(vector.x, vector.y);
	}

	public Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2 add(Vector2 vector) {
		return add(vector.x, vector.y);
	}

	public Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public Vector2 subtract(Vector2 position) {
		x -= position.x;
		y -= position.y;
		return this;
	}

	public Vector2 scale(float scale) {
		x *= scale;
		y *= scale;
		return this;
	}

	public void reset() {
		x = y = 0;
	}

	@Override
	public String toString() {
		return String.format("[x: %.2f, y: %.2f]", x, y);
	}

	public boolean isZero() {
		return x == 0 && y == 0;
	}

}
