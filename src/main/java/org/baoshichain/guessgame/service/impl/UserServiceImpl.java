package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.BookDao;
import org.baoshichain.guessgame.dao.UserDao;
import org.baoshichain.guessgame.entity.Book;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.BookService;
import org.baoshichain.guessgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

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
}
