package com.jeremy.flail.ui;

import com.jeremy.flail.Game;
import com.jeremy.flail.assets.Assets;
import com.jeremy.flail.graphics.Renderer;

public class EndingLayer implements Layer {
	
	private static EndingLayer instance;
	private boolean visible = false;

	public void tick() {
	}

	public void render(Renderer renderer) {
		if (this.visible) {
			renderer.draw(Assets.getInstance().getImage("ending"), 0, 0, Game.WIDTH, Game.HEIGHT);
		}
	}

	public static EndingLayer getInstance() {
		return (instance == null) ? (instance = new EndingLayer()) : instance;
	}

	public void show() {
		this.visible = true;
	}
	
}
