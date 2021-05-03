package com.jeremy.flail.input;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;

public class MouseInput implements MouseListener, MouseMotionListener, MouseWheelListener {

	public static final int LEFT_BUTTON = MouseEvent.BUTTON1;
	public static final int MIDDLE_BUTTON = MouseEvent.BUTTON2;
	public static final int RIGHT_BUTTON = MouseEvent.BUTTON3;

	private final Point location = new Point();
	private final HashSet<Integer> pressed = new HashSet<>();

	public void attach(Canvas canvas) {
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);
	}

	public Point getLocation() {
		return location;
	}

	public boolean isPressed(int button) {
		return pressed.contains(button);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		pressed.add(event.getButton());
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		pressed.remove(event.getButton());
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		location.setLocation(event.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		location.setLocation(event.getPoint());
	}

}
