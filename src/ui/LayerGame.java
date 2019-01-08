package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {
	

	//TODO
	private static int SIZE_ROL = GameConfig.getFrameConfig().getSize_Rol();
	
	private static final int LEFT_SIDE = 0;
	
	private static final int RIGHT_SIDE = GameConfig.getSystemConfig().getMaxX();
	
	private static final int LOSE_IDX = GameConfig.getFrameConfig().getLoseIdx();
	
	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void paint(Graphics g) {
		this.createWindow(g);
		//��÷������鼯��
		GameAct act = this.dto.getGameAct();
		if(act != null) {
			Point[] points =act.getActPoints();
			//��Ӱ�ر�
			this.drawShadow(points, g);
			//���ƻ����
			this.drawMainAct(points, g);
		}
		//������Ϸ��ͼ
		this.drawMap(g);
		if(this.dto.isPause()) {
			drawImageAtCenter(Img.PAUSE, g);
		}
	}
	


	private void drawMainAct(Point[] points, Graphics g) {
		//��÷������ͱ��
		int typeCode = this.dto.getGameAct().getTypeCode();
		//���Ʒ���
		for (int i = 0; i < points.length; i++) {
			this.drawActByPoint(points[i].x, points[i].y, typeCode + 1, g);		
		}
	}
	
	private void drawMap(Graphics g) {
		//��ӡ��ͼ
		boolean[][] map = this.dto.getGameMap();
		//���㵱ǰ�ѻ���ɫ
		int lv = this.dto.getNowLevel();
		int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if(map[x][y]) {
					this.drawActByPoint(x, y, imgIdx, g);	
				}
			}		
		}	
	}

	private void drawShadow(Point[] points, Graphics g) {
		
		if(this.dto.isShowShadow()) {
			return;
		}
		int leftX = RIGHT_SIDE;
		int righeX = LEFT_SIDE;
		for (Point p : points) {
			leftX = p.x < leftX ? p.x : leftX;
			righeX = p.x > righeX ? p.x :righeX;
		}
		g.drawImage(Img.SHADOW,
				this.x + BORDER + (leftX << SIZE_ROL),
				this.y + BORDER,
				(righeX - leftX + 1) << SIZE_ROL,
				this.h - (BORDER << 1),
				null);
		
		
	}

	private void drawActByPoint(int x,int y,int imgIdx,Graphics g){
		imgIdx = this.dto.isStart() ? imgIdx : LOSE_IDX;
		g.drawImage(Img.ACT,
				this.x + (x << SIZE_ROL) + BORDER,
				this.y + (y << SIZE_ROL) + BORDER,
				this.x + (x  + 1 << SIZE_ROL) + BORDER,
				this.y + (y  + 1 << SIZE_ROL) + BORDER,
				imgIdx << SIZE_ROL, 0, (imgIdx + 1) << SIZE_ROL,1 << SIZE_ROL,null);
	}
}
