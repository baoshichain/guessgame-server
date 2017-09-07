package org.baoshichain.guessgame.dao;

import org.baoshichain.guessgame.bean.DrawLuck;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.bean.EthRoom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    public int insert(DrawLuck drawLuck);

    //List<Activity> selectByUserId(int userId);
    List<Activity> getActivityList();

    int getList(int activityId);

    Activity getActivityinfo(int id);

    int updateCardNum(int userid);

    int getActivityNum(int userid);

    List<Map> getUserOfActivity(int userid);
	
	List<Activity> selectAllLotteryActivity();

    List<Map> selectAllLotteryActivityInfo();

    Map selectLotteryActivityDetailByActivityId(String id);

}