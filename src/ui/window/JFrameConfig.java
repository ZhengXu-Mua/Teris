package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;

import control.GameControl;
import util.FrameUtil;

public class JFrameConfig extends JFrame{
	
	private JButton btnOk = new JButton("确定");
	
	private JButton btnCancel = new JButton("取消");
	
	private JButton btnUser = new JButton("应用");
	
	private TextCtrl[] keyText = new TextCtrl[8];
	
	private JLabel errorMsg = new JLabel();
	
	private JList skinList = null;
	
	private JPanel skinView = null;
	
	private DefaultListModel skinData = new DefaultListModel();
	
	private GameControl gameControl;
	
	private final static Image IMG_PSP = new ImageIcon("data/psp.jpg").getImage();
	
	private Image temp_skinImg = new ImageIcon("graphics/view.jpg").getImage();
	
	private final static String[] METHOD_NAMES = {
		"keyUp","keyDown","keyLeft","keyRight"
		,"keyFunUp", "keyFunDown" , "keyFunLeft" ,"keyFunRight"
	};
	
	private final static String PATH =  "data/control.dat";
	
	public JFrameConfig(GameControl gameControl) {
		this.gameControl = gameControl;
		//设置布局管理器为边界布局
		this.setLayout(new BorderLayout());
		this.setTitle("设置");
		//初始化按键输入
		this.initkeyTexts();
		this.add(createMainPanel(), BorderLayout.CENTER);
		//添加按钮面板
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		this.setResizable(false);
		this.setSize(610, 380);
		//居中
		FrameUtil.setFrameCenter(this);
		
	}

	
	private void initkeyTexts() {
		int x= 0;
		int y = 50;
		int w = 60;
		int h = 20;
		for (int i = 0; i < 4; i++) {
			keyText[i] = new TextCtrl(x,y,w,h, METHOD_NAMES[i]);
			y += 40;
		}
		x = 520;
		y = 50;
		for (int i = 4; i < 8; i++) {
			keyText[i] = new TextCtrl(x,y,w,h,METHOD_NAMES[i]);
			y += 40;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			@SuppressWarnings("unchecked")
			HashMap<Integer, String> cfgSet =(HashMap<Integer, String>) ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entryset = cfgSet.entrySet();
			for (Entry<Integer, String> e : entryset) {
				for(TextCtrl tc : keyText) {
					if(tc.getMethodName().equals(e.getValue())) {
						tc.setKeyCode(e.getKey());
					}
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private JPanel createButtonPanel() {
		//创建按钮面板
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		this.btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()) {
					setVisible(false);
					gameControl.setOver();
				}
			}
		});
		this.errorMsg.setForeground(Color.RED);
		
		jp.add(this.errorMsg);
		jp.add(this.btnOk);
		jp.add(this.btnUser);
		this.btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}
		});
		this.btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
			}
		});
		jp.add(this.btnCancel);
		return jp;
	}

	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("控制设置", this.createControlPanel());
		jtp.addTab("皮肤设置", this.createSkinPanel());
		
		return  jtp;
	}
	private Component createSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		this.skinData.addElement("默认皮肤");
		this.skinData.addElement("皮肤1");
		this.skinData.addElement("皮肤2");
		this.skinData.addElement("皮肤3");
	
		this.skinList = new JList(this.skinData);
		
		this.skinView = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(temp_skinImg, 0, 0,null);
			}
		};
		
		panel.add(new JScrollPane(this.skinList),BorderLayout.WEST);
		panel.add(this.skinView,BorderLayout.CENTER);
		
		
		
		
		return panel;
	}


	/**
	 * 玩家控制设置面板
	 * @return
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}	
		};
		jp.setLayout(null);
		for (int i = 0; i < keyText.length; i++) {
			jp.add(keyText[i]);
		}
		
		return jp;
	}
	
	
	private boolean writeConfig(){
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < keyText.length; i++) {
			int keyCode = this.keyText[i].getKeyCode();
			if(keyCode == 0) {
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(keyCode, this.keyText[i].getMethodName());
		}
		if(keySet.size() != 8) {
			this.errorMsg.setText("重复按键");
			return false;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.errorMsg.setText(e.getMessage());
			return false;
		}
		this.errorMsg.setText(null);
		return true;
	}

	
}
