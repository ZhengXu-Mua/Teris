package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerAbout extends Layer {
	
	
	public LayerAbout(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		this.drawImageAtCenter(Img.SIGN, g);
	}
}
