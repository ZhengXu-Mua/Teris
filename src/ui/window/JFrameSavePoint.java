package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControl;
import util.FrameUtil;

public class JFrameSavePoint extends JFrame {
	
	private JLabel lbPoint = null;
	
	private JTextField txName= null;
	
	private JButton btnOk = null;
	
	private JLabel errMsg = null;
	
	private GameControl gameControl = null;
	
	public JFrameSavePoint(GameControl gameContro) {
		this.gameControl = gameContro;
		this.setTitle("保存记录");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createCom();
		this.createAction();
		
	}
	
	public void showWindow(int point) {
		this.lbPoint.setText("您的得分:" + point);
		this.setVisible(true);
	}
	
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txName.getText();
				if(name.length() > 16 || name == null || "".equals(name)) {
					errMsg.setText("名字输入错误");
				}else {
					setVisible(false);
					gameControl.savePoint(name);
				}
			}
		});
	}
	

	private void createCom() {
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.lbPoint = new JLabel();
		north.add(lbPoint);
		this.errMsg = new JLabel();
		this.errMsg.setForeground(Color.RED);
		north.add(this.errMsg);
		
		this.add(north, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		center.add(new JLabel("您的名字："));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);
		
		
		this.btnOk = new JButton("确定");
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		south.add(btnOk);
		this.add(south, BorderLayout.SOUTH);
	}
	
}
