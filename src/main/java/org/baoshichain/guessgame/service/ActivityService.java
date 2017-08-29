package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.entity.Activity;

public interface ActivityService {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);
}