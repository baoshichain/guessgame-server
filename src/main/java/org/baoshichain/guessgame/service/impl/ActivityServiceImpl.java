package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.dao.ActivityDao;
import org.baoshichain.guessgame.dao.UserDao;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.ActivityService;
import org.baoshichain.guessgame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hisen on 17-4-24.
 */
@Service
public class ActivityServiceImpl implements ActivityService{
  @Autowired
  private ActivityDao activityDao;

  @Override
  public int deleteByPrimaryKey(Integer id) {
    return 0;
  }

  @Override
  public int insert(Activity record) {
    return 0;
  }

  @Override
  public int insertSelective(Activity record) {
    return activityDao.insertSelective(record);
  }

  @Override
  public Activity selectByPrimaryKey(Integer id) {
    return null;
  }

  @Override
  public int updateByPrimaryKeySelective(Activity record) {
    return 0;
  }

  @Override
  public int updateByPrimaryKey(Activity record) {
    return 0;
  }
}
