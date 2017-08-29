package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.Card;

public interface CardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Card record);

    int insertSelective(Card record);

    Card selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);
}