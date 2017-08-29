package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.ActivityOfCard;

public interface ActivityOfCardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityOfCard record);

    int insertSelective(ActivityOfCard record);

    ActivityOfCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityOfCard record);

    int updateByPrimaryKey(ActivityOfCard record);
}