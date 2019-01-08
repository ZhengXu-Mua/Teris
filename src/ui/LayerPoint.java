package ui;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import config.GameConfig;

public class LayerPoint extends Layer {
	
	
	private final int expY;
	
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	

	/**
	 * ����y����
	 **/
	private static  int rmlineY ;
	
	/**
	 * ����y���� 
	 **/
	private  final int pointY ;
	/**
	 * �������λ�� 
	 */
	private  final int POINT_BIT = 5;
	
	
	
	private  int comX ;
	
	public LayerPoint(int x, int y, int w, int h) {
		super(x, y, w, h);
		//��ʼ��������ʾX����
		this.comX =this.w - IMG_NUMBER_W * POINT_BIT - PADDING;
		//��ʼ��������ʾy����
		this.pointY = PADDING;
		//��ʼ��
		this.rmlineY =this.pointY + Img.POINT.getHeight(null) +PADDING ;
		
		this.expY = this.rmlineY + Img.RMLINE.getHeight(null) + PADDING;
		
		
		
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//���ڱ���(����)
		g.drawImage(Img.POINT, this.x + PADDING, this.y + pointY, null);
		
		this.drawNumberLeftPad(comX, pointY, this.dto.getNowPoint(),POINT_BIT, g);
		//����
		g.drawImage(Img.RMLINE, this.x + PADDING, this.y + rmlineY, null);
		
		this.drawNumberLeftPad(comX, rmlineY, this.dto.getNowRemoveLine(),POINT_BIT, g);
		
		//����ֵ��
		int rmLine = this.dto.getNowRemoveLine();
		drawRect(expY,"��һ��",null,(double)(rmLine % LEVEL_UP) / (double)LEVEL_UP,g);
	}
	
	
}
