package org.baoshichain.guessgame.contract;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class DeployContract {
    public static void main(String[] args) {
        try {
            Web3j web3;
            web3 = Web3j.build(new HttpService("http://120.24.232.80:8545/"));
            Credentials credentials = WalletUtils.loadCredentials("123", "src/main/resources/contract/UTC--2017-07-26T10-38-48.569288272Z--e559eddf4367634912316d71d4f0b52766c64a79");
            BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
            BigInteger GAS_LIMIT = BigInteger.valueOf(4_700_000);
            String deployHash = Game.deploy(web3,credentials,GAS_PRICE,GAS_LIMIT,new BigInteger("0")).get().getTransactionReceipt().get().getContractAddress();
            System.out.println(deployHash);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
