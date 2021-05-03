package com.jeremy.flail.world;

import java.awt.image.BufferedImage;

import com.jeremy.flail.assets.Assets;

public enum TileType {

	AIR(' ', false, false), GRASS('G', true, true), DIRT('D', true, true), STONE('S', true, true), FLOWER('f', false, true),
	SUPPORT('s', false, true), SIGN('i', false, true), SPIKES('^', false, true), UNDERGROUND('*', false, true), DOOR_TOP(']', false, true),
	DOOR_BOTTOM('[', false, true),;

	public final char symbol;
	public final BufferedImage texture;
	public final boolean solid;

	private TileType(char symbol, boolean solid, boolean texture) {
		this.symbol = symbol;
		this.solid = solid;
		if (texture) {
			String textureName = String.format("tile-%s", name().toLowerCase().replace('_', '-'));
			Assets.getInstance().loadImage(textureName, String.format("/textures/tiles/%s.png", textureName));
			this.texture = Assets.getInstance().getImage(textureName);
		} else {
			this.texture = null;
		}
	}

	public static TileType get(char symbol) {
		for (TileType type : values()) {
			if (type.symbol == symbol) {
				return type;
			}
		}
		System.out.printf("Coundn't find tile type for symbol \"%c\".%n", symbol);
		return AIR;
	}

}
