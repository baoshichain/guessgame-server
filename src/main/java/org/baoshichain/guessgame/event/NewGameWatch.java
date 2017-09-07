package org.baoshichain.guessgame.event;

import org.baoshichain.guessgame.contract.Game;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by think on 2017-09-04.
 */
public class NewGameWatch {
    public static void main(String[] args) {
        String ethUrl = "http://192.168.132.133:8545";
        String gameAddress = "0x83f50B60Cf76BDe819e4cc1dF6b5d4f16D55CAf8";
        String adminPassword = "123";
        String keystorePath = "D:/workspace/baoshichain/src/main/resources/UTC--2017-07-26T10-38-48.569288272Z--e559eddf4367634912316d71d4f0b52766c64a79";
        BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_700_000);
        Web3j web3 = Web3j.build(new HttpService(ethUrl));
        Credentials credentials = null;
        Game guessGameContract = null;
        try {
            credentials = WalletUtils.loadCredentials(adminPassword, keystorePath);
            guessGameContract = Game.load(gameAddress,web3,credentials,GAS_PRICE, GAS_LIMIT);
            guessGameContract.addGameLogEventObservable(DefaultBlockParameterName.EARLIEST,DefaultBlockParameterName.LATEST).subscribe(sub->{
                System.out.println("------------------------NewGameLog------------------------");
                System.out.println("（新建游戏）游戏Id："+sub.gameId.getValue().toString());
                System.out.println("（新建游戏）游戏合约地址："+sub.gameAddress.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }
}
