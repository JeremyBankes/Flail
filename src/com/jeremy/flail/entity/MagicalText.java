package com.jeremy.flail.entity;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import com.jeremy.flail.Game;
import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.math.Vector2;

public class MagicalText extends Entity {

	private static final float BOB_AMOUNT = 0.1f;

	private Font font;
	private Color color;
	private String text;
	private float bobTimer;

	public MagicalText(String text, Color color) {
		super(new Vector2(), new Bounds());
		this.text = text;
		this.color = color;
		font = new Font("Free Pixel", Font.PLAIN, 16);
	}

	public MagicalText(String text) {
		this(text, Color.WHITE);
	}

	@Override
	public void tick() {
		bobTimer += Game.TICK_TIME;
		if (bobTimer > PI * 2) {
			bobTimer -= PI * 2;
		}
	}

	@Override
	public void render(Renderer renderer) {
		renderer.setFont(font);
		renderer.setColor(color);
		float bob = (float) sin(bobTimer * 2) * BOB_AMOUNT;
		float y = bounds.position.y;
		for (String line : text.split("\n")) {
			renderer.draw(line, new Vector2(bounds.position.x, y + bob));
			y -= renderer.getFontHeightInWorld();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public BufferedImage getTexture() {
		return null;
	}

}
