package dao;

import java.util.List;

import dto.Player;

/**
 * ���ݳ־ò�ӿ�
 * @author a
 *
 */
public interface Data {
	
	/**
	 * �������
	 * 
	 */
	public List<Player> loadData();
	/**
	 * ��������
	 * 
	 */
	public void saveData(Player players);

}
