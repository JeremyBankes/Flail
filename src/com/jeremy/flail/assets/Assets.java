package com.jeremy.flail.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Assets {

	private static Assets instance;

	private final HashMap<String, BufferedImage> textures = new HashMap<>();

	public void loadFont(String path) {
		try {
			Font font = Font.createFont(Font.PLAIN, Assets.class.getResourceAsStream(path));
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		} catch (FontFormatException | IOException exception) {
			exception.printStackTrace();
		}
	}

	public void loadImage(String key, String path) {
		try {
			textures.put(key, ImageIO.read(Assets.class.getResourceAsStream(path)));
		} catch (Exception exception) {
			System.out.printf("Failed to load image \"%s\". %s: %s%n", path, exception.getClass().getSimpleName(), exception.getMessage());
		}
	}

	public boolean isImageLoaded(String key) {
		return textures.containsKey(key);
	}

	public BufferedImage getImage(String key) {
		return textures.get(key);
	}

	public static Assets getInstance() {
		return instance == null ? instance = new Assets() : instance;
	}

}
