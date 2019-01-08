package entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import config.GameConfig;

public class GameAct {
	/**
	 * ��������
	 */
	private Point[] actPoints;
	
	private int typeCode;
	/**
	 * 
	 */
	private  static int MIN_X = 0;
	private  static int MAX_X = 9;
	private  static int MIN_Y = 0;
	private  static int MAX_Y = 17;
	
	private  static List<Point[]>TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	
	private  static List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();

	
	public GameAct(int typeCode) {
		this.init(typeCode);
		//TODO �����ļ�
		
	}
	
	public void init(int typeCode) {
		this.typeCode = typeCode;
		//TODO ����actCode��ֵˢ�·���
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			actPoints[i] = new Point(points[i].x,points[i].y);
		}
	}

	public Point[] getActPoints() {
		return actPoints;
	}

	/**
	 * �����ƶ�
	 * 
	 * @param moveX X��ƫ����
	 * @param moveY Y��ƫ����
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {
		
		//�ƶ�����
		for (int i = 0; i < actPoints.length; i++) {
			int newX = actPoints[i].x + moveX;
			int newY = actPoints[i].y + moveY;
				if(isOverZone(newX, newY, gameMap)) {
					return false;
				}
			
		}
		for (int i = 0; i < actPoints.length; i++) {
			actPoints[i].x = actPoints[i].x + moveX;
			actPoints[i].y = actPoints[i].y + moveY;
		}
		return true;
		
	}
	/**
	 * ������ת
	 * 
	 * ˳ʱ��
	 * �ѿ�������ϵ��ת��ʽ
	 * A.x = 0.y + 0.x - B.y;
	 * A.y = 0.y - 0.x + B.y;
	 */
	public void round( boolean[][] gameMap) {
		
		if(!TYPE_ROUND.get(this.typeCode)) {
			return;
		}
		for (int i = 0; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			//TODO �ж��Ƿ����ѡ��
			if(isOverZone(newX,newY,gameMap)){
				return;
			}
			
		}
		for (int i = 0; i < actPoints.length; i++) {
			 int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			 int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			 actPoints[i].x = newX;
			 actPoints[i].y = newY;
		}
	}
	
	private boolean isOverZone(int x, int y,boolean[][] gameMap) {
		return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || gameMap[x][y];
	}
	/**
	 * ��÷�������
	 * @return
	 */
	public int getTypeCode() {
		return typeCode;
	}
	
	
	
}
