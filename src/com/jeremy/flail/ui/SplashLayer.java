package com.jeremy.flail.ui;

import static java.lang.Math.min;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import java.awt.Color;

import com.jeremy.flail.Game;
import com.jeremy.flail.graphics.Renderer;
import com.jeremy.flail.math.Anchor;

public class SplashLayer implements Layer {

	private static SplashLayer instance;

	private static final float FADE_IN_DURATION = 2.0f;
	private static final float FADE_OUT_DURATION = 0.5f;
	private static final float TEXT_DURATION = 1.0f;

	private String[] texts;
	private int currentText;
	private Color fadeColor;
	private Color textColor;

	private int stage;
	private float timer;

//	public void show(String text, int fadeColor, int textColor) {
//		this.text = text;
//		this.fadeColor = new Color(fadeColor);
//		this.textColor = new Color(textColor);
//		timer = 0;
//		stage = 1;
//		Overlay.getInstance().addLayer(this);
//	}

	public void show(int fadeColor, int textColor, String... texts) {
		this.texts = texts;
		this.fadeColor = new Color(fadeColor);
		this.textColor = new Color(textColor);
		this.currentText = 0;
		this.timer = 0.0f;
		this.stage = 0;
		Overlay.getInstance().addLayer(this);
	}

//	@Override
//	public void tick() {
//		if (stage == 1) {
//			if (timer > FADE_IN_DURATION) {
//				timer = 0;
//				stage++;
//			}
//		} else if (stage == 2) {
//			if (timer > TEXT_DURATION) {
//				timer = 0;
//				stage++;
//			}
//		} else {
//			if (timer > FADE_OUT_DURATION) {
//				Overlay.getInstance().removeLayer(this);
//			}
//		}
//		timer += Game.TICK_TIME;
//	}

	@Override
	public void tick() {
		if (this.stage == 0) {
			if (this.timer > FADE_IN_DURATION) {
				this.timer = 0.0f;
				this.stage++;
			}
		} else if (this.stage == 1) {
			if (this.timer > TEXT_DURATION) {
				this.timer = 0.0f;
				if (this.currentText < this.texts.length - 1) {
					this.currentText++;
				} else {
					this.stage++;
				}

			}
		} else if (this.timer > FADE_OUT_DURATION) {
			Overlay.getInstance().removeLayer(this);
		}

		this.timer += Game.TICK_TIME;
	}

	@Override
	public void render(Renderer renderer) {
		if (stage == 1) {
			float interpolation = min(1.0f, timer / FADE_IN_DURATION);
			renderer.setColor(getInterpolatedColor(fadeColor, interpolation));
			renderer.fill();
			renderer.setColor(getInterpolatedColor(textColor, interpolation));
		} else if (stage == 2) {
			renderer.setColor(fadeColor);
			renderer.fill();
			renderer.setColor(textColor);
		} else {
			float interpolation = min(1.0f, timer / FADE_OUT_DURATION);
			renderer.setColor(getInterpolatedColor(fadeColor, 1.0f - interpolation));
			renderer.fill();
			renderer.setColor(getInterpolatedColor(textColor, 1.0f - interpolation));
		}
		renderer.draw(this.texts[currentText], Game.WIDTH / 2, Game.HEIGHT / 2, Anchor.CENTER);
	}

	/**
	 * @param baseColor     The RGB values to start with
	 * @param interpolation 0.0 - 1.0f
	 * @return A linearly-interpolated-alpha color
	 */
	private Color getInterpolatedColor(Color baseColor, float interpolation) {
		float transition = (float) sin(Math.PI * (interpolation - 0.5)) / 2.0f + 0.5f;
		return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), round(transition * 255));
	}

	public static SplashLayer getInstance() {
		return instance == null ? instance = new SplashLayer() : instance;
	}

}
