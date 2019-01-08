package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer {

	
	private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();
	
	private static int START_Y = 0;
	
	private static int RECT_H = IMG_RECT_H + 4;
	
	private static int SPA = 0;
	
	protected LayerData(int x, int y, int w, int h) {
		super(x, y, w, h);
		SPA = (this.h - (IMG_RECT_H + 4) * 5 - (PADDING << 1) - Img.DB.getHeight(null)) / 5;
		START_Y = PADDING + Img.DB.getHeight(null) +SPA;
	}


/**
 * 绘制该窗口所有值槽
 * 
 * @param imgTitle
 * @param players
 * @param g
 */
	public void showData(Image imgTitle, List<Player>players,Graphics g) {
		g.drawImage(imgTitle,this.x + PADDING, this.y +PADDING, null);

		int nowPoint = this.dto.getNowPoint();
		for (int i = 0; i < MAX_ROW; i++) {
			Player pla = players.get(i);
			int recodePoint = pla.getPoint();
			double precent = (double)nowPoint / pla.getPoint();
			precent = precent > 1 ? 1.0 : precent;
			String strPoint = recodePoint == 0 ? null : Integer.toString(recodePoint);
			this.drawRect(START_Y + i*(RECT_H + SPA), 
					pla.getName(),strPoint,
					precent, g);
			}
		}

	@Override
	abstract public void paint(Graphics g);
	
}
