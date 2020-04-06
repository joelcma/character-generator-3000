package com.company;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ToolBox extends JPanel {
	private int WIDTH, HEIGHT;

	private JPanel buttonPanel;
	private InfoPanel infoPanel;

	private JButton okButton;
	private JButton resetButton;

	private int mousex, mousey;

	private boolean ok = false;


	public ToolBox(int width, int height) {
		WIDTH = width;
		HEIGHT = height;

		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		MouseListener listener = new ToolBox.MouseListener();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		// Add button and info panel
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		buttonPanel = new JPanel();
		setupButtonPanel(buttonPanel);
		buttonPanel.setPreferredSize(new Dimension(WIDTH, 30));
		infoPanel = new InfoPanel(WIDTH, HEIGHT-30);

		container.add(buttonPanel);
		container.add(infoPanel);

		this.add(container);
	}

	private void setupButtonPanel(JPanel panel) {
		panel.setLayout(new GridLayout(1,2));
		Dimension buttonDimensions = new Dimension(50,20);

		okButton = new JButton("OK");
		resetButton = new JButton("RESET");
		okButton.setPreferredSize(buttonDimensions);
		resetButton.setPreferredSize(buttonDimensions);

		okButton.addActionListener(actionEvent -> ok = true);
		resetButton.addActionListener(actionEvent -> {
			ok = false;
			System.out.println("RESET!");
		});

		panel.add(okButton);
		panel.add(resetButton);

		JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		container.add(panel);
	}

	public void requestCharacter(String character) {
		infoPanel.setInfoText("Please draw: " + character);
	}

	public void setCharacterCounter(int count) {
		System.out.printf("%d characters drawn!\n", count);
	}

	public boolean okPressed() {
		return ok;
	}

	public void resetOK() {
		ok = false;
	}

	private void mouseMove() {
	}

	// MOUSE LISTENER
	class MouseListener implements MouseInputListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("dragged!");
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//System.out.println("moved!");
			mousex = e.getX();
			mousey = e.getY();
			mouseMove();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("click!");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("press!");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("release!");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("entered!");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("exited!");
		}
	}
}
