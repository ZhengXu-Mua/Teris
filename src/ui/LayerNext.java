package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerNext extends Layer {
	
	
	
	public LayerNext(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		if(this.dto.isStart()) {
			drawImageAtCenter(Img.NEXT_ACT[this.dto.getNext()], g);
		}
	}
	
	
}
