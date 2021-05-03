package com.jeremy.flail.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class PalletteGenerator {

	public static void main(String[] args) throws Exception {
		BufferedImage palletSource = ImageIO.read(new File("C:\\Users\\Jeremy\\Desktop\\pallette.png"));
		HashSet<Integer> pallet = new HashSet<>();
		for (int i = 0; i < palletSource.getHeight(); i++) {
			for (int j = 0; j < palletSource.getWidth(); j++) {
				pallet.add(palletSource.getRGB(j, i));
			}
		}
		ArrayList<Integer> result = new ArrayList<>(pallet);
//		for (int i = 0; i < result.size(); i++) {
//			Color color = new Color(result.get(i));
//			float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
//			result.set(i, Color.HSBtoRGB((hsb[0] + 0.05f) % 1.0f, hsb[1] * 0.8f, hsb[2] * 0.75f));
//		}
//		result.sort((a, b) -> {
//			Color colorA = new Color(a);
//			Color colorB = new Color(b);
//			float[] hsbA = Color.RGBtoHSB(colorA.getRed(), colorA.getGreen(), colorA.getBlue(), null);
//			float[] hsbB = Color.RGBtoHSB(colorB.getRed(), colorB.getGreen(), colorB.getBlue(), null);
//			return round((hsbA[0] - hsbB[0]) * 1000.0f) + round((hsbA[1] - hsbB[1]) * 100.0f);
//		});
		result.forEach(color -> {
			System.out.printf("%h%n", color);
		});
	}

}
