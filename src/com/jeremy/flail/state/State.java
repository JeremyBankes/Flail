package com.jeremy.flail.state;

import com.jeremy.flail.graphics.Renderer;

public abstract class State {

	public abstract void tick();

	public abstract void render(Renderer renderer);

	protected abstract void enter();

	protected abstract void exit();

}
