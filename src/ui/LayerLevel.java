package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class LayerLevel extends Layer {

	
	/**
	 * 取宽度
	 */
	private static final int IMG_LV_W =Img.LV.getWidth(null);
	

	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//取窗口标题居中坐标
		int centerX =(this.w - IMG_LV_W >> 1);
		//窗口标题
		g.drawImage(Img.LV, this.x + centerX, this.y + PADDING, null);
		//显示等级
		this.drawNumberLeftPad(centerX, 64, this.dto.getNowLevel(),2, g);
	}
	
}
