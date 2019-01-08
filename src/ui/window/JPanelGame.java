package ui.window;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.FrameConfig;
import config.GameConfig;
import config.LayerConfig;
import control.GameControl;
import control.PlayerControl;
import dto.GameDto;
import ui.Img;
import ui.Layer;

public class JPanelGame extends JPanel {
	
	private static final int BTN_SIZE_W = GameConfig.getFrameConfig().getButtonConfig().getButtonW();
	
	private static final int BTN_SIZE_H = GameConfig.getFrameConfig().getButtonConfig().getButtonH();
	
	private List<Layer> layers = null;
	
	private JButton btnStart;
	
	private JButton btnConfig;
	
	private GameControl gameControl = null;
	
	public JPanelGame(GameControl gameControl,  GameDto dto) {
		//连接游戏控制器
		this.gameControl = gameControl;
		//设置布局管理器
		this.setLayout(null);
		//初始化组件
		this.initComponent();
		//初始化层
		this.initLayer(dto);
		
		this.addKeyListener(new PlayerControl(gameControl));
		
	}
	
	/**
	 * 安装玩家控制器
	 * @param control
	 */
	
	
	private void initComponent() {
		this.btnStart = new JButton(Img.BTN_START);
		
		this.btnStart.setBounds(
				GameConfig.getFrameConfig().getButtonConfig().getStartX(), 
				GameConfig.getFrameConfig().getButtonConfig().getStartY(),
				BTN_SIZE_W, BTN_SIZE_H);
		this.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControl.start();
			}
		});
		this.add(btnStart);
		
		this.btnConfig = new JButton(Img.BTN_CONFIG);
		
		this.btnConfig.setBounds(
				GameConfig.getFrameConfig().getButtonConfig().getUserConfigX(),
				GameConfig.getFrameConfig().getButtonConfig().getUserConfigY(),
				BTN_SIZE_W, BTN_SIZE_H);
		this.btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameControl.showUserConfig();
				
			}
		});
		this.add(btnConfig);
		
	}
	/**
	 * 初始化层
	 */
			
	private void initLayer(GameDto dto) {
		try {
			//获得游戏配置
			FrameConfig fCfg = GameConfig.getFrameConfig();
			//获得层配置
			List<LayerConfig> layersCfg = fCfg.getLayersConfig();
			//创建游戏层数组
			layers = new ArrayList<Layer>(layersCfg.size());
			//创建所有层对象
			for(LayerConfig layerCfg : layersCfg) {
				//获得类对象
				Class<?> cls = Class.forName(layerCfg.getClassName());
				//获得构造函数
				Constructor<?> ctr = cls.getConstructor(int.class, int.class, int.class, int.class);
				//调用构造函数创建对象
				Layer layer = (Layer)ctr.newInstance(
						layerCfg.getX(),layerCfg.getY(),layerCfg.getW(),layerCfg.getH()
						);
				//设置游戏数据对象 
				layer.setDto(dto);
				//把创建对象Layer对象放入集合
				layers.add(layer);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Override
	public void paintComponent(Graphics g) {
		//调用基类方法
		super.paintComponent(g);
//		//绘制游戏界面
		for (int i = 0 ;i < layers.size() ;layers.get(i++).paint(g));
		//返回焦点
		this.requestFocus();
	}
	
    /**
     * 控制按钮是否可以点击
     */
	public void buttonSwitch(boolean onOff) {
		this.btnConfig.setEnabled(onOff);
		this.btnStart.setEnabled(onOff);
		
	}
}
