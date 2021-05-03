package com.jeremy.flail.entity;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.UUID;

import com.jeremy.flail.Game;
import com.jeremy.flail.assets.Animation;
import com.jeremy.flail.assets.Animator;
import com.jeremy.flail.assets.Assets;
import com.jeremy.flail.assets.SpriteSheet;
import com.jeremy.flail.graphics.Camera;
import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.input.KeyInput;
import com.jeremy.flail.input.KeyInput.KeyInputEventType;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.math.Vector2;
import com.jeremy.flail.physics.Physics;
import com.jeremy.flail.ui.SplashLayer;
import com.jeremy.flail.world.World;

public class Player extends Entity {

	public static final float ACCELERATION = 0.75f;
	public static final float DECELERATION = 0.25f;
	public static final float MAX_VELOCITY = 5.0f;
	public static final float JUMP_VELOCITY = 10.0f;
	public static final int MAX_BOOST_COUNT = 1;
	public static final float DEATH_TIME = 2.5f;

	private static Player instance;

	private Animator animator;
	private int boostCount = 0;

	private boolean attacking = false;
	private boolean dead = false;
	private float deathTimer = 0.0f;

	private Player(UUID id) {
		super(id, new Vector2(9.0f / 16f, 22.0f / 16.0f), new Bounds(0.5f / 16.0f, 0.0f, 2.0f, 2.0f));
		animator = new Animator();
		BufferedImage source = Assets.getInstance().getImage("player");
		animator.addAnimation("idle", new Animation(new SpriteSheet(source, new Rectangle(0, 0, 64, 64), 2, 2, 32, 32, 4), 4.0f));
		animator.addAnimation("fall", new Animation(new SpriteSheet(source, new Rectangle(64, 0, 64, 64), 2, 2, 32, 32, 4), 12.0f));
		animator.addAnimation("walk", new Animation(new SpriteSheet(source, new Rectangle(128, 0, 64, 128), 4, 2, 32, 32, 7), 12.0f));
		animator.addAnimation("attack", new Animation(new SpriteSheet(source, new Rectangle(0, 64, 128, 64), 2, 4, 32, 32, 8), 16.0f));
		Game.getInstance().getKeyInput().add((KeyInputEventType type, int code, char character, int modifiers, boolean consumed) -> {
			if (type == KeyInputEventType.PRESS) {
				if (code == 'W') {
					jump();
				} else if (code == KeyEvent.VK_SPACE) {
					attacking = true;
				}
			}
		});
	}

	@Override
	public BufferedImage getTexture() {
		return animator.getAnimationFrame();
	}

	@Override
	public void tick() {
		Physics.gravity(this);
		control();
		Physics.collide(this, World.getInstance());
		Physics.move(this);

		final Camera camera = Game.getInstance().getWindow().getRenderer().getCamera();
		camera.position.set(bounds.position);

		if (isGrounded() && boostCount > 0) {
			boostCount = 0;
		}

		animator.setFlipped(velocity.x > 0 ? false : velocity.x < 0 ? true : animator.isFlipped());
		animator.tick();
		if (!attacking) {
			if (isGrounded() && velocity.isZero()) {
				animator.setState("idle");
			} else if (!isGrounded()) {
				animator.setState("fall");
			} else {
				animator.setState("walk");
			}
		} else {
			if (!animator.getState().equals("attack")) {
				animator.setState("attack");
			} else if (animator.isLoopTransition()) {
				attacking = false;
			}
		}

		if (dead) {
			if (deathTimer > DEATH_TIME) {
				bounds.position.set(World.getInstance().getSpawnPoint());
				deathTimer = 0.0f;
				dead = false;
			}
			deathTimer += Game.TICK_TIME;
		}
	}

	@Override
	public void render(Renderer renderer) {
		if (dead) {
			return;
		}
		super.render(renderer);
	}

	private void control() {
		KeyInput keyInput = Game.getInstance().getKeyInput();
		if (hasControl() && keyInput.isPressed('D')) {
			if (velocity.x < Player.MAX_VELOCITY) {
				velocity.x += Player.ACCELERATION;
				if (velocity.x > Player.MAX_VELOCITY) {
					velocity.x = Player.MAX_VELOCITY;
				}
			}
		} else if (velocity.x > 0) {
			velocity.x -= Player.DECELERATION;
			if (velocity.x < 0) {
				velocity.x = 0;
			}
		}
		if (hasControl() && keyInput.isPressed('A')) {
			if (velocity.x > -Player.MAX_VELOCITY) {
				velocity.x -= Player.ACCELERATION;
				if (velocity.x < -Player.MAX_VELOCITY) {
					velocity.x = -Player.MAX_VELOCITY;
				}
			}
		} else if (velocity.x < 0) {
			velocity.x += Player.DECELERATION;
			if (velocity.x > 0) {
				velocity.x = 0;
			}
		}
	}

	private boolean hasControl() {
		return !attacking && !dead;
	}

	private void jump() {
		if (dead) {
			return;
		}
		if (isGrounded()) {
			velocity.y = JUMP_VELOCITY;
		} else {
			if (boostCount < MAX_BOOST_COUNT) {
				velocity.y = JUMP_VELOCITY * 3 / 4;
				boostCount++;
				animator.reset();
			}
		}
	}

	public void die() {
		dead = true;
		deathTimer = 0.0f;
		SplashLayer.getInstance().show(0x800000, 0xFFFFFF, "Respawning...");
	}

	public static Player getInstance() {
		return instance == null ? instance = new Player(UUID.randomUUID()) : instance;
	}

}
