package com.jeremy.flail.physics;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

import com.jeremy.flail.Game;
import com.jeremy.flail.entity.Entity;
import com.jeremy.flail.math.Anchor;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.math.Vector2;
import com.jeremy.flail.world.Tile;
import com.jeremy.flail.world.World;

public class Physics {

	private static final float ACCELERATION_GRAVITY = -30.0f;

	private final static Vector2 bufferA = new Vector2();
	private final static Vector2 bufferB = new Vector2();
	private final static Bounds boundsBuffer = new Bounds();

	public static void gravity(Entity entity) {
		entity.velocity.y += ACCELERATION_GRAVITY * Game.TICK_TIME;
	}

	public static boolean isColliding(Bounds entityBounds, Bounds tileBounds) {
		// Entity right < tile left
		if (entityBounds.position.x + entityBounds.size.x / 2 < tileBounds.position.x - tileBounds.size.x / 2) {
			return false;
		}
		// Entity left > tile right
		if (entityBounds.position.x - entityBounds.size.x / 2 > tileBounds.position.x + tileBounds.size.x / 2) {
			return false;
		}

		// Entity bottom > tile top
		if (entityBounds.position.y > tileBounds.position.y + tileBounds.size.y / 2) {
			return false;
		}
		// Entity top < tile bottom
		if (entityBounds.position.y + entityBounds.size.y < tileBounds.position.y - tileBounds.size.y / 2) {
			return false;
		}
		return true;
	}

	private static void collideTileRight(Entity entity, Bounds tileBounds) {
		bufferA.x = entity.bounds.position.x - entity.bounds.size.x / 2;
		bufferB.x = tileBounds.position.x + tileBounds.size.x / 2;
		if (bufferA.x >= bufferB.x) {
			entity.velocity.x = (bufferB.x - bufferA.x) / Game.TICK_TIME;
		}
	}

	private static void collideTileLeft(Entity entity, Bounds tileBounds) {
		bufferA.x = entity.bounds.position.x + entity.bounds.size.x / 2;
		bufferB.x = tileBounds.position.x - tileBounds.size.x / 2;
		if (bufferA.x <= bufferB.x) {
			entity.velocity.x = (bufferB.x - bufferA.x) / Game.TICK_TIME;
		}
	}

	private static void collideTileBottom(Entity entity, Bounds tileBounds) {
		bufferA.y = entity.bounds.position.y + entity.bounds.size.y;
		bufferB.y = tileBounds.position.y - tileBounds.size.y / 2;
		if (bufferA.y <= bufferB.y) {
			entity.velocity.y = (bufferB.y - bufferA.y) / Game.TICK_TIME;
		}
	}

	private static void collideTileTop(Entity entity, Bounds tileBounds) {
		bufferA.y = entity.bounds.position.y;
		bufferB.y = tileBounds.position.y + tileBounds.size.y / 2;
		if (bufferA.y >= bufferB.y) {
			entity.velocity.y = (bufferB.y - bufferA.y) / Game.TICK_TIME;
		}
	}

	public static void move(Entity entity) {
		entity.bounds.position.add(getPerTickVelocity(entity));
	}

	public static void collide(Entity entity, World world) {
		Tile tile;
		entity.setColliding(false);
		entity.setGrounded(false);
		for (int y = (int) floor(entity.bounds.position.y - 1); y < (int) ceil(entity.bounds.position.y + entity.bounds.size.y + 1); y++) {
			for (int x = (int) floor(entity.bounds.position.x - entity.bounds.size.x / 2 - 1); x < (int) ceil(
					entity.bounds.position.x + entity.bounds.size.x / 2 + 1); x++) {
				tile = world.getTile(x, y);
				if (tile != null && tile.isSolid()) {
					Bounds tileBounds = tile.getBounds();
					boundsBuffer.set(entity.bounds).position.add(getPerTickVelocity(entity));
					boolean notSnagRight = tileBounds.position.x + tileBounds.size.x / 2 > entity.bounds.position.x - entity.bounds.size.x / 2;
					boolean notSnagLeft = tileBounds.position.x - tileBounds.size.x / 2 < entity.bounds.position.x + entity.bounds.size.x / 2;
					if (isColliding(boundsBuffer, tileBounds)) {
						if (entity.velocity.y < 0 && isCollidableEdge(world, x, y, Anchor.TOP)) {
							if (notSnagRight && notSnagLeft) {
								collideTileTop(entity, tileBounds);
								if (tileBounds.position.y + tileBounds.size.y / 2 <= entity.bounds.position.y) {
									entity.setGrounded(true);
								}
							}
						}
						if (entity.velocity.x < 0 && isCollidableEdge(world, x, y, Anchor.RIGHT)) {
							collideTileRight(entity, tileBounds);
						}
						if (entity.velocity.y > 0 && isCollidableEdge(world, x, y, Anchor.BOTTOM)) {
							if (notSnagRight && notSnagLeft) {
								collideTileBottom(entity, tileBounds);
							}
						}
						if (entity.velocity.x > 0 && isCollidableEdge(world, x, y, Anchor.LEFT)) {
							collideTileLeft(entity, tileBounds);
						}
						entity.setColliding(true);
					}
				}
			}
		}
	}

	public static boolean isCollidableEdge(World world, int x, int y, Anchor anchor) {
		if (anchor == Anchor.TOP) {
			if (y + 1 >= world.getHeight()) {
				return true;
			}
			return !world.getTile(x, y + 1).isSolid();
		} else if (anchor == Anchor.RIGHT) {
			if (x + 1 >= world.getWidth()) {
				return true;
			}
			return !world.getTile(x + 1, y).isSolid();
		} else if (anchor == Anchor.BOTTOM) {
			if (y - 1 < 0) {
				return true;
			}
			return !world.getTile(x, y - 1).isSolid();
		} else if (anchor == Anchor.LEFT) {
			if (x - 1 < 0) {
				return true;
			}
			return !world.getTile(x - 1, y).isSolid();
		}
		return true;
	}

	public static Vector2 getPerTickVelocity(Entity entity) {
		return bufferA.set(entity.velocity).scale(Game.TICK_TIME);
	}

}
