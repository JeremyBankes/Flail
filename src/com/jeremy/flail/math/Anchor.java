package com.jeremy.flail.math;

public enum Anchor {

	TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT, CENTER;

	public void effect(Bounds bounds, Bounds result) {
		result.set(bounds);
		if (this == LEFT || this == RIGHT || this == CENTER) {
			result.position.y -= result.size.y / 2;
		}
		if (this == TOP_LEFT || this == TOP_RIGHT || this == TOP) {
			result.position.y -= result.size.y;
		}
		if (this == TOP || this == BOTTOM || this == CENTER) {
			result.position.x -= result.size.x / 2;
		}
		if (this == TOP_RIGHT || this == BOTTOM_RIGHT || this == RIGHT) {
			result.position.x -= result.size.x;
		}
	}

}
