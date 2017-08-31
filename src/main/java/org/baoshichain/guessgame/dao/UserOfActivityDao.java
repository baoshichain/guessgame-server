package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.UserOfActivity;

public interface UserOfActivityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOfActivity record);

    int insertSelective(UserOfActivity record);

    UserOfActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOfActivity record);

    int updateByPrimaryKey(UserOfActivity record);
}