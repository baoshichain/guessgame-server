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
    //ResourceBundle resource = null;
    String ethUrl = "http://https://ropsten.infura.io:8545";
    String gameAddress = "0xe3b7c9d2dc7819b73bf64a982e3d0d8afdb2390d";
    String adminPassword = "123";
    String keystorePath = "C:/Users/think/Desktop/key";
    String adminAddress = "0xe559eddf4367634912316d71d4f0b52766c64a79";
    BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    BigInteger GAS_LIMIT = BigInteger.valueOf(4_700_000);
    Web3j web3 = null;
    Credentials credentials = null;
    Game guessGameContract = null;



/*    @PostConstruct
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
    }*/


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
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    public List<Activity> getActivityList() {
        return activityDao.getActivityList();
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
    public int addEthActivityInfo(Activity activity, Card card) throws ExecutionException, InterruptedException, IOException, CipherException {
        int activityResult = activityDao.insert(activity);
        int cardResult = cardDao.insert(card);
        int activityId = activity.getId();
        int cardId = card.getId();
        ActivityOfCard aoc = new ActivityOfCard();
        aoc.setCardid(cardId);
        aoc.setActivityid(activityId);
        activityOfCardDao.insert(aoc);//至此，所有插入已经完成
        int result = 0;
        String txHash = "";
        //写入区块链
        //1.新建一个游戏，返回合约地址
        BigInteger nonce = WalletUtil.getNonce(WalletUtil.getWeb3jInstance(), adminAddress);
        Function function = new Function("newGame",
                Arrays.<Type>asList(new Uint256(activityId), new Uint256(new BigInteger(activity.getLimitmax())), new Uint256(new BigInteger(activity.getLimitmin())), new Uint256(new BigInteger(activity.getEndblock()))),
                Collections.<TypeReference<?>>emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GAS_PRICE, GAS_LIMIT, gameAddress, encodedFunction);
        credentials = WalletUtils.loadCredentials(adminPassword, keystorePath);
        byte[] signed = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signed);
        String resultStr = WalletUtil.eth_sendRawTransaction(hexValue);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(resultStr);
        if (jsonNode.has("error")) {
            return 0;
        } else {
            txHash = jsonNode.get("result").asText();
        }
        //3.再写数据库
        int startBlock = Integer.parseInt(WalletUtil.getLocalBlockNumber());
        int endBlock = startBlock + Integer.parseInt(activity.getEndblock());
        Activity act = new Activity();
        act.setId(activityId);
        act.setStartblock(String.valueOf(startBlock));
        act.setEndblock(String.valueOf(endBlock));
        activityDao.updateByPrimaryKeySelective(act);
        return activityId;
    }

    @Override
    public List<Activity> selectAlllotteryActivity() {
        return activityDao.selectAllLotteryActivity();
    }

    @Override
    public List<Map> selectAllLotteryActivityInfo() throws IOException {
        List<Map> dataMaps = activityDao.selectAllLotteryActivityInfo();
        //String nowBlock = WalletUtil.getLocalBlockNumber();
        return dataMaps;
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
            er.setNowBlock(WalletUtil.getLocalBlockNumber());
        } catch (IOException e) {
            e.printStackTrace();
            er.setNowBlock("0");
            return null;
        }
        return er;

    }

    @Override
    @Transactional
    public int joinLotteryActivity(String id, String value, String phone, String userId) throws Exception {
        int result = 0;
        try {
            //1.先判断钱够不够
            User user = userDao.selectByPrimaryKey(Integer.parseInt(userId));
            Activity activity = activityDao.selectByPrimaryKey(Integer.parseInt(id));
            if (user.getToken() < Integer.parseInt(activity.getToken()) * Integer.parseInt(value)) {
                System.out.println("钱不够");
                return -1;
            }
            //2.先判断时间有没有过
            int nowBlock = Integer.parseInt(WalletUtil.getLocalBlockNumber());
            int endBlock = Integer.parseInt(activityDao.selectByPrimaryKey(Integer.parseInt(id)).getEndblock());
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

            //先写入区块链
            BigInteger nonce = WalletUtil.getNonce(WalletUtil.getWeb3jInstance(), adminAddress);
            Function function = new Function("joinGame",
                    Arrays.<Type>asList(new Uint(new BigInteger(id)), new Uint256(new BigInteger(phone)), new Uint256(new BigInteger(value))),
                    Collections.<TypeReference<?>>emptyList()
            );
            String encodedFunction = FunctionEncoder.encode(function);
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GAS_PRICE, GAS_LIMIT, gameAddress, encodedFunction);
            credentials = WalletUtils.loadCredentials(adminPassword, keystorePath);
            byte[] signed = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signed);
            String resultStr = WalletUtil.eth_sendRawTransaction(hexValue);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(resultStr);
            String txHash = "";
            if (jsonNode.has("error")) {
                throw new Exception("could not get new blocknum");
            } else {
                txHash = jsonNode.get("result").asText();
            }
            System.out.println(txHash);
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
    public int checkActivityStatus(String id) throws CipherException {
        try {
            //1.检查房间状态，如果flag=1，表示该房间已经处理，直接放回
            Activity activity = activityDao.selectByPrimaryKey(Integer.parseInt(id));
            int flag = activity.getFlag();
            if (flag == 1) return -1;//房间已经处理
            //2.房间没有处理，检查房间是否已经过期
            int nowBlock = Integer.parseInt(WalletUtil.getLocalBlockNumber());
            int endblock = Integer.parseInt(activity.getEndblock());
            //3.如果房间没过，则返回1，让用户继续参与抽奖
            System.out.println("endBlock:" + endblock);
            System.out.println("nowBlock:" + nowBlock);
            if (nowBlock <= endblock) return 1;//可以继续参与开奖
            if (endblock < nowBlock) {
                //4，如果房间过了，判断是否满足开奖的条件，如果满足，则开奖，区块链跑出结果，将其写入数据库，并修改数据库中activity表的flag状态
                int sellNum = Integer.parseInt(activity.getLimitmax()) - Integer.parseInt(activity.getNum());
                //满足开奖
                //首先尝试读取，看是否已经运行result方法
                Function function = new Function("gameRewards",
                        Arrays.<Type>asList(new Uint256(new BigInteger(id))),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                        })
                );
                String encodedFunction = FunctionEncoder.encode(function);
                String rewardPhone = WalletUtil.getReward(encodedFunction);
                System.out.println("rewardPhone:" + rewardPhone);
                if (Long.parseLong(rewardPhone) > 0) {
                    User user = userDao.selectByPhone(rewardPhone);
                    int rewardId = user.getId();
                    Winner winner = new Winner();
                    winner.setActivityid(Integer.parseInt(id));
                    winner.setUserid(rewardId);

                    winnerDao.insert(winner);
                    activity.setFlag(1);
                    activityDao.updateByPrimaryKeySelective(activity);
                    return 2;//新做出了修改，用户可以看到开奖信息了，同时不允许参加
                }
                System.out.println(rewardPhone);
                if (sellNum > Integer.parseInt(activity.getLimitmin())) {
                    //5.首先把区块链进行开奖
                    Function function2 = new Function("getResult",
                            Arrays.<Type>asList(new Uint256(new BigInteger(id))),
                            Collections.<TypeReference<?>>emptyList()
                    );
                    String encodedFunction2 = FunctionEncoder.encode(function);
                    BigInteger nonce = WalletUtil.getNonce(WalletUtil.getWeb3jInstance(), adminAddress);
                    RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GAS_PRICE, GAS_LIMIT, gameAddress, encodedFunction);
                    credentials = WalletUtils.loadCredentials(adminPassword, keystorePath);
                    byte[] signed = TransactionEncoder.signMessage(rawTransaction, credentials);
                    String hexValue = Numeric.toHexString(signed);
                    String resultStr = WalletUtil.eth_sendRawTransaction(hexValue);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(resultStr);
                    String txHash = "";
                    if (jsonNode.has("error")) {
                        return 0;
                    } else {
                        txHash = jsonNode.get("result").asText();
                    }
                    System.out.println("开奖Hash:" + txHash);
                    //6.将结果写入到区块链中

                } else {
                    //不满足就退钱
                    //循环查询
                    List<UserOfActivity> userOfActivities = userOfActivityDao.selectByActivityId(Integer.parseInt(id));
                    for (int i = 0; i < userOfActivities.size(); i++) {
                        UserOfActivity uoa = userOfActivities.get(i);
                        User user = userDao.selectByPrimaryKey(uoa.getUserid());
                        int finalToken = Integer.parseInt(uoa.getFlag()) * Integer.parseInt(activity.getToken()) + user.getToken();
                        user.setToken(finalToken);
                        userDao.updateByPrimaryKey(user);
                        System.out.println("退钱成功");
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
