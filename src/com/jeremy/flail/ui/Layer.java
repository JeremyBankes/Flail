package com.jeremy.flail.ui;

import com.jeremy.flail.graphics.Renderer;

public interface Layer {

	public abstract void tick();

	public abstract void render(Renderer renderer);

}
