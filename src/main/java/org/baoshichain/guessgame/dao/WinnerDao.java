package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.Winner;

import java.util.List;
import java.util.Map;

public interface WinnerDao {

    int deleteByPrimaryKey(Integer id);

    int insert(Winner record);

    int insertWinner(Winner winner);

    List<Map> getWinnerList();
 int insertSelective(Winner record);

    Winner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Winner record);

    int updateByPrimaryKey(Winner record);
	
	List<Map> getWinnerListByUserId(int userId);
}
