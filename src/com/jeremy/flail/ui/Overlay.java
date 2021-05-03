package com.jeremy.flail.ui;

import java.util.LinkedHashSet;

import com.jeremy.flail.graphics.Renderer;

public class Overlay {

	private static Overlay instance;

	/*
	 * Queue layer edits for before and after ticking to avoid concurrent
	 * modifications
	 */
	private LinkedHashSet<Layer> layersToAdd = new LinkedHashSet<>();
	private LinkedHashSet<Layer> layersToRemove = new LinkedHashSet<>();

	private final LinkedHashSet<Layer> layers = new LinkedHashSet<>();

	public void tick() {
		layers.addAll(layersToAdd);
		layers.forEach(Layer::tick);
		layers.removeAll(layersToRemove);
		layersToAdd.clear();
		layersToRemove.clear();
	}

	public void render(Renderer renderer) {
		layers.forEach(layer -> layer.render(renderer));
	}

	public void addLayer(Layer layer) {
		layersToAdd.add(layer);
	}

	public void removeLayer(Layer layer) {
		layersToRemove.add(layer);
	}

	public static Overlay getInstance() {
		return instance == null ? instance = new Overlay() : instance;
	}

}
