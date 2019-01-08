package entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import config.GameConfig;

public class GameAct {
	/**
	 * 方块数组
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
		//TODO 配置文件
		
	}
	
	public void init(int typeCode) {
		this.typeCode = typeCode;
		//TODO 根据actCode的值刷新方块
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
	 * 方块移动
	 * 
	 * @param moveX X轴偏移量
	 * @param moveY Y轴偏移量
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {
		
		//移动处理
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
	 * 方块旋转
	 * 
	 * 顺时针
	 * 笛卡尔坐标系旋转公式
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
			//TODO 判断是否可以选择
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
	 * 获得方块类型
	 * @return
	 */
	public int getTypeCode() {
		return typeCode;
	}
	
	
	
}
