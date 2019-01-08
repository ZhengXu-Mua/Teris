package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSavePoint;
import ui.window.JPanelGame;


public class GameControl {
	
	private Data dataA;
	
	private Data dataB;
	
	/**
	 * 游戏界面层
	 */
	private JPanelGame panelGame;
	
	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;
	
	private JFrameConfig frameConfig;
	
	private JFrameSavePoint frameSavePoint;
	
	private Map<Integer,Method> actionList;
	
	private GameDto dto = null;
	
	private Thread gameThread = null;

	public GameControl() {
		//创建游戏数据源
		this.dto = new GameDto();
		//创建游戏逻辑块
		this.gameService = new GameTetris(dto);
		
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		this.dto.setDbRecode(dataA.loadData());
		
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		this.dto.setDiskRecode(dataB.loadData());
		
		//创建游戏面板
		this.panelGame = new JPanelGame(this, dto);
		
		this.setControlConfig();
		
		this.frameConfig = new JFrameConfig(this);
		
		this.frameSavePoint = new JFrameSavePoint(this);
		//创建游戏窗口
		new JFrameGame(this.panelGame);
			
	}
	/**
	 * 读取设置
	 */
	private void setControlConfig() {
		this.actionList = new HashMap<Integer, Method>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			HashMap<Integer, String> cfgSet =(HashMap<Integer, String>) ois.readObject();
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for(Entry<Integer, String> e : entryset) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建数据对象
	 * @param cfg
	 * @return
	 */
	private Data createDataObject(DataInterfaceConfig cfg) {
		try {
			//获得类对象
			Class<?> cls = Class.forName(cfg.getClassName());
			//获得构造器
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			//创建对象
			return (Data)ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void actionByKeyCode(int keyCode) {
		
		try {
			if(this.actionList.containsKey(keyCode)) {
				this.actionList.get(keyCode).invoke(this.gameService);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		this.panelGame.repaint();
	}
	
	
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}
	
	
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}
	
	
	public void start() {
		this.panelGame.buttonSwitch(false);
		this.frameConfig.setVisible(false);
		this.frameSavePoint.setVisible(false);
		this.gameService.startGame();
    	this.gameThread = new MainTread();
    	this.gameThread.start();
    	this.panelGame.repaint();
	}
	
	
	
	public void afterLose() {
		if(!this.dto.isCheat()) {
			this.frameSavePoint.showWindow(this.dto.getNowPoint());
		}
		this.panelGame.buttonSwitch(true);
		
	}
	
	private class MainTread extends Thread{
		@Override
		public void run() {
			panelGame.repaint();
			while(dto.isStart()) {
				try {
					Thread.sleep(dto.getSleepTime());
					//如果暂停，那么不执行主行为
					if(dto.isPause()) {
						continue;
					}
					gameService.mainAction();
					panelGame.repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			afterLose();
		}
	}

	public void savePoint(String name) {
		Player pla =new Player(name,this.dto.getNowPoint());
		this.dataA.saveData(pla);
		this.dataB.saveData(pla);
		this.dto.setDbRecode(dataA.loadData());
		this.dto.setDiskRecode(dataB.loadData());
		this.panelGame.repaint();
	}

	

//	public void keyUp() {
//		// TODO Auto-generated method stub
//		this.gameService.keyUp();
//		this.panelGame.repaint();
//	}
//
//	public void keyDown() {
//		// TODO Auto-generated method stub
//		this.gameService.keyDown();
//		this.panelGame.repaint();
//	}
//
//	public void keyLeft() {
//		// TODO Auto-generated method stub
//		this.gameService.keyLeft();
//		this.panelGame.repaint();
//	}
//
//	public void keyRight() {
//		// TODO Auto-generated method stub
//		this.gameService.keyRight();
//		this.panelGame.repaint();
//	}
//
// ======================测试专用方法=====================
//	public void testLevelZ() {
//		this.gameService.testLevelZ();
//		this.panelGame.repaint();
//	}


}
