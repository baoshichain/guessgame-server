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
import org.springframework.beans.factory.annotation.Value;
import org.baoshichain.guessgame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;

import org.baoshichain.guessgame.bean.EthRoom;
import org.baoshichain.guessgame.contract.Game;
import org.baoshichain.guessgame.dao.*;
import org.baoshichain.guessgame.entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.HashMap;
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
    //ResourceBundle resource = null;
    String ethUrl = "http://192.168.132.133:8545";
    String gameAddress = "0x83f50B60Cf76BDe819e4cc1dF6b5d4f16D55CAf8";
    String adminPassword = "123";
    String keystorePath = "D:/workspace/baoshichain/src/main/resources/UTC--2017-07-26T10-38-48.569288272Z--e559eddf4367634912316d71d4f0b52766c64a79";
    BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    BigInteger GAS_LIMIT = BigInteger.valueOf(4_700_000);
    Web3j web3 = null;
    Credentials credentials = null;
    Game guessGameContract = null;



    @PostConstruct
    public void initWeb3() {
        web3 = Web3j.build(new HttpService(ethUrl));
        try {
            credentials = WalletUtils.loadCredentials(adminPassword, keystorePath);
            guessGameContract = Game.load(gameAddress, web3, credentials, GAS_PRICE, GAS_LIMIT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }


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

    @Transactional
    @Override
    public int insert(DrawLuck drawLuck,User user) {
        String cardname=drawLuck.getCardname();
        String[] str=cardname.split(",");

        //插入房间 tb_activity
        Activity activity=new Activity();
        activity.setUserid(user.getId());
        activity.setActivityname(drawLuck.getActivityname());
        activity.setDescribe(drawLuck.getRoomdes());
        activity.setType("2"); //房间类型 抽奖
        activity.setFlag(1); //开始
        activity.setToken(drawLuck.getToken());
        if(drawLuck.getProbability().contains("%")){
            activity.setWinrate(drawLuck.getProbability().substring(0,drawLuck.getProbability().length()-1));
        }
        activity.setNummax(drawLuck.getCardnum()); //房间卡牌数目上限
        activity.setStartblock(String.valueOf(TimerUtil.getCurrentTimes())); //起始日期
        activity.setNum(String.valueOf(str.length/3));//房间卡牌添加数目

        int limitTime = Integer.valueOf(drawLuck.getLimittime());
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String endTimes = dateFormater.format(new Date(date.getTime() + limitTime*60*60*1000));
        activity.setEndblock(endTimes);  //限时
        activityDao.insert(activity);

        //插入卡牌 tb_card
        List<String> name=new ArrayList<>();
        List<String> des=new ArrayList<>();
        List<String> price=new ArrayList<>();
        for(int i=0;i<str.length;i++){
            if(i%3==0){
               name.add(str[i]);
            }
            if(i%3==1){
               des.add(str[i]);
            }
            if(i%3==2){
               price.add(str[i]);
            }
        }
        for(int i=0;i<name.size();i++){
            Card card=new Card();
            card.setName(name.get(i));
            card.setDiscribe(des.get(i));
            card.setPrice(price.get(i));
            cardDao.insert(card);
            //插入活动对应的card
            ActivityOfCard activityOfCard=new ActivityOfCard();
            activityOfCard.setActivityid(activity.getId());
            activityOfCard.setCardid(card.getId());
            activityOfCard.setFlag(0); //卡牌未发出
            activityOfCardDao.insert(activityOfCard);
        }
        return 1;
    }

    @Override
    public List<Activity> getActivityList() {
        return activityDao.getActivityList();
    }

    @Override
    public int getList(int activityId) {
        try {
            return activityDao.getList(activityId);
        }catch (Exception e){
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

  /*  @Override
    public List<Activity> selectByUserId(int userId) {
        return null;//activityDao.selectByUserId(userId);
    }*/
	
	 @Override
    @Transactional
    public int addEthActivityInfo(Activity activity, Card card) {
        activity.setFlag(0);
        activity.setNum(activity.getLimitmax());
        int activityResult = activityDao.insert(activity);
        int cardResult = cardDao.insert(card);
        int activityId = activity.getId();
        int cardId = card.getId();
        ActivityOfCard aoc = new ActivityOfCard();
        aoc.setCardid(cardId);
        aoc.setActivityid(activityId);
        activityOfCardDao.insert(aoc);


        //写入区块链
        //1.新建一个合约，返回合约地址
        Future<TransactionReceipt> newGameReceipt = guessGameContract.newGame(new Uint256(activityId), new Uint256(Integer.parseInt(activity.getLimitmax())), new Uint256(Integer.parseInt(activity.getLimitmin())));
        try {
            String transactionHash = newGameReceipt.get().getTransactionHash();
            System.out.println(transactionHash);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return 0;
        }
        //2.开始游戏
        Future<TransactionReceipt> startGameReceipt = guessGameContract.startGame(new Uint256(activityId), new Uint256(new BigInteger(activity.getEndblock())));
        try {
            String transactionHash2 = startGameReceipt.get().getTransactionHash();
            System.out.println(transactionHash2);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 0;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return 0;
        }
        //3.再写数据库
        try {
            String nowBlock = web3.ethBlockNumber().send().getBlockNumber().toString();
            int startBlock = Integer.parseInt(startGameReceipt.get().getBlockNumber().toString());
            int endBlock = startBlock +  Integer.parseInt(activity.getEndblock());
            Activity act = new Activity();
            act.setId(activityId);
            act.setStartblock(String.valueOf(startBlock));
            act.setEndblock(String.valueOf(endBlock));
            //act.setNum(String.valueOf(endBlock));
            activityDao.updateByPrimaryKeySelective(act);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return activityId;
    }

    @Override
    public List<Activity> selectAlllotteryActivity() {
        return activityDao.selectAllLotteryActivity();
    }

    @Override
    public List<EthRoom> selectAllLotteryActivityInfo() {
        List<Map> dataMaps = activityDao.selectAllLotteryActivityInfo();
        List<EthRoom> dataList = new ArrayList<EthRoom>();
        for (int i = 0; i < dataMaps.size(); i++) {
            EthRoom er = new EthRoom();
            er.setActivityId(String.valueOf(dataMaps.get(i).get("id")));
            er.setActivityName((String) dataMaps.get(i).get("activityname"));
            er.setCardPrice((String) dataMaps.get(i).get("price"));
            er.setActivityMembersNum(String.valueOf(dataMaps.get(i).get("membernum")));
            er.setStartBlock((String) dataMaps.get(i).get("startBlock"));
            er.setEndBlock((String) dataMaps.get(i).get("endBlock"));
            try {
                er.setNowBlock(web3.ethBlockNumber().send().getBlockNumber().toString());
            } catch (IOException e) {
                e.printStackTrace();
                er.setNowBlock("0");
                return null;
            }
            dataList.add(er);
        }
        return dataList;
    }

    @Override
    public EthRoom selectLotteryActivityInfoByActivityId(String id) {
        Map dataMap = activityDao.selectLotteryActivityDetailByActivityId(id);
        EthRoom er = new EthRoom();
        er.setActivityId(String.valueOf(dataMap.get("id")));
        er.setActivityName((String) dataMap.get("activityname"));
        er.setCardPrice((String) dataMap.get("price"));
        er.setActivityMembersNum(String.valueOf(dataMap.get("membernum")));
        er.setStartBlock((String) dataMap.get("startBlock"));
        er.setEndBlock((String) dataMap.get("endBlock"));
        er.setDescribe((String) dataMap.get("describe"));
        try {
            er.setNowBlock(web3.ethBlockNumber().send().getBlockNumber().toString());
        } catch (IOException e) {
            e.printStackTrace();
            er.setNowBlock("0");
            return null;
        }
        return er;

    }

    @Override
    @Transactional
    public int joinLotteryActivity(String id, String value, String phone, String userId) {
        int result = 0;
        try {
            //1.先判断钱够不够
            User user = userDao.selectByPrimaryKey(Integer.parseInt(userId));
            Activity activity = activityDao.selectByPrimaryKey(Integer.parseInt(id));
            if (user.getToken() < Integer.parseInt(activity.getToken())*Integer.parseInt(value)) {
                System.out.println("钱不够");
                return -1;
            }
            //2.先判断时间有没有过
            int nowBlock = Integer.parseInt(web3.ethBlockNumber().send().getBlockNumber().toString());
            int endBlock = Integer.parseInt(activityDao.selectByPrimaryKey(Integer.parseInt(id)).getEndblock()) ;
            if (nowBlock > endBlock) {
                System.out.println("时间已经过了");
                return 0;
            }
            //3.判断是否还有票可以投
            System.out.println("num:" + activity.getNum());
            System.out.println("value:" + Integer.parseInt(value));
            if (Integer.parseInt(activity.getNum()) < Integer.parseInt(value)) {
                System.out.println("无票可投");
                return 0;
            }
            //4.写入数据库
            int spend = Integer.parseInt(value) * Integer.parseInt(activity.getToken());
            int balance = user.getToken() - spend;
            user.setToken(balance);
            userDao.updateByPrimaryKeySelective(user);//扣除费用
            UserOfActivity uoa = new UserOfActivity();
            uoa.setUserid(Integer.parseInt(userId));
            uoa.setActivityid(Integer.parseInt(id));
            uoa.setFlag(value);
            System.out.println("userId:" + userId);
            result = userOfActivityDao.insert(uoa);//插入数据

            int balanceticket = Integer.parseInt(activity.getNum()) - Integer.parseInt(value);
            System.out.println("balance:" + String.valueOf(balanceticket));
            activity.setNum(String.valueOf(balanceticket));
            System.out.println(activity.toString());
            activityDao.updateByPrimaryKeySelective(activity);
            //5.再写入区块链
            Future<TransactionReceipt> transactionReceiptFuture = guessGameContract.joinGame(new Uint256(new BigInteger(id)), new Uint256(new BigInteger(phone)), new Uint256(new BigInteger(value)));
            String transactionHash = transactionReceiptFuture.get().getTransactionHash();
            System.out.println(transactionHash);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("err");
            return 0;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("err");
            return 0;
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("err");
            return 0;
        }
        return result;
    }

    @Override
    @Transactional
    public int checkActivityStatus(String id) {
        try {
            //1.检查房间状态，如果flag=1，表示该房间已经处理，直接放回
            Activity activity = activityDao.selectByPrimaryKey(Integer.parseInt(id));
            int flag = activity.getFlag();
            if (flag == 1) return -1;//房间已经处理
            //2.房间没有处理，检查房间是否已经过期
            int nowBlock = Integer.parseInt(web3.ethBlockNumber().send().getBlockNumber().toString());
            int endblock = Integer.parseInt(activity.getEndblock());
            //3.如果房间没过，则返回1，让用户继续参与抽奖
            System.out.println("endBlock:"+endblock);
            System.out.println("nowBlock:"+nowBlock);
            if (nowBlock <= endblock) return 1;//可以继续参与开奖
            if (endblock < nowBlock) {
                //4，如果房间过了，判断是否满足开奖的条件，如果满足，则开奖，区块链跑出结果，将其写入数据库，并修改数据库中activity表的flag状态
                int sellNum = Integer.parseInt(activity.getLimitmax()) - Integer.parseInt(activity.getNum());
                //满足开奖
                if (sellNum > Integer.parseInt(activity.getLimitmin())) {
                    //5.首先把区块链进行开奖
                    TransactionReceipt transactionReceipt = guessGameContract.getResult(new Uint256(new BigInteger(id))).get();
                    String result = guessGameContract.gameRewards(new Uint256(new BigInteger(id))).get().getValue().toString();
                    System.out.println("开奖结果:" + result);
                    //6.将结果写入到区块链中
                    User user = userDao.selectByPhone(result);
                    int rewardId = user.getId();
                    Winner winner = new Winner();
                    winner.setActivityid(Integer.parseInt(id));
                    winner.setUserid(rewardId);
                    winnerDao.insert(winner);
                    activity.setFlag(1);
                    activityDao.updateByPrimaryKeySelective(activity);
                    return 2;//新做出了修改，用户可以看到开奖信息了，同时不允许参加
                } else {
                    //不满足就退钱
                    //循环查询
                    List<UserOfActivity> userOfActivities = userOfActivityDao.selectByActivityId(Integer.parseInt(id));
                    for(int i=0;i<userOfActivities.size();i++){
                        UserOfActivity uoa = userOfActivities.get(i);
                        User user = userDao.selectByPrimaryKey(uoa.getUserid());
                        int finalToken = Integer.parseInt(uoa.getFlag())*Integer.parseInt(activity.getToken())+user.getToken();
                        user.setToken(finalToken);
                        userDao.updateByPrimaryKey(user);
                    }
                    //设置activityflag
                    activity.setFlag(1);
                    activityDao.updateByPrimaryKeySelective(activity);
                    return 3;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -2;
    }

}
