package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;
import util.GameFunction;

public class GameDto {
	
	public static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX() + 1;
	
	public static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY() + 1;
	
	private List<Player> dbRecode;
	
	private List<Player> diskRecode;
	
	private boolean[][]gameMap;
	
	private GameAct gameAct;
	
	private int next;
	
	private int nowLevel;
	
	private int nowPoint;
	
	private int nowRemoveLine;
	
	private boolean start;
	
	private boolean showShadow;
	
	private boolean pause;
	
	private boolean cheat;
	
	private long sleepTime;
	
	/**
	 * 构造函数
	 */
	public GameDto() {
		dtoInit();
	}
	/**
	 * dto初始化
	 */
	public void dtoInit() {
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		this.nowLevel = 0;
		this.nowPoint = 0;
		this.nowRemoveLine = 0;
		this.pause = false;
		this.cheat = false;
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
	}

	public List<Player> getDbRecode() {
		return dbRecode;
	}

	public void setDbRecode(List<Player> dbRecode) {
		setFillRecode(dbRecode);
		this.dbRecode = dbRecode;
	}

	public List<Player> getDiskRecode() {
		return diskRecode;
	}

	public void setDiskRecode(List<Player> diskRecode) {
		setFillRecode(diskRecode);
		this.diskRecode = diskRecode;
	}

	public boolean[][] getGameMap() {
		return gameMap;
	}
	
	private List<Player> setFillRecode(List<Player>players) {
		if(players == null) {
			players = new ArrayList<Player>();
		}
		while(players.size() < 5 ){
			players.add(new Player("No Data",0));		
		}
		Collections.sort(players);
		return players;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getNowLevel() {
		return nowLevel;
	}

	public void setNowLevel(int nowLevel) {
		this.nowLevel = nowLevel;
		//计算下落速度
		this.sleepTime = GameFunction.getSleepTimeByLevel(this.nowLevel);
	}

	public int getNowPoint() {
		return nowPoint;
	}

	public void setNowPoint(int nowPoint) {
		this.nowPoint = nowPoint;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public boolean isShowShadow() {
		return showShadow;
	}
	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}
	public boolean isPause() {
		return pause;
	}
	public void changPause() {
		this.pause = !this.pause;
	}
	public boolean isCheat() {
		return cheat;
	}
	public void setCheat(boolean cheat) {
		this.cheat = cheat;
	}
	public long getSleepTime() {
		return sleepTime;
	}
	
}
