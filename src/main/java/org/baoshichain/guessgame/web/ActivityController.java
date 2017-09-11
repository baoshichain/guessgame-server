package org.baoshichain.guessgame.web;

import jnr.ffi.annotations.In;
import jnr.ffi.annotations.Transient;
import net.sf.json.JSONObject;
import org.baoshichain.guessgame.bean.*;
import org.baoshichain.guessgame.entity.*;
import org.baoshichain.guessgame.service.*;
import org.baoshichain.guessgame.util.CommonUtil;
import org.baoshichain.guessgame.util.TimerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOfActivityService userOfActivityService;

    @Autowired
    private ActivityOfCardService activityOfCardService;

    @Autowired
    private WinnerService winnerService;

    /**
     * 创建抽奖房间
     *
     * @param drawLuck
     * @param session
     * @return
     */
    @RequestMapping(value = "/createluck", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(DrawLuck drawLuck, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int flag = activityService.insert(drawLuck, user);
            if (flag == 1) {
                return CommonUtil.constructHtmlResponse(200, "抽奖房间创建成功", null);
            } else {
                session.setAttribute("user", null);
                return CommonUtil.constructHtmlResponse(201, "创建失败", null);
            }
        }
        return CommonUtil.constructHtmlResponse(201, "创建失败", null);
    }

    /**
     * 抽奖房间详情
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/roominfo2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject showinfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            DrawLuckRoom room = new DrawLuckRoom();
            //查询房间
            List<Activity> alist = activityService.getActivityList();
            List<DrawLuckRoom.LuckRoom> lucklist = new ArrayList<>();
            for (Activity activity : alist) {
                int activityId = activity.getId();
                DrawLuckRoom.LuckRoom luckroom = new DrawLuckRoom.LuckRoom();
                luckroom.setActivityid(activity.getId());
                luckroom.setRoomname(activity.getActivityname());
                luckroom.setNum(Integer.parseInt(activity.getNum()));
                luckroom.setWinrate(activity.getWinrate());
                double price=activityService.getList(activityId);
                logger.info("price="+price);
                luckroom.setPrice(activityService.getList(activityId));
                lucklist.add(luckroom);
            }
            room.setList(lucklist);
            User newuser = userService.selectByPrimaryKey(user.getId());
            room.setToken(newuser.getToken());
            room.setUserId(user.getId());
            room.setUsername(user.getLoginname());
            logger.info("room=" + room);
            return CommonUtil.constructHtmlResponse(200, "查询列表成功", room);
        }
        return CommonUtil.constructHtmlResponse(201, "查询失败", null);
    }

    /**
     * 抽奖详情
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/roominfo/drawluck", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject showDrawluck(String activityid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            User newuser = userService.selectByPrimaryKey(user.getId());
            LuckRoomInfo luckRoomInfo = new LuckRoomInfo();
            luckRoomInfo.setToken(newuser.getToken());
            luckRoomInfo.setUserId(user.getId());

            int price = activityService.getList(Integer.parseInt(activityid));
            luckRoomInfo.setRoomprice(String.valueOf(price));
            //查询房间
            logger.info("activity_id=" + activityid);
            Activity activity = activityService.getActivityinfo(Integer.parseInt(activityid));
            logger.info("activity=" + activity.getId());
            luckRoomInfo.setDes(activity.getDescribe());
            luckRoomInfo.setRoomname(activity.getActivityname());
            luckRoomInfo.setRate(activity.getWinrate());
            luckRoomInfo.setNum(Integer.parseInt(activity.getNum()));
            return CommonUtil.constructHtmlResponse(200, "查询列表成功", luckRoomInfo);
        }
        return CommonUtil.constructHtmlResponse(201, "查询失败", null);
    }

    /**
     * 抽奖详情2,点击参与抽奖
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/win", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public JSONObject win(String rate, String activityid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            User newuser = userService.selectByPrimaryKey(user.getId());
            DrawluckResult drawluckResult = new DrawluckResult();
            drawluckResult.setToken(newuser.getToken()); //用户积分
            drawluckResult.setUserId(user.getId()); //用户id
            //创建随机数
            Random rand = new Random();
            int result = rand.nextInt(100);
            if (result < Integer.parseInt(rate)) { //未中奖
                drawluckResult.setResult("0"); //抽奖结果
            } else {
                drawluckResult.setResult("1"); //中奖
                //中奖后减少user 表中剩余卡牌数目，同时更改activityofcard 中flag 为1，标识当前card 抽走
                activityService.updateCardNum(Integer.parseInt(activityid));
                //更新acitivityofcard 某一卡牌的flag 值
                List<ActivityOfCard> activityOfCard = activityOfCardService.selectActivityOfCardList(Integer.parseInt(activityid));
                logger.info("activityofcard=" + activityOfCard.size());
                for(int i=0;i<activityOfCard.size();i++){
                    int cardid=activityOfCard.get(i).getCardid();
                    int flag=activityOfCardService.updateActivityOfcard(cardid,Integer.parseInt(activityid)); //更改当前cardid
                    logger.info("更改成功");
                    //同时向tb_winner 插入中奖的cardid
                    Winner winner=new Winner();
                    winner.setActivityid(Integer.parseInt(activityid));
                    winner.setUserid(user.getId());
                    winner.setCardid(cardid);
                    winner.setFlag(2); //1 开奖  2 抽奖
                    int results=winnerService.insertWinner(winner);
                    logger.info("results="+results);
                    break;
                }
            }
            //向数据库中插入用户参与的房间
            UserOfActivity userOfActivity = new UserOfActivity();
            userOfActivity.setActivityid(Integer.parseInt(activityid));
            userOfActivity.setUserid(user.getId());
            userOfActivity.setFlag("2"); //1 开奖 2 抽奖
            int flag = userOfActivityService.insertUser(userOfActivity);
            logger.info("flag=" + flag);
            return CommonUtil.constructHtmlResponse(200, "查询列表成功", drawluckResult);
        }
        return CommonUtil.constructHtmlResponse(201, "查询失败", null);
    }

    /**
     * 点击参与抽奖,进入前判断
     * 积分是否为0，或者小于房间参与积分， 房间时间是否过时，房间卡牌数目是否为0
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/roominfo/cardnum", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public JSONObject getCardNum(String activityid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            User newuser = userService.selectByPrimaryKey(user.getId());
            Activity activity = activityService.getActivityinfo(Integer.parseInt(activityid));

            //同一用户无法再次参加
            List<UserOfActivity> list= userOfActivityService.getJoinNum(activity.getId());
            for(int i=0;i<list.size();i++){
                UserOfActivity userOfActivity=list.get(i);
                if(userOfActivity.getUserid().equals(user.getId())){
                    return CommonUtil.constructHtmlResponse(201, "同一玩家无法再次参与抽奖", null);
                }
            }
            if ((newuser.getToken() < Integer.parseInt(activity.getToken()) || newuser.getToken() <= 0)) {
                return CommonUtil.constructHtmlResponse(201, "积分不足", null);
            }
            if (TimerUtil.compare_date(activity.getEndblock(), TimerUtil.getCurrentTimes()) != 1) {
                return CommonUtil.constructHtmlResponse(201, "活动已经结束", null);
            }
            if (Integer.parseInt(activity.getNum()) <= 0) {
                return CommonUtil.constructHtmlResponse(201, "房间卡牌数目已抽完，请换房间抽奖", null);
            }
            //参与人数是否达到上限
            //if(list.size()>Integer.parseInt(activity.getLimitmax())){
            //    return CommonUtil.constructHtmlResponse(201, "房间参与人数达到上限，请换房间抽奖", null);
            //}
            return CommonUtil.constructHtmlResponse(200, "查询列表成功", "ok");
        }
        return CommonUtil.constructHtmlResponse(201, "查询失败", null);
    }



    /**
     *  活动后台
     *
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/roomcontroll", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public JSONObject getCardNum(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            User newuser = userService.selectByPrimaryKey(user.getId());
            RoomControll roomControll=new RoomControll();
            List<RoomControll.Wininfo> wininfoList=new ArrayList<>();
            roomControll.setUserid(newuser.getId());
            roomControll.setToken(newuser.getToken());
            int  size = activityService.getActivityNum(newuser.getId());
            logger.info("size="+size);
            roomControll.setPublishnum(size);
            List<Map> list=winnerService.getWinnerList();
            logger.info("list="+list.size());
            for(int i=0;i<list.size();i++){
                RoomControll.Wininfo wininfo=new RoomControll.Wininfo();
                Map map=list.get(i);
                Iterator entries = map.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry entry = (Map.Entry) entries.next();
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    if(entry.getKey().equals("userId")){
                        wininfo.setUserid((Integer)entry.getValue());
                    }
                    if(entry.getKey().equals("flag")){
                        wininfo.setFlag((Integer)entry.getValue());
                    }
                    if(entry.getKey().equals("phone")){
                        wininfo.setPhone((String)entry.getValue());
                    }
                    if(entry.getKey().equals("activityname")){
                        wininfo.setActivityname((String)entry.getValue());
                    }
                }
                wininfoList.add(wininfo);
            }
            roomControll.setList(wininfoList);
            return CommonUtil.constructHtmlResponse(200, "查询列表成功", roomControll);
        }
        return CommonUtil.constructHtmlResponse(201, "查询失败", null);
    }

}