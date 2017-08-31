package org.baoshichain.guessgame.web;

import org.baoshichain.guessgame.contract.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by hisen on 17-4-24.
 */
@Controller
@RequestMapping("/game/eth")
public class GameController {
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
        }
    }



  @RequestMapping("/lottery/add")
  private String addActivity(){
      //test
      Future<TransactionReceipt> transactionReceiptFuture = guessGameContract.newGame(new Uint256(2), new Uint256(10), new Uint256(7));
      try {
          System.out.println(transactionReceiptFuture.get().getTransactionHash());

          return transactionReceiptFuture.get().getTransactionHash();
      } catch (InterruptedException e) {
          e.printStackTrace();
      } catch (ExecutionException e) {
          e.printStackTrace();
      }
      return null;
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
        }
        return null;
    }

    @RequestMapping("/lottery/finish")
    private String finishActivity(){
        //test
        Future<TransactionReceipt> transactionReceiptFuture = guessGameContract.getResult(new Uint256(2));
        try {
            System.out.println(transactionReceiptFuture.get().getTransactionHash());
            return transactionReceiptFuture.get().getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/lottery/join")
    private String joinActivity(){
        //test
        Future<TransactionReceipt> transactionReceiptFuture = guessGameContract.joinGame(new Uint256(2),new Uint256(new BigInteger("13342579293")), new Uint256(5));
        try {
            System.out.println(transactionReceiptFuture.get().getTransactionHash());
            return transactionReceiptFuture.get().getTransactionHash();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/lottery/address")
    private String getGameAddress(){
        //test
        Future<Address> transactionReceiptFuture = guessGameContract.gameMap(new Uint256(2));
        String address = null;
        try {
            address = Numeric.toHexString(transactionReceiptFuture.get().getValue().toByteArray());
            System.out.println("address:"+address);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
