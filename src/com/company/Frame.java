package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Frame extends JFrame {
	private String PROJECT_PATH = "/home/roboto/Documents/Dippen/PyLPR";

	private PaintBox paintbox;
	private ToolBox toolbox;
	Random generator;

	public Frame(int gridSize) throws InterruptedException{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Character generator program");
		generator = new Random();
		this.init(gridSize);
	}

	private void init(int gridSize) throws InterruptedException{
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		paintbox = new PaintBox(gridSize);
		container.add(paintbox);
		toolbox = new ToolBox(400, gridSize*30);
		container.add(toolbox);

		add(container);

		pack();

		setLocationRelativeTo(null);
		setVisible(true);

		run();
	}

	private String getRandomCharacter() {
		String[] characters = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Garbage"};
		int randomIndex = generator.nextInt(characters.length);
		return characters[randomIndex];
	}

	private String getRandomDigit() {
		String[] characters = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		int randomIndex = generator.nextInt(characters.length);
		return characters[randomIndex];
	}

	private String getRandomLetter() {
		String[] characters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		int randomIndex = generator.nextInt(characters.length);
		return characters[randomIndex];
	}

	private String getOneOfTwo() {
		String[] characters = new String[]{"E", "G"};
		int randomIndex = generator.nextInt(characters.length);
		return characters[randomIndex];
	}

	private void run() throws InterruptedException {
		boolean running = true;

		int characterCounter = 0;

		while(running) {
			// Clear paintbox.
			paintbox.clear();

			// Request a letter
			//String character = getRandomCharacter();
			//String character = getRandomDigit();
			// String character = getRandomLetter();
			String character = getOneOfTwo();
			toolbox.requestCharacter(character);
			toolbox.setCharacterCounter(characterCounter);

			// Wait for OK button to be pressed
			while(!toolbox.okPressed()) {
				Thread.sleep(5); // Sleep 5 milliseconds
			}

			// Reset toolBox OK button
			toolbox.resetOK();

			// Save as image
			BufferedImage image = paintbox.getImage();
			String filename = character + "_" + System.currentTimeMillis() + ".jpg";
			String folder = PROJECT_PATH + "/ocr/hand_drawn/" + character + "/";
			saveImage(folder, filename, image);

			// If something, running = false and exit

			characterCounter++;
		}
	}

	private void saveImage(String folder, String filename, BufferedImage image) {
		File outputFile = new File(folder, filename);
		System.out.printf("Saving %s\n", outputFile.getPath());
		try {
			boolean success = outputFile.createNewFile();
			if (!success) {
				System.out.println("Failed to create new file");
				return;
			}

			ImageIO.write(image, "jpg", outputFile);
			System.out.println("Image saved!");
		} catch (Exception e) {
			System.out.println("Uh-oh! Image failed to save!");
			e.printStackTrace();
		}
	}
}
