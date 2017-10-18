package org.baoshichain.guessgame.web;

import net.sf.json.JSONObject;
import org.baoshichain.guessgame.bean.EthActivity;
import org.baoshichain.guessgame.bean.EthRoom;
import org.baoshichain.guessgame.contract.Game;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.entity.Card;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.UserOfActivity;
import org.baoshichain.guessgame.service.ActivityService;
import org.baoshichain.guessgame.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/activity")
public class GameController {
    @Autowired
    private ActivityService activityService;




    @RequestMapping("/ethlottery/add")
    @ResponseBody
    private JSONObject addActivity(EthActivity ethActivity, HttpSession session) throws InterruptedException, ExecutionException, CipherException, IOException {
        User user = (User)session.getAttribute("user");
        //先判断用户是否已经缴纳保证金
        System.out.println("bond:"+user.getBond());
        if(Integer.parseInt(user.getBond()) < 100){
            return CommonUtil.constructHtmlResponse(301,"缴纳金不足",null);
        }
        //处理用户提交的开房（活动提交申请）
        //构造activity
        Activity activity = new Activity();
        activity.setActivityname(ethActivity.getActivityName());
        activity.setToken(ethActivity.getToken());
        activity.setLimitmax(ethActivity.getLimitmax());
        activity.setLimitmin(ethActivity.getLimitmin());
        activity.setDescribe(ethActivity.getActivityDescribe());
        activity.setEndblock(ethActivity.getBlockTime());
        activity.setUserid(((User) session.getAttribute("user")).getId());
        //构造card
        Card card = new Card();
        card.setName(ethActivity.getCardName());
        card.setPrice(ethActivity.getCardPrice());
        card.setDiscribe(ethActivity.getCardDescribe());
        activity.setFlag(0);
        activity.setType("1");
        activity.setNum(activity.getLimitmax());
        //提交
        int activityId = activityService.addEthActivityInfo(activity,card);
        if(activityId < 1){
            return CommonUtil.constructHtmlResponse(301,"操作失败",null);
        }
        return CommonUtil.constructHtmlResponse(200,"信息已经提交，请稍后查看",null);
    }



    @RequestMapping("/ethlottery/list")
    @ResponseBody
    private JSONObject ethActivityList() throws IOException {
        List<Map> ethRooms = activityService.selectAllLotteryActivityInfo();
        return CommonUtil.constructHtmlResponse(200,"ok",ethRooms);
    }

    @RequestMapping(value = "/ethlottery/detail")
    @ResponseBody
    public JSONObject ethActivityDetail(String id,HttpSession session) throws CipherException {

        //查询房间详细信息
        EthRoom ethRoom = activityService.selectLotteryActivityInfoByActivityId(id);
        //检查房间的状态
        int status = activityService.checkActivityStatus(id);
        //如果是已经结束的房间
        if(status == -1) return CommonUtil.constructHtmlResponse(300,"-1",ethRoom);//该房间已经被开奖
        if(status == 1)  return CommonUtil.constructHtmlResponse(200,"1",ethRoom);//正常状态，用户将允许进行一切活动
        if(status == 2) return CommonUtil.constructHtmlResponse(301,"2",ethRoom);//更新了状态，且该房间被开奖了，用户也将不允许继续参与
        if(status == 3) return CommonUtil.constructHtmlResponse(301,"3",ethRoom);//更新了状态，且该房间因为参与人数不够，所以退款，用户也将不允许继续参与
        if(status == -2) return CommonUtil.constructHtmlResponse(301,"-2",ethRoom);//非正常状态，发生错误
        return CommonUtil.constructHtmlResponse(301,"err",null);
    }

    @RequestMapping("/ethlottery/join")
    @ResponseBody
    private JSONObject joinActivity(String id, String value, HttpSession session) throws Exception {
        System.out.println("control value:"+value);
        /*String id,String value,String phone, String userId*/
        User user = (User) session.getAttribute("user");
        int result = activityService.joinLotteryActivity(id, value, user.getPhone(), user.getId().toString());
        if(result == -1){
            return CommonUtil.constructHtmlResponse(301,"余额不足",null);
        }
        if (result == 1){
            return CommonUtil.constructHtmlResponse(200,"成功",null);
        }
        return CommonUtil.constructHtmlResponse(302,"错误",null);
    }



}
