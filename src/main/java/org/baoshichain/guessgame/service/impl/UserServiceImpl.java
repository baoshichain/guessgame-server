package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.UserDao;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hisen on 17-4-24.
 */
@Service
public class UserServiceImpl implements UserService{
  @Autowired
  private UserDao userDao;


  @Override
  public int deleteByPrimaryKey(Integer id) {
    return 0;
  }

  @Override
  public int insert(User record) {
    return 0;
  }

  @Override
  public int insertSelective(User record) {
    return 0;
  }

  @Override
  public User selectByPrimaryKey(Integer id) {
    return userDao.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(User record) {
    return 0;
  }

  @Override
  public int updateByPrimaryKey(User record) {
    return 0;
  }

  @Override
  public User checkLogin(String phone, String password) {
    User user = userDao.selectByPhone(phone);
    if(user == null){
      return null;
    }
    if(user.getPassword().equals(password)){
      return user;
    }else{
      return null;
    }

  }

  /**
   * 获取发布的活动总数
   * @param userId
   * @return
   */
  @Override
  public int getCountofActivity(int userId) {
    return userDao.getCountofActivity(userId);
  }

  /**
   * 获取参与的活动总数
   * @param userId
   * @return
   */
  @Override
  public int getCountofJoinActivity(int userId) {
    return userDao.getCountofJoinActivity(userId);
  }

  @Override
  public int insertUser(User user) {
    User newuser=new User();
    newuser.setPassword(user.getPassword());
    newuser.setBond("");
    newuser.setEthaddress(user.getEthaddress());
    newuser.setFlag(0);
    newuser.setLoginname(user.getLoginname());
    newuser.setName(user.getName());
    newuser.setPhone(user.getPhone());
    newuser.setQq(user.getQq());
    newuser.setPortrait("");
    newuser.setHabit(user.getHabit());
    return userDao.insert(user);
  }

  @Override
  public int insertAdmin(User user) {
    User newuser=new User();
    newuser.setPassword(user.getPassword());
    newuser.setBond("");
    newuser.setEthaddress(user.getEthaddress());
    newuser.setFlag(1);
    newuser.setLoginname(user.getLoginname());
    newuser.setName(user.getName());
    newuser.setPhone(user.getPhone());
    newuser.setQq(user.getQq());
    newuser.setPortrait("");
    newuser.setHabit(user.getHabit());
    return userDao.insert(user);
  }

  @Override
  public int checkPhone(String phone) {
    return userDao.checkPhone(phone);
  }

  @Override
  public int updateBond(User user) {
    return userDao.updateBond(user);
  }

  @Override
  public int updateToken(User user) { //int userId, int token
    return userDao.updateToken(user);
  }


}
