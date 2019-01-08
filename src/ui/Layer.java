package ui;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

/**
 *���ƴ���
 * 
 * @author xiaoE
 *
 */
public abstract class Layer {
	
	/**
	 * ����ͼƬ 260 36
	 */
	private static final Image IMG_NUMBER = new ImageIcon("graphics/default/string/num.png").getImage();
	/**
	 * ������Ƭ���
	 */
	protected static final int IMG_NUMBER_W = IMG_NUMBER.getWidth(null) / 10;
	/**
	 * ������Ƭ�߶�
	 */
	private static final int IMG_NUMBER_H = IMG_NUMBER.getHeight(null);

	private static Image IMG_RECT = Img.IMG_RECT;
	
	protected static final int IMG_RECT_H = IMG_RECT.getHeight(null);
	
	private static final int IMG_RECT_W = IMG_RECT.getWidth(null);
	
	
	private  final int rectW ; 
	
	protected static final int PADDING;
	
	protected static final int BORDER;
	
	static {
		//�����Ϸ����
		FrameConfig fCfg = GameConfig.getFrameConfig();
		PADDING = fCfg.getPadding();
		BORDER = fCfg.getBorder();
	}
	
	private static Image WINDOW_IMG = Img.WINDOW;
    
	private static int WINDOW_W = WINDOW_IMG.getHeight(null);
	
	private static int WINDOW_H = WINDOW_IMG.getWidth(null);
	
	private static final Font DEF_FONT = new Font("����",Font.BOLD ,20);
	
	
	/**
     * �������Ͻ�x����
     */
	
	protected int x ;
	
	
	/**
	 * �������Ͻ�y����
	 */
	
	protected int y;
	
	/**
	 * ���ڿ��
	*/
	
	protected int w;
	
	/**
	 * ���ڸ߶�
	 */
	
	protected int h;
	
	protected GameDto dto = null;
	
	protected Layer(int x ,int y ,int w ,int h ){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rectW = this.w - (PADDING << 1);
		
	}
	/**
	 * ���ƴ���
	 */
	protected  void createWindow(Graphics g) {
		//����
		g.drawImage(WINDOW_IMG, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
		//����
		g.drawImage(WINDOW_IMG, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINDOW_W - BORDER, BORDER, null);
		//����
		g.drawImage(WINDOW_IMG, w + x - BORDER, y ,x + w , y + BORDER, WINDOW_W - BORDER , 0, WINDOW_W, BORDER, null);
		//����
		g.drawImage(WINDOW_IMG, x, y + BORDER, x + BORDER, y + h -BORDER, 0, BORDER, BORDER, WINDOW_H - BORDER, null);
		//��
		g.drawImage(WINDOW_IMG, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINDOW_W - BORDER, WINDOW_H - BORDER, null);
		//����
		g.drawImage(WINDOW_IMG, x + w -BORDER, y + BORDER, x + w,y + h - BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W , WINDOW_H - BORDER, null);
		//����
		g.drawImage(WINDOW_IMG, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER, WINDOW_H, null);
		//����
		g.drawImage(WINDOW_IMG, x + BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER, WINDOW_H, null);
		//����
		g.drawImage(WINDOW_IMG, x + w -BORDER, y + h - BORDER, x + w, y + h, WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W, WINDOW_H, null);
	}
	
	 public void setDto(GameDto dto) {
		this.dto = dto;
	}
	/**
	  * ˢ����Ϸ��������
	  * @author zx
	  * @param g ����
	  */
	 abstract public void paint(Graphics g);
	 
	 /**
		 * ��ʾ����
		 * 
		 * @param x  ���Ͻ�x����
		 * @param y  ���Ͻ�y����
		 * @param num  Ҫ��ʾ������
		 * @param maxBit ����λ��
		 * @param g    ���ʶ���
		 */
		protected void drawNumberLeftPad(int x, int y,int num ,int maxBit,Graphics g) {
			//������number�е�ÿһλȡ����
			String strNum = Integer.toString(num);
			for (int i = 0; i < maxBit; i++) {
				if(maxBit - i <= strNum.length()) {
					int idx = i - maxBit + strNum.length();
				int bit = strNum.charAt(idx) - '0';
				g.drawImage(IMG_NUMBER, 
						this.x + x + IMG_NUMBER_W * i, this.y + y,
						this.x + x + IMG_NUMBER_W * (i + 1), this.y + y + IMG_NUMBER_H, 
						bit * IMG_NUMBER_W, 0,
						(bit + 1) * IMG_NUMBER_W, IMG_NUMBER_H, null);
				}
			}
			
		}
		
		private  void showNumberLeftPad(int rect_x, int rect_y,String number ,int maxBit,Graphics g) {
			//������number�е�ÿһλȡ����
			
			for (int i = 0; i < maxBit; i++) {
				if(maxBit - i <= number.length()) {
					int idx = i - maxBit + number.length();
				int bit = number.charAt(idx) - '0';
				g.drawString(Integer.toString(bit), rect_x + 231 + i * 10, rect_y +22);
				}
			}
			
		}
		
		
		protected void drawRect( int y,String title,String number,double precent, Graphics g) {
			//��ʼ��ֵ
			int rect_x = this.x + PADDING;
			int rect_y =  this.y + y;
			
			g.setColor(Color.BLACK);		
			g.fillRect(rect_x, rect_y, this.rectW, IMG_RECT_H + 4);
			
			g.setColor(Color.WHITE);
			g.fillRect(rect_x + 1, rect_y + 1, this.rectW - 2, IMG_RECT_H + 2);
			
			g.setColor(Color.BLACK);
			g.fillRect(rect_x + 2, rect_y + 2, this.rectW - 4,IMG_RECT_H);
			
			g.setColor(Color.GREEN);
			//TODO
			//����ֵ��
			
			
			//������
			int w =(int)(precent * (this.rectW - 4));
			//�����ɫ
			int subIdx =(int)(precent * IMG_RECT_W) - 1;
			g.drawImage(IMG_RECT, 
					rect_x + 2, rect_y + 2, 
					rect_x + 2 + w,rect_y + 2 + IMG_RECT_H, 
					subIdx, 0,subIdx + 1, IMG_RECT_H, null);
			
			g.setColor(Color.WHITE);
			g.setFont(DEF_FONT);
			g.drawString(title, rect_x + 5, rect_y +22);
			if(number !=null) {
				showNumberLeftPad(rect_x, rect_y,number ,5, g);
				
			}
			
		}
		
		protected void drawImageAtCenter(Image img,Graphics g) {
			int imgW = img.getWidth(null);
			int imgH = img.getHeight(null);
			g.drawImage(img, this.x + (this.w - imgW >> 1), this.y + (this.h - imgH >> 1), null);
			
		}
	
}
