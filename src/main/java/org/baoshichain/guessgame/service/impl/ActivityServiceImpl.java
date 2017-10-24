package org.baoshichain.guessgame.service.impl;

import org.baoshichain.guessgame.bean.DrawLuck;
import org.baoshichain.guessgame.dao.ActivityDao;
import org.baoshichain.guessgame.dao.ActivityOfCardDao;
import org.baoshichain.guessgame.dao.CardDao;
import org.baoshichain.guessgame.dao.UserDao;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.entity.ActivityOfCard;
import org.baoshichain.guessgame.entity.Card;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.ActivityService;
import org.baoshichain.guessgame.util.TimerUtil;
import org.baoshichain.guessgame.util.WalletUtil;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.baoshichain.guessgame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;

import org.baoshichain.guessgame.bean.EthRoom;
import org.baoshichain.guessgame.contract.Game;
import org.baoshichain.guessgame.dao.*;
import org.baoshichain.guessgame.entity.*;
import org.web3j.utils.Numeric;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private ActivityOfCardDao activityOfCardDao;

    @Autowired
    private UserOfActivityDao userOfActivityDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WinnerDao winnerDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());




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


    SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Transactional
    @Override
    public int insert(DrawLuck drawLuck, User user) {
        String cardname = drawLuck.getCardname();
        String[] str = cardname.split(",");

        //插入房间 tb_activity
        Activity activity = new Activity();
        activity.setUserid(user.getId());
        activity.setActivityname(drawLuck.getActivityname());
        activity.setDescribe(drawLuck.getRoomdes());
        activity.setType("2"); //房间类型 抽奖
        activity.setFlag(1); //开始
        activity.setToken(drawLuck.getToken());
        //if (drawLuck.getProbability().contains("%")) {
         //   activity.setWinrate(drawLuck.getProbability().substring(0, drawLuck.getProbability().length() - 1));
        //}
        activity.setWinrate(drawLuck.getProbability());
        activity.setNummax("100"); //房间卡牌数目上限,默认100
        activity.setStartblock(String.valueOf(TimerUtil.getCurrentTimes())); //起始日期


        //计算房间中卡牌总数量
        List<String> name = new ArrayList<>();
        List<String> des = new ArrayList<>();
        List<String> price = new ArrayList<>();
        List<String> num = new ArrayList<>();
        int allnum=0;
        for (int i = 0; i < str.length; i++) {
            if (i % 4 == 0) {
                name.add(str[i]);
            }
            if (i % 4 == 1) {
                des.add(str[i]);
            }
            if (i % 4 == 2) {
                price.add(str[i]);
            }
            if (i % 4 == 3) {
                num.add(str[i]);
                allnum=allnum+Integer.parseInt(str[i]);
            }
        }
        activity.setNum(String.valueOf(allnum));//房间卡牌添加数目
        //房间起始，终止时间
        int limitTime = Integer.valueOf(drawLuck.getLimittime());
        dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String endTimes = dateFormater.format(new Date(date.getTime() + limitTime * 60 * 60 * 1000));
        activity.setEndblock(endTimes);  //限时
        activityDao.insert(activity);
        int activityId = activity.getId();
        logger.info("activityId=" + activityId);

        //插入卡牌 tb_card tb_cardofactivity
        for (int k = 0; k < name.size(); k++) {
            int cardnum =Integer.parseInt(num.get(k));
            logger.info("cardnum="+cardnum);
            for (int j = 0; j < cardnum; j++) {
                Card card = new Card();
                card.setName(name.get(k));
                card.setDiscribe(des.get(k));
                card.setPrice(price.get(k));
                cardDao.insert(card);

                //插入活动对应的card
                ActivityOfCard activityOfCard = new ActivityOfCard();
                activityOfCard.setActivityid(activityId);
                activityOfCard.setCardid(card.getId());
                activityOfCard.setFlag(0); //卡牌未发出 0 未出  1 开出
                activityOfCardDao.insert(activityOfCard);
            }
        }


        /*for(int i=0;i<name.size();i++){
            Card card=new Card();
            card.setName(name.get(i));
            card.setDiscribe(des.get(i));
            card.setPrice(price.get(i));
            cardDao.insert(card);

            //插入活动对应的card
            ActivityOfCard activityOfCard=new ActivityOfCard();
            logger.info("activityId="+activityId);
            activityOfCard.setActivityid(activityId);
            activityOfCard.setCardid(card.getId());
            activityOfCard.setFlag(0); //卡牌未发出
            activityOfCardDao.insert(activityOfCard);
        }*/
        return 1;
    }

    @Override
    public List<Activity> getActivityList(int page) {

        return activityDao.getActivityList((page-1)*5);
    }

    @Override
    public int getList(int activityId) {
        try {
            return activityDao.getList(activityId);
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Activity getActivityinfo(int id) {
        return activityDao.getActivityinfo(id);
    }

    @Override
    public int updateCardNum(int id) {
        return activityDao.updateCardNum(id);
    }

    @Override
    public int getActivityNum(int userid) {
        return activityDao.getActivityNum(userid);
    }

    @Override
    public List<Map> getUserOfActivity(int userid) {
        return activityDao.getUserOfActivity(userid);
    }

    @Override
    @Transactional
    public int addKjRoom(int userId, String activityName, String needToken, String max, String min, String time, String describe, String cardInfo) throws Exception {
        //new version
        //1.首先往acticity数据库里添加数据


        Date date = new Date();
        Long startTime = date.getTime();
        int needTime = Integer.parseInt(time);
        Activity activity = new Activity();
        activity.setUserid(userId);
        activity.setActivityname(activityName);
        activity.setToken(needToken);
        activity.setLimitmin(min);
        activity.setLimitmax(max);
        activity.setStartblock(dateFormater.format(new Date(startTime)));
        activity.setEndblock(dateFormater.format(new Date(startTime + needTime*60*60*1000)));
        activity.setDescribe(describe);
        activity.setNum("0");
        activity.setType("1");
        activity.setFlag(1);
        int result = activityDao.insert(activity);
        if(result < 1){
            throw new Exception("insert err");
        }
        //2.往card 和 activiry Card 里添加数据
        String[] cardInfoArr = cardInfo.split(",");
        Card card = new Card();
        ActivityOfCard aoc = new ActivityOfCard();
        for(int i=0;i<cardInfoArr.length/4;i++){
            int cardNum = Integer.parseInt(cardInfoArr[i*4+3]);
            for(int j =0 ;j< cardNum; j++){
                card.setName(cardInfoArr[i*4]);
                card.setDiscribe(cardInfoArr[i*4+1]);
                card.setPrice(cardInfoArr[i*4+2]);
                card.setId(null);
                cardDao.insert(card);
                aoc.setActivityid(activity.getId());
                aoc.setCardid(card.getId());
                activityOfCardDao.insert(aoc);
            }
        }
        return 1;
    }


    @Override
    public int joinLotteryActivity(int userId, int roomId) throws Exception {
        //判断是否已经参加过了
        int count = userOfActivityDao.selectUserActivityCountByActivityIdAndUserId(userId,roomId);
        System.out.println(count);
        if(count>0){
            return 2;
        }
        Activity activity = activityDao.selectByPrimaryKey(roomId);
        //判断是否有足够的钱
        User user = userDao.selectByPrimaryKey(userId);
        if(user.getToken()<Integer.parseInt(activity.getToken())){
            return 3;
        }
        //判断时间是否已经过了
        if(dateFormater.parse(activity.getEndblock()).getTime() < new Date().getTime()){
            return -1;
        }
        //判断是否还有足够的票
        int limitMax = Integer.parseInt(activityDao.selectByPrimaryKey(roomId).getLimitmax());
        int selled = userOfActivityDao.selectUserActivityCountByActivityId(roomId);
        if(limitMax<=selled){
            return -1;
        }
        activity.setNum(String.valueOf(Integer.parseInt(activity.getNum())+1));
        activityDao.updateByPrimaryKeySelective(activity);
        //扣钱
        userDao.reduceToken(userId,Integer.parseInt(activity.getToken()));
        //写入参加记录
        UserOfActivity uoa = new UserOfActivity();
        uoa.setActivityid(roomId);
        uoa.setUserid(userId);
        uoa.setTime(dateFormater.format(new Date().getTime()));
        userOfActivityDao.insert(uoa);
        //如果插入后，满足最大开奖要求了
        if(selled+1>=limitMax){
            kj(roomId);
        }
        return 1;
    }

    @Override
    public List<HashMap> normalKJRoomList() throws ParseException {
        List<HashMap> datalist = activityDao.selectAllNormalLotteryActivity();
        //循环判断
        for(int i=0;i<datalist.size();i++){
            //取出判断时间
            HashMap dataMap = datalist.get(i);
            if(dateFormater.parse(dataMap.get("endBlock").toString()).getTime() < new Date().getTime()) {
                //如果有已经过期的
                //1.先判断是否达到开奖要求
                if(Integer.parseInt(dataMap.get("num").toString()) > Integer.parseInt(dataMap.get("limitMin").toString())){
                    kj((int)dataMap.get("id"));
                    datalist.remove(i);
                }else{
                    //退钱处理
                    //查询所有参加该活动的id，循环退钱
                    int token = Integer.parseInt(dataMap.get("token").toString());
                    int roomId = Integer.parseInt(dataMap.get("id").toString());
                    List<UserOfActivity> list = userOfActivityDao.selectByActivityId((int)dataMap.get("id"));
                    for(int k =0; k<list.size(); k++){
                        int userId = list.get(k).getUserid();
                        userDao.refound(userId,token);
                    }
                    Activity activity = activityDao.selectByPrimaryKey(roomId);
                    activity.setFlag(-1);
                    activityDao.updateByPrimaryKeySelective(activity);
                    datalist.remove(i);
                }
            }
        }
        return datalist;
    }

    @Override
    public User getAdminName(int activityId) {
        return activityDao.getAdminName(activityId);
    }

    @Override
    public HashMap kJRoomDetail(int userId, int roomId) {
        HashMap dataMap = activityDao.selectKJRoomDetail(roomId);
        HashMap useMap = userDao.selectBasicInformationById(userId);
        dataMap.putAll(useMap);
        return dataMap;
    }

    @Override
    public HashMap allJoinedKjRoom(int userId) {
        HashMap dataMap = userDao.selectBasicInformationById(userId);
        int count = userOfActivityDao.selectCountByUserId(userId);
        List<HashMap> activityList = activityDao.selectAllJoinedKjRoom(userId);
        dataMap.put("count", count);
        dataMap.put("kjroomInfo", activityList);
        return dataMap;
    }


    public void kj(int roomId){
        Activity activity = activityDao.selectByPrimaryKey(roomId);
        int joinedNum =  Integer.parseInt(activity.getNum());
        int random = (int) (Math.random()*joinedNum-1);
        int rewardUserId = userOfActivityDao.getRewardUserId(roomId,random);
        //设置房间已经被处理
        activity.setFlag(-1);
        activityDao.updateByPrimaryKeySelective(activity);
        //设置获奖表
        Winner winner = new Winner();
        winner.setUserid(rewardUserId);
        winner.setActivityid(roomId);
        winnerDao.insert(winner);
    }




}
