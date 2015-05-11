package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class splash extends JFrame {
	private static final long serialVersionUID = 1L;

	public splash() {
		setLocationRelativeTo(null); 
		setUndecorated(true);
		setBounds(HEIGHT, WIDTH, 500, 330);
		String path_house = "images/splash/splash.bmp";
		File houseIconsFile = new File(path_house);
		BufferedImage houseImg = null;
		try {
			houseImg = ImageIO.read(houseIconsFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JLabel houseIconlbl = new JLabel(new ImageIcon(houseImg));
		houseIconlbl.setBounds(0, 0, 500, 330);
		add(houseIconlbl);
	}

}
