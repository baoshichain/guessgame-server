package org.baoshichain.guessgame.dao;

import org.apache.ibatis.annotations.Param;
import org.baoshichain.guessgame.entity.ActivityOfCard;

import java.util.List;

public interface ActivityOfCardDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivityOfCard record);

    int insertSelective(ActivityOfCard record);

    ActivityOfCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ActivityOfCard record);

    int updateByPrimaryKey(ActivityOfCard record);

    List<ActivityOfCard> selectActivityOfCardList(int id);

    int updateActivityOfcard(@Param("cardid") int cardid, @Param("activityid") int activityid);
}