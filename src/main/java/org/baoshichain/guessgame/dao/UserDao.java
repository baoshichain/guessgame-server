package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.User;

import java.util.HashMap;

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

    //int updateBond(User user);

    //add my yin
    int reduceToken(int userId,int reduceNum);

    int refound(int userId, int token);

    HashMap selectBasicInformationById(int userId);

    int addToken(int token,String activityId);
}