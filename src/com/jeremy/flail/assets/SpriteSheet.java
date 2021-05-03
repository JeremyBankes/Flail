package com.jeremy.flail.assets;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class SpriteSheet {

	private final BufferedImage[] sprites;

	public SpriteSheet(BufferedImage source, Rectangle bounds, int rows, int columns, int spriteWidth, int spriteHeight, int count) {
		if (bounds.x != 0 || bounds.y != 0 || source.getWidth() != bounds.width || source.getHeight() != bounds.height) {
			source = source.getSubimage(bounds.x, bounds.y, bounds.width, bounds.height);
		}
		sprites = new BufferedImage[count * 2];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int index = i * columns + j;
				if (index < count) {
					sprites[index] = source.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
					sprites[index + count] = getFlipped(sprites[index]);
				}
			}
		}
	}

	public SpriteSheet(BufferedImage source, int rows, int columns, int spriteWidth, int spriteHeight, int count) {
		this(source, new Rectangle(0, 0, source.getWidth(), source.getHeight()), rows, columns, spriteWidth, spriteHeight, count);
	}

	public BufferedImage getSprite(int index, boolean flipped) {
		return sprites[flipped ? getSpriteCount() + index : index];
	}

	public BufferedImage getSprite(int index) {
		return getSprite(index, false);
	}

	public int getSpriteCount() {
		return sprites.length / 2;
	}

	private static BufferedImage getFlipped(BufferedImage image) {
		AffineTransform transform = new AffineTransform();
		transform.concatenate(AffineTransform.getScaleInstance(-1, 1));
		transform.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
		BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImage.createGraphics();
		g.transform(transform);
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return newImage;
	}

}
