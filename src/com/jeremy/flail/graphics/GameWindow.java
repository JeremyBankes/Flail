package com.jeremy.flail.graphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jeremy.flail.input.KeyInput;
import com.jeremy.flail.input.MouseInput;

public class GameWindow {

	private JFrame frame;
	private Canvas canvas;
	private Renderer renderer;

	private MouseInput mouseInput;
	private KeyInput keyInput;

	public GameWindow(String title, int width, int height) {
		frame = new JFrame(title);
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setBackground(Color.BLACK);
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(canvas, BorderLayout.CENTER);
		frame.setContentPane(contentPane);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mouseInput = new MouseInput();
		mouseInput.attach(canvas);

		keyInput = new KeyInput();
		keyInput.attach(canvas);
	}

	public void start() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		renderer = new Renderer(canvas);
		frame.setVisible(true);
	}

	public void stop() {
		if (frame.isVisible()) {
			frame.dispose();
		}
	}

	public boolean isValid() {
		return frame.isVisible();
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public MouseInput getMouseInput() {
		return mouseInput;
	}

	public KeyInput getKeyInput() {
		return keyInput;
	}

}
