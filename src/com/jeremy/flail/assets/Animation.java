package com.jeremy.flail.assets;

import java.awt.image.BufferedImage;

import com.jeremy.flail.Game;

public class Animation {

	private SpriteSheet sheet;
	private int frame;
	private float frameTimer = 0.0f;
	private final float frameTime;
	private boolean loopTransition;

	public Animation(SpriteSheet sheet, float fps) {
		this.sheet = sheet;
		this.frameTime = 1.0f / fps;
	}

	public void tick() {
		if (frameTimer > frameTime) {
			frameTimer = frameTimer % frameTime;
			nextFrame();
		}
		frameTimer += Game.TICK_TIME;
	}

	private void nextFrame() {
		frame++;
		if ((loopTransition = frame >= sheet.getSpriteCount())) {
			frame = 0;
		}
	}

	public void reset() {
		loopTransition = false;
		frame = 0;
	}

	public BufferedImage getAnimationFrame(boolean flipped) {
		return sheet.getSprite(frame, flipped);
	}

	public BufferedImage getAnimationFrame() {
		return getAnimationFrame(false);
	}

	public int getFrame() {
		return frame;
	}

	public boolean isFirstFrame() {
		return frame == 0;
	}

	public boolean isLoopTransition() {
		return loopTransition;
	}

}
