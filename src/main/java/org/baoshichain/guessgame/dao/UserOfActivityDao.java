package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.UserOfActivity;

import java.util.List;

public interface UserOfActivityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOfActivity record);

    int insertSelective(UserOfActivity record);

    UserOfActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOfActivity record);

    int updateByPrimaryKey(UserOfActivity record);

    int insertUser(UserOfActivity userOfActivity);

    List<UserOfActivity> getJoinNum(int activityid,int userid);

    List<UserOfActivity> getJoinUserNum(int activityid);


    List<UserOfActivity> getJoinNum(int activityid);

    List<UserOfActivity> selectByActivityId(Integer activityId);

    int selectUserActivityCountByActivityIdAndUserId(int userId, int activityId);

    int selectUserActivityCountByActivityId(int activityId);

    int getRewardUserId(int activityId,int random);


    User getAdminName(int activityId);


}