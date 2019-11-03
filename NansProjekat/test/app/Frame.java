package app;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame(int width, int height, String title, App app){
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		add(app);
		app.setPreferredSize(new Dimension(width, height));
		pack();
		setLocationRelativeTo(null);
	}
}
