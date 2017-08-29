package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.entity.ActivityOfCard;

public interface ActivityOfCardService {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityOfCard record);

    int insertSelective(ActivityOfCard record);

    ActivityOfCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityOfCard record);

    int updateByPrimaryKey(ActivityOfCard record);
}