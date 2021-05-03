package com.jeremy.flail.graphics;

import static java.lang.Math.round;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import com.jeremy.flail.Game;
import com.jeremy.flail.math.Anchor;
import com.jeremy.flail.math.Bounds;
import com.jeremy.flail.math.Vector2;

/**
 * 
 * A class to allow for rendering custom elements to a java.awt.Canvas
 * 
 * @author Jeremy
 *
 *         Used
 *         https://docs.oracle.com/javase/7/docs/api/java/awt/image/BufferStrategy.html
 *         as reference
 *
 */
public class Renderer {

	public static final int BUFFERS = 2;

	private Canvas canvas;
	private BufferedImage frameBuffer;
	private BufferStrategy bufferStrategy;
	private Graphics2D canvasGraphics;
	private Graphics2D graphics;

	private boolean debug = false;

	private final Bounds boundsBuffer = new Bounds();

	private Camera camera;

	public Renderer(Canvas canvas) {
		this.canvas = canvas;
		frameBuffer = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
		camera = new Camera();
		graphics = (Graphics2D) frameBuffer.getGraphics();
	}

	public void setColor(Color color) {
		graphics.setColor(color);
	}

	public void setFont(Font font) {
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		graphics.setFont(font);
	}

	public float getFontHeightInWorld() {
		return camera.transform(new Point(0, graphics.getFontMetrics().getAscent()), true).y;
	}

	public void debugTrace(Bounds bounds, Anchor anchor, Color color) {
		if (debug) {
			setColor(color);
			traceRectangle(bounds, anchor);
		}
	}

	public void traceRectangle(int x, int y, int width, int height) {
		graphics.drawRect(x, y, width - 1, height - 1);
	}

	public void traceRectangle(Rectangle rectangle) {
		traceRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public void traceRectangle(Bounds bounds, Anchor anchor) {
		anchor.effect(bounds, boundsBuffer);
		traceRectangle(camera.transform(boundsBuffer));
	}

	public void drawRectangle(int x, int y, int width, int height) {
		graphics.fillRect(x, y, width, height);
	}

	public void drawRectangle(Rectangle rectangle) {
		drawRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public void drawRectangle(Bounds bounds, Anchor anchor) {
		anchor.effect(bounds, boundsBuffer);
		drawRectangle(camera.transform(boundsBuffer));
	}

	public void fill() {
		drawRectangle(0, 0, getCanvasWidth(), getCanvasHeight());
	}

	public void draw(Image image, int x, int y, int width, int height) {
		graphics.drawImage(image, x, y, width, height, canvas);
	}

	public void draw(Image image, Rectangle rectangle) {
		draw(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	public void draw(Image image, Bounds bounds, Anchor anchor) {
		anchor.effect(bounds, boundsBuffer);
		draw(image, camera.transform(boundsBuffer));
	}

	public void draw(String text, int x, int y, Anchor anchor) {
		FontMetrics metrics = graphics.getFontMetrics();
		int width = metrics.stringWidth(text);
		int height = metrics.getAscent();
		if (anchor == Anchor.LEFT || anchor == Anchor.RIGHT || anchor == Anchor.CENTER) {
			y -= height / 2;
		}
		if (anchor == Anchor.TOP_LEFT || anchor == Anchor.TOP_RIGHT || anchor == Anchor.TOP) {
			y -= height;
		}
		if (anchor == Anchor.TOP || anchor == Anchor.BOTTOM || anchor == Anchor.CENTER) {
			x -= width / 2;
		}
		if (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT || anchor == Anchor.RIGHT) {
			x -= width;
		}
		graphics.drawString(text, x, y);
	}

	public void draw(String text, int x, int y) {
		draw(text, x, y, Anchor.CENTER);
	}

	public void draw(String text, Point point) {
		draw(text, point.x, point.y);
	}

	public void draw(String text, Vector2 position) {
		draw(text, camera.transform(position, false));
	}

	public Camera getCamera() {
		return camera;
	}

	public int getCanvasWidth() {
		return canvas.getWidth();
	}

	public int getCanvasHeight() {
		return canvas.getHeight();
	}

	public float getAspectRatio() {
		return (float) getCanvasWidth() / getCanvasHeight();
	}

	public void begin() {
		if (bufferStrategy == null) {
			canvas.createBufferStrategy(BUFFERS);
			bufferStrategy = canvas.getBufferStrategy();
		}
		canvasGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();
		canvasGraphics.setColor(Color.BLACK);
		graphics.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
	}

	public void end() {
		int width, height;
		float canvasAspectRatio = (float) getCanvasWidth() / getCanvasHeight();
		if (canvasAspectRatio > Game.ASPECT_RATIO) {
			height = getCanvasHeight();
			width = round(height * Game.ASPECT_RATIO);
		} else {
			width = getCanvasWidth();
			height = round(width / Game.ASPECT_RATIO);
		}
		int x = (canvas.getWidth() - width) / 2;
		int y = (canvas.getHeight() - height) / 2;
		canvasGraphics.drawImage(frameBuffer, x, y, width, height, canvas);
		canvasGraphics.dispose();
	}

	public void display() {
		bufferStrategy.show();
	}

	public boolean isRestored() {
		return bufferStrategy.contentsRestored();
	}

	public boolean isLost() {
		return bufferStrategy.contentsLost();
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
