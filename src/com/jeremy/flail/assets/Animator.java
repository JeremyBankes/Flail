package com.jeremy.flail.assets;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Animator {

	private final HashMap<String, Animation> animations = new HashMap<>();

	private String state;
	private boolean flipped;

	public void addAnimation(String state, Animation animation) {
		animations.put(state, animation);
		if (this.state == null) {
			this.state = state;
		}
	}

	public Animation getAnimation(String state) {
		return animations.get(state);
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		if (this.state != state) {
			this.state = state;
			reset();
		}
	}

	public BufferedImage getAnimationFrame() {
		return getCurrentAnimation().getAnimationFrame(flipped);
	}

	public boolean isLoopTransition() {
		return getCurrentAnimation().isLoopTransition();
	}

	public void reset() {
		getCurrentAnimation().reset();
	}

	public boolean isFirstFrame() {
		return getCurrentAnimation().isFirstFrame();
	}

	public void tick() {
		getCurrentAnimation().tick();
	}

	public Animation getCurrentAnimation() {
		return getAnimation(state);
	}

	public boolean isFlipped() {
		return flipped;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
	}

	public int getFrame() {
		return getCurrentAnimation().getFrame();
	}

}
