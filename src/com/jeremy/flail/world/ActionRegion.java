package com.jeremy.flail.world;

import java.awt.Color;

import com.jeremy.flail.entity.Player;
import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.math.Anchor;
import com.jeremy.flail.math.Bounds;

public class ActionRegion {

	private Bounds bounds;

	private boolean inRegion;

	private Runnable enter;
	private Runnable exit;

	private final Bounds buffer = new Bounds();

	public ActionRegion(Bounds bounds, Runnable enter, Runnable exit) {
		this.bounds = bounds;
		this.enter = enter;
		this.exit = exit;
	}

	public ActionRegion(Bounds bounds) {
		this(bounds, null, null);
	}

	public void tick() {
		if (isIn(Player.getInstance().bounds, Anchor.BOTTOM)) {
			if (!inRegion) {
				if (enter != null) {
					enter.run();
				}
				inRegion = true;
			}
		} else {
			if (inRegion) {
				if (exit != null) {
					exit.run();
				}
				inRegion = false;
			}
		}
	}

	public void render(Renderer renderer) {
		renderer.debugTrace(bounds, Anchor.BOTTOM_LEFT, Color.CYAN);
	}

	public boolean isIn(Bounds bounds, Anchor anchor) {
		anchor.effect(bounds, buffer);
		return this.bounds.isOverlapping(buffer);
	}

	public Runnable getEnter() {
		return enter;
	}

	public void setEnter(Runnable enter) {
		this.enter = enter;
	}

	public Runnable getExit() {
		return exit;
	}

	public void setExit(Runnable exit) {
		this.exit = exit;
	}

}
