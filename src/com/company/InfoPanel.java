package com.company;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
	private int WIDTH, HEIGHT;

	private String textToShow = "";

	public InfoPanel(int width, int height) {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		WIDTH = width;
		HEIGHT = height;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void setInfoText(String text) {
		textToShow = text;
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2);

		g2.drawRect(10, 10, WIDTH-20, HEIGHT/4);

		g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g2.drawString("INFO PANEL", WIDTH/2-60, 30);

		g2.setFont(new Font("Roboto", Font.PLAIN, 14));
		int textX = 20;
		int textY = 50;
		g2.drawString(textToShow, textX, textY);

		g2.dispose();
	}

}
