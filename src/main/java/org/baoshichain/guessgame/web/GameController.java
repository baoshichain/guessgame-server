package org.baoshichain.guessgame.web;

import net.sf.json.JSONObject;
<<<<<<< HEAD
import org.baoshichain.guessgame.bean.EthActivity;
import org.baoshichain.guessgame.bean.EthRoom;
import org.baoshichain.guessgame.contract.Game;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.entity.Card;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.UserOfActivity;
import org.baoshichain.guessgame.service.ActivityService;
=======
import org.baoshichain.guessgame.contract.Game;
import org.baoshichain.guessgame.entity.Activity;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.service.ActivityService;
import org.baoshichain.guessgame.service.UserService;
>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3
import org.baoshichain.guessgame.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMethod;
=======
>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/activity")
public class GameController {
<<<<<<< HEAD
=======
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

>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3
    @Autowired
    private ActivityService activityService;




<<<<<<< HEAD
    @RequestMapping("/ethlottery/add")
    @ResponseBody
    private JSONObject addActivity(EthActivity ethActivity, HttpSession session){
        User user = (User)session.getAttribute("user");
        System.out.println(user.toString());
        //���ж��û��Ƿ��Ѿ����ɱ�֤��
        if(Integer.parseInt(user.getBond()) < 100){
            return CommonUtil.constructHtmlResponse(301,"���ɽ���",null);
        }
        //�����û��ύ�Ŀ�������ύ���룩
        //����activity
        Activity activity = new Activity();
        activity.setActivityname(ethActivity.getActivityName());
        activity.setToken(ethActivity.getToken());
        activity.setLimitmax(ethActivity.getLimitmax());
        activity.setLimitmin(ethActivity.getLimitmin());
        activity.setDescribe(ethActivity.getActivityDescribe());
        activity.setEndblock(ethActivity.getBlockTime());
        activity.setUserid(((User) session.getAttribute("user")).getId());
        //����card
        Card card = new Card();
        card.setName(ethActivity.getCardName());
        card.setPrice(ethActivity.getCardPrice());
        card.setDiscribe(ethActivity.getCardDescribe());
        //�ύ
        int activityId = activityService.addEthActivityInfo(activity,card);
        if(activityId < 1){
            return CommonUtil.constructHtmlResponse(301,"����ʧ��",null);
=======
    @PostConstruct
    public void initWeb3(){
        //resource = ResourceBundle.getBundle("C:/Users/think/Documents/GitHub/guessgame-server/src/main/resources/contract.properties");
        //ethUrl = resource.getString("eth.url");
        //gameAddress = resource.getString("game.address");
        //adminPassword = resource.getString("admin.password");
        //keystorePath = resource.getString("admin.keystore");
        //GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
        //GAS_LIMIT = BigInteger.valueOf(4_700_000);
        web3 = Web3j.build(new HttpService(ethUrl));
        try {
            credentials = WalletUtils.loadCredentials(adminPassword, keystorePath);
            guessGameContract = Game.load(gameAddress,web3,credentials,GAS_PRICE, GAS_LIMIT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3
        }
        return CommonUtil.constructHtmlResponse(200,"��Ϣ�Ѿ��ύ�����Ժ�鿴",null);
    }



<<<<<<< HEAD
    @RequestMapping("/ethlottery/list")
    @ResponseBody
    private JSONObject ethActivityList(){
        List<EthRoom> ethRooms = activityService.selectAllLotteryActivityInfo();
        return CommonUtil.constructHtmlResponse(200,"ok",ethRooms);
    }

    @RequestMapping(value = "/ethlottery/detail")
    @ResponseBody
    public JSONObject ethActivityDetail(String id,HttpSession session){

        //��ѯ������ϸ��Ϣ
        EthRoom ethRoom = activityService.selectLotteryActivityInfoByActivityId(id);
        //��鷿���״̬
        int status = activityService.checkActivityStatus(id);
        //������Ѿ������ķ���
        if(status == -1) return CommonUtil.constructHtmlResponse(300,"-1",ethRoom);//�÷����Ѿ�������
        if(status == 1)  return CommonUtil.constructHtmlResponse(200,"1",ethRoom);//����״̬���û����������һ�л
        if(status == 2) return CommonUtil.constructHtmlResponse(301,"2",ethRoom);//������״̬���Ҹ÷��䱻�����ˣ��û�Ҳ���������������
        if(status == 3) return CommonUtil.constructHtmlResponse(301,"3",ethRoom);//������״̬���Ҹ÷�����Ϊ�������������������˿�û�Ҳ���������������
        if(status == -2) return CommonUtil.constructHtmlResponse(301,"-2",ethRoom);//������״̬����������
        return CommonUtil.constructHtmlResponse(301,"err",null);
    }

    @RequestMapping("/ethlottery/join")
    @ResponseBody
    private JSONObject joinActivity(String id, String value, HttpSession session){
        System.out.println("control value:"+value);
        /*String id,String value,String phone, String userId*/
        User user = (User) session.getAttribute("user");
        int result = activityService.joinLotteryActivity(id, value, user.getPhone(), user.getId().toString());
        if(result == -1){
            return CommonUtil.constructHtmlResponse(301,"����",null);
=======
    @RequestMapping("/lottery/add")
    @ResponseBody
    private JSONObject addActivity(Activity activity, HttpSession session){
        User user = (User)session.getAttribute("user");
        //先判断用户是否已经缴纳保证金
        if(Integer.parseInt(user.getBond()) < 100){
            return CommonUtil.constructHtmlResponse(301,"缴纳金不足",null);
        }
        //处理用户提交的开房（活动提交申请）
        int insertId = activityService.insert(activity);
        if(insertId <1){
            return  CommonUtil.constructHtmlResponse(302,"信息提交失败",null);
        }
        //写入数据库成功后，开始进行链上操作
        Future<TransactionReceipt> transactionReceiptFuture = guessGameContract.newGame(new Uint256(insertId), new Uint256(Integer.parseInt(activity.getLimitmax())), new Uint256(Integer.parseInt(activity.getLimitmin())));
        try {
            System.out.println(transactionReceiptFuture.get().getTransactionHash());

            // return transactionReceiptFuture.get().getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return CommonUtil.constructHtmlResponse(200,"成功",null);
    }

    @RequestMapping("/lottery/start")
    private String startActivity(){
        //test
        Future<TransactionReceipt> transactionReceiptFuture = guessGameContract.startGame(new Uint256(2),new Uint256(10));
        try {
            System.out.println(transactionReceiptFuture.get().getTransactionHash());
            return transactionReceiptFuture.get().getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
>>>>>>> d5be35ebbd69282512d4f61ab4316fd0e0d36fd3
        }
        if (result == 1){
            return CommonUtil.constructHtmlResponse(200,"�ɹ�",null);
        }
        return CommonUtil.constructHtmlResponse(302,"����",null);
    }



    @RequestMapping("/lottery/finish")
    private String finishActivity(){
      /*  //test
        Future<TransactionReceipt> transactionReceiptFuture = guessGameContract.getResult(new Uint256(2));
        try {
            System.out.println(transactionReceiptFuture.get().getTransactionHash());
            return transactionReceiptFuture.get().getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        return null;
    }



    @RequestMapping("/lottery/address")
    private String getGameAddress(){
        //test
/*        Future<Address> transactionReceiptFuture = guessGameContract.gameMap(new Uint256(2));
        String address = null;
        try {
            address = Numeric.toHexString(transactionReceiptFuture.get().getValue().toByteArray());
            System.out.println("address:"+address);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        return null;
    }












/*  @Autowired
  private BookService bookService;*/

/*  @RequestMapping(value = "/list", method = RequestMethod.GET)
  private String list(Model model) {
    List<Book> list = bookService.getList(0, 1000);
    model.addAttribute("list", list);
    return "list";// WEB-INF/jsp/"list".jsp
  }

  @RequestMapping(value = "/detail/{bookId}", method = RequestMethod.GET)
  private String detail(@PathVariable("bookId") Long bookId, Model model) {
    Book book = bookService.getById(bookId);
    model.addAttribute("book", book);
    return "detail";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
  @ResponseBody
  private String add(Book book) {
    Book hasBook = bookService.getById(book.getBookId());
    int i = -2;
    if (hasBook == null) {
      i = bookService.addBook(book);
    }
    return i > 0 ? "success" : "error";
  }

  @RequestMapping(value = "/del/{bookId}", method = RequestMethod.GET)
  @ResponseBody
  private String deleteBookById(@PathVariable("bookId") Long id) {
    int i = bookService.deleteBookById(id);
    return i > 0 ? "success" : "error";
  }*/
}
