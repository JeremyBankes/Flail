package com.jeremy.flail;

import java.awt.GraphicsEnvironment;

public class Launcher {

	public static void main(String[] args) {
		GraphicsEnvironment.getLocalGraphicsEnvironment();
		Game.getInstance().start();
	}

}
