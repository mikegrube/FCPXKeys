package com.camas.shortcutkeys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static javax.swing.SwingUtilities.invokeLater;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	CombinationService combinationService;

	@Autowired
	KeyPositionService keyPositionService;

	public static void main(String[] args) {

		new SpringApplicationBuilder(Application.class).headless(false).run(args);
	}

	@Override
	public void run(String... args) {

		loadData();

		invokeLater(() -> {

			createUI();

			//createActions(frame);
		});
	}

	private void loadData() {

		keyPositionService.load();

		combinationService.load();

	}

	private void createUI() {

		BufferedImage keyboardPic = null;

		JFrame frame = new JFrame("Spring Boot Swing App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,300);

		//The overall main panel
		JPanel main = new JPanel(new BorderLayout());

		//Make an overlay panel
		JPanel overlayPanel = new JPanel() {
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		overlayPanel.setLayout(new OverlayLayout(overlayPanel));

		//Get the keyboard background on a panel
		try {
			keyboardPic = ImageIO.read(new File("Keyboard.jpeg"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		JLabel keyboardLabel = new JLabel(new ImageIcon(keyboardPic));
		JPanel keyboardPanel = new JPanel(new BorderLayout());
		keyboardPanel.add(keyboardLabel);


		//Set up sprite panel
		OverlayPanel spritePanel = new OverlayPanel();

		//Overlay the panels
		overlayPanel.add(spritePanel);
		overlayPanel.add(keyboardPanel);

		main.add(overlayPanel, BorderLayout.NORTH);

		JPanel listPanel = new JPanel(new BorderLayout());
		JTextArea text = new JTextArea("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		listPanel.add(text);
		main.add(listPanel, BorderLayout.SOUTH);

		main.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				CombinationSearch search = new CombinationSearch(e.getKeyCode(),
						e.getKeyChar(),
						e.isMetaDown() ? 1 : 0,
						e.isShiftDown() ? 1 : 0,
						e.isAltDown() ? 1 : 0,
						e.isControlDown() ? 1 : 0
				);

				//Develop text
				int code = e.getKeyCode();	//a = 65
				String combo = e.getModifiersExText(e.getModifiersEx());
				StringBuilder sb = new StringBuilder("Keystroke is: " + combo + " (" + code + ")\n");

				//Find all possibles
				List<Combination> combinationList = combinationService.findPossibles(search);
				sb.append("=====\n");
				sb.append("Possible combinations:\n");
				for (Combination combination : combinationList) {
					sb.append("  ").append(combination.getCombination()).append(": ").append(combination.getCommand()).append("\n");
				}

				text.setText(sb.toString());

				//Show rectangles
				spritePanel.setCurrentRectangles(combinationService.getRectangles(search));

				spritePanel.revalidate();
				spritePanel.repaint();
			}

		});

		main.setFocusable(true);
		main.setFocusTraversalKeysEnabled(false);

		frame.setContentPane(main);
		frame.pack();
		frame.setVisible(true);

	}

}