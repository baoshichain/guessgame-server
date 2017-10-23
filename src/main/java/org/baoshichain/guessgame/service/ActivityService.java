package org.baoshichain.guessgame.service;

import org.baoshichain.guessgame.bean.DrawLuck;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.Card;
import org.baoshichain.guessgame.bean.EthRoom;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public interface ActivityService {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    int insert(DrawLuck drawLuck,User user);

    //List<Activity> selectByUserId(int userId);

    List<Activity> getActivityList();

    int getList(int activityId);

    Activity getActivityinfo(int id);

    int updateCardNum(int id);

    int getActivityNum(int userid);

    List<Map> getUserOfActivity(int userid);





    int addKjRoom(int userId,String activityName, String needToken,String max, String min, String time, String describe,String cardInfo) throws Exception;

    HashMap kJRoomDetail(int userId, int roomId);

    int joinLotteryActivity(int userId, int roomId) throws Exception;

    List<HashMap> normalKJRoomList() throws ParseException;

}