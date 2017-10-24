package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.entity.User;

public interface UserService {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User checkLogin(String phone,String password);

    int getCountofActivity(int userId);

    int getCountofJoinActivity(int userId);

    int insertUser(User user);

    int insertAdmin(User user);

    int checkPhone(String phone);

    int updateBond(User user);

    int updateToken(User user);

    int addToken(int token,String activityId);

}