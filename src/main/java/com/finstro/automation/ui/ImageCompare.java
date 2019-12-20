package com.finstro.automation.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.finstro.automation.common.Common;

public class ImageCompare {

	public BufferedImage diffImages(String strImage1, String strImage2,
			int threshold, int percent) throws IOException {
		
		
		
		BufferedImage bim1 = null;
		BufferedImage bim2 = null;
		BufferedImage result = null;

		bim1 = ImageIO.read(new File(Common.correctPath(strImage1)));
		bim2 = ImageIO.read(new File(Common.correctPath(strImage2)));

		int minWidth = Math.min(bim1.getWidth(), bim2.getWidth());
		int minHeight = Math.min(bim1.getHeight(), bim2.getHeight());
		int maxWidth = Math.max(bim1.getWidth(), bim2.getWidth());
		int maxHeight = Math.max(bim1.getHeight(), bim2.getHeight());

		if (result == null) {
			result = createEmptyDiffImage(minWidth, minHeight,
					maxWidth, maxHeight);
		}
		int numberDiffPoint = 0;
		int numberImgPoint = 0;
//		result = new BufferedImage(minWidth, minHeight, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < minWidth; ++x) {
			for (int y = 0; y < minHeight; ++y) {
				int rgb1 = bim1.getRGB(x, y);
				int rgb2 = bim2.getRGB(x, y);
				if (rgb1 != rgb2
						// don't bother about differences of 1 color step
						&& (Math.abs((rgb1 & 0xFF) - (rgb2 & 0xFF)) > threshold
								|| Math.abs(((rgb1 >> 8) & 0xFF)
										- ((rgb2 >> 8) & 0xFF)) > threshold || Math
								.abs(((rgb1 >> 16) & 0xFF)
										- ((rgb2 >> 16) & 0xFF)) > threshold)) {
					
					result.setRGB(x, y, new Color(255, 255, 0).getRGB());
					numberDiffPoint ++;
				} else {
					if (result != null) {
						result.setRGB(x, y, new Color(rgb1).darker().getRGB());
					}
				}
				numberImgPoint++;
			}
		}
		if((numberDiffPoint/numberImgPoint)*100 > percent) {
			return result;
		}
		else {
			return null;
		}
	}

	private BufferedImage createEmptyDiffImage(int minWidth, int minHeight,
			int maxWidth, int maxHeight) {
		BufferedImage bim3 = new BufferedImage(maxWidth, maxHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bim3.getGraphics();
		if (minWidth != maxWidth || minHeight != maxHeight) {
			graphics.setColor(Color.BLACK);
			graphics.fillRect(0, 0, maxWidth, maxHeight);
		}
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, minWidth, minHeight);
		graphics.dispose();
		return bim3;
	}


}
