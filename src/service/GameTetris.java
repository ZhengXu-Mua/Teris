package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {
	private GameDto dto;

	/**
	 * 生产随机数
	 */
	private Random random = new Random();
	/**
	 * 放开种类
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() ;
	
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	
	private static final Map<Integer,Integer> PLUS_POINT = GameConfig.getSystemConfig().getPlusPoint();
	
	public GameTetris(GameDto dto) {
		this.dto = dto;
		
	}
	
	public boolean keyUp() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
		return true;
	}

	public boolean keyDown() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			if(this.dto.getGameAct().move(0, 1, this.dto.getGameMap())){
				return false;
			}
			//获得游戏地图对象
			boolean[][] map= this.dto.getGameMap();
			Point[] act = this.dto.getGameAct().getActPoints();
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
				
			}
			int plusExp = this.plusExp();
			if(plusExp > 0) {
				this.plusePoint(plusExp);
			}
			this.dto.getGameAct().init(this.dto.getNext());		
			this.dto.setNext(random.nextInt(MAX_TYPE));
			if(this.isLose()) {
				this.dto.setStart(false);
			}
		}
		return true;
	}
	
	

	private boolean isLose() {
		Point[] actPoints = this.dto.getGameAct().getActPoints();
		boolean[][] map = this.dto.getGameMap();
		for (int i = 0; i < actPoints.length; i++) {
			if(map[actPoints[i].x][actPoints[i].y]) {
				return true;
			}
		}
		return false;
	}

	private void plusePoint(int plusExp) {
		int level = this.dto.getNowLevel();
		int rmLine = this.dto.getNowRemoveLine();
		int point = this.dto.getNowPoint();
		if(rmLine % LEVEL_UP + plusExp >= LEVEL_UP) {
			this.dto.setNowLevel(++level);
		}
		this.dto.setNowRemoveLine(rmLine + plusExp);
	
		this.dto.setNowPoint(point + PLUS_POINT.get(plusExp));
	}

	private int plusExp() {
		boolean[][] map = this.dto.getGameMap();  
		int exp = 0;
		for (int y = 0; y < GameDto.GAMEZONE_H; y++) {
			if(this.isCanRemoveLine(y, map)) {
				this.removeLine(y,map);
				exp++;
			}
		}
		return exp;
	}
	
	private void removeLine(int rowNumber, boolean[][] map) {
		for (int x = 0; x < GameDto.GAMEZONE_W; x++) {
			for (int y = rowNumber; y > 0; y--) {
				map[x][y] = map[x][y - 1];
			}
			map[x][0] = false;
		}
	}

	private boolean isCanRemoveLine(int y, boolean[][] map) {
		for (int x = 0; x <GameDto.GAMEZONE_W; x++) {
			if(!map[x][y]) {
				//如果有一个方格为false，跳到下一行
				return false;
			}
		}
		return true;
	}

	public boolean keyLeft() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
		return true;
	}

	public boolean keyRight() {
		if(this.dto.isPause()) {
			return true;
		}
		synchronized (this.dto) {
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
		return true;
	}


	@Override
	public boolean keyFunUp() {
		//TODO 作弊
		this.dto.setCheat(true);
		this.plusePoint(4);
		return true;
	}

	@Override
	public boolean keyFunDown() {
		if(this.dto.isPause()) {
			return true;
		}
		while(!this.keyDown());
		return true;
	}

	@Override
	public boolean keyFunLeft() {
		this.dto.changeShowShadow();
		return true;
		
	}

	@Override
	public boolean keyFunRight() {
		if(this.dto.isStart()) {
			this.dto.changPause();
		}
		return true;
	}

	@Override
	public void startGame() {
		this.dto.setNext(random.nextInt(MAX_TYPE));
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		this.dto.setStart(true);
		this.dto.dtoInit();
	}
	
	public void mainAction() {
		this.keyDown();
	}

}
