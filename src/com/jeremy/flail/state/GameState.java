package com.jeremy.flail.state;

import java.awt.event.KeyEvent;

import com.jeremy.flail.Game;
import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.input.KeyInput.KeyInputEventType;
import com.jeremy.flail.level.TutorialLevel;
import com.jeremy.flail.world.World;

public class GameState extends State {

	public GameState() {
		TutorialLevel.start();
		Game.getInstance().getKeyInput().add((type, code, character, modifiers, consumed) -> {
			if (type == KeyInputEventType.PRESS) {
				if (code == KeyEvent.VK_F3) {
					final Renderer renderer = Game.getInstance().getWindow().getRenderer();
					renderer.setDebug(!renderer.isDebug());
				}
			}
		});
	}

	@Override
	public void tick() {
		World.getInstance().tick();
	}

	@Override
	public void render(Renderer renderer) {
		World.getInstance().render(renderer);
	}

	@Override
	protected void enter() {

	}

	@Override
	protected void exit() {

	}

}
