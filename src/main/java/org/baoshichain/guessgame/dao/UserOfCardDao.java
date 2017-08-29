package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.UserOfCard;

public interface UserOfCardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOfCard record);

    int insertSelective(UserOfCard record);

    UserOfCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOfCard record);

    int updateByPrimaryKey(UserOfCard record);
}