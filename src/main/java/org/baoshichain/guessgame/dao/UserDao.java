package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByPhone(String phone);

    int getCountofActivity(int userId);

    int getCountofJoinActivity(int userId);

    int insertUser(User user);

    int checkPhone(String phone);

    int updateBond(User user);

    int updateToken(User user);
}