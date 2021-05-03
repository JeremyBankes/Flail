package com.jeremy.flail.graphics;

import static java.lang.Math.round;

import java.awt.Point;
import java.awt.Rectangle;

import com.jeremy.flail.Game;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.math.Vector2;

public class Camera {

	public static final int PIXELS_PER_METER = 32;

	public final Vector2 position = new Vector2(0.0f, 0.0f);

	public Vector2 transform(Point point, boolean dimension) {
		Vector2 result = new Vector2();
		if (dimension) {
			result.x = (float) point.x / PIXELS_PER_METER;
			result.y = (float) point.y / PIXELS_PER_METER;
		} else {
			result.x = (float) (point.x - Game.WIDTH / 2) / PIXELS_PER_METER + position.x;
			result.y = (float) (Game.HEIGHT / 2 - point.y) / PIXELS_PER_METER + position.y;
		}
		return result;
	}

	public Point transform(Vector2 vector, boolean dimension) {
		int x, y;
		if (dimension) {
			x = Math.round(vector.x * PIXELS_PER_METER);
			y = Math.round(vector.y * PIXELS_PER_METER);
		} else {
			x = round((vector.x - position.x) * PIXELS_PER_METER) + Game.WIDTH / 2;
			y = Game.HEIGHT / 2 - round((vector.y - position.y) * PIXELS_PER_METER);
		}
		return new Point(x, y);
	}

	public Rectangle transform(Bounds bounds) {
		Point location = transform(bounds.position, false);
		Point size = transform(bounds.size, true);
		return new Rectangle(location.x, location.y - size.y, size.x, size.y);
	}

}
