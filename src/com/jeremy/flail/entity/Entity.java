package com.jeremy.flail.entity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.UUID;

import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.math.Anchor;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.math.Vector2;
import com.jeremy.flail.physics.Physics;
import com.jeremy.flail.world.World;

public abstract class Entity {

	private final UUID id;

	private Bounds renderBounds; // Relative to bounds

	public final Bounds bounds;
	public final Vector2 velocity;

	private boolean colliding;
	private boolean grounded;

	public Entity(UUID id, Vector2 size, Bounds renderBounds) {
		this.id = id;
		this.renderBounds = renderBounds;
		this.bounds = new Bounds(new Vector2(), size);
		this.velocity = new Vector2();
	}

	public Entity(Vector2 size, Bounds renderBounds) {
		this(UUID.randomUUID(), size, renderBounds);
	}

	public abstract BufferedImage getTexture();

	public void tick() {
		Physics.gravity(this);
		Physics.collide(this, World.getInstance());
		Physics.move(this);
	}

	public void render(Renderer renderer) {
		if (getTexture() != null) {
			renderer.draw(getTexture(), renderBounds.getRelativeTo(bounds.position), Anchor.BOTTOM);

			renderer.debugTrace(bounds, Anchor.BOTTOM, Color.YELLOW);

			Bounds futureBounds = new Bounds(bounds);
			futureBounds.position.add(Physics.getPerTickVelocity(this));
			renderer.debugTrace(futureBounds, Anchor.BOTTOM, colliding ? Color.RED : Color.WHITE);
		}
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}

	public boolean isGrounded() {
		return grounded;
	}

	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	public UUID getId() {
		return id;
	}

}
