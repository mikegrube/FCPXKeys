package com.camas.shortcutkeys;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OverlayPanel extends JPanel {

	Color COLOR = new Color(255, 0, 0, 64);
	int ARCSIZE = 10;

	List<Rectangle> currentRectangles = new ArrayList<>();

	public OverlayPanel() {
		super();
		setOpaque(false);
	}

	public void setCurrentRectangles(List<Rectangle> rectangles) {
		currentRectangles = rectangles;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(COLOR);

		for (Rectangle rectangle : currentRectangles) {
			g.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, ARCSIZE, ARCSIZE);
		}

	}

}
