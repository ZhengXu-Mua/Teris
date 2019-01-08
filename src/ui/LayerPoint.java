package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	
	private final int expY;
	
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	

	/**
	 * 消行y坐标
	 **/
	private static  int rmlineY ;
	
	/**
	 * 分数y坐标 
	 **/
	private  final int pointY ;
	/**
	 * 分数最大位数 
	 */
	private  final int POINT_BIT = 5;
	
	
	
	private  int comX ;
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//初始化分数显示X坐标
		this.comX =this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
		//初始化分数显示y坐标
		this.pointY = PADDING;
		//初始化
		this.rmlineY =this.pointY + Img.POINT.getHeight(null) +PADDING ;
		
		this.expY = this.rmlineY + Img.RMLINE.getHeight(null) + PADDING;
		
		
		
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//窗口标题(分数)
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(),POINT_BIT, g);
		//消行
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmlineY, null);
		
		this.drawNumberLeftPad(comX, rmlineY, this.dto.getNowRemoveLine(),POINT_BIT, g);
		
		//绘制值槽
		int rmLine = this.dto.getNowRemoveLine();
		drawRect(expY,"下一级",null,(double)(rmLine % LEVEL_UP) / (double)LEVEL_UP,g);
	}
	
	
}
