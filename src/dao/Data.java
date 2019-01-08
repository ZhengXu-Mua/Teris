package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层接口
 * @author a
 *
 */
public interface Data {
	
	/**
	 * 获得数据
	 * 
	 */
	public List<Player> loadData();
	/**
	 * 保存数据
	 * 
	 */
	public void saveData(Player players);

}
