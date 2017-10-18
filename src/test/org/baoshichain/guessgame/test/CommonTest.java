package org.baoshichain.guessgame.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.baoshichain.guessgame.contract.Game;
import org.baoshichain.guessgame.entity.User;
import org.baoshichain.guessgame.entity.Winner;
import org.baoshichain.guessgame.util.WalletUtil;
import org.junit.Test;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

/**
 * Created by think on 2017-08-30.
 */
public class CommonTest {
    @Test
    public void t1(){
        ResourceBundle resource = null;
        String ethUrl = null;
        String gameAddress = null;
        String adminPassword = null;
        String keystorePath = null;
        BigInteger GAS_PRICE = null;
        BigInteger GAS_LIMIT = null;
        Web3j web3 = null;
        Credentials credentials = null;
        Game guessGameContract = null;
        resource = ResourceBundle.getBundle("contract.properties",Locale.getDefault());
        ethUrl = resource.getString("eth.url");
        gameAddress = resource.getString("game.address");
        adminPassword = resource.getString("admin.password");
        keystorePath = resource.getString("admin.keystore");
        System.out.println(ethUrl);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, CipherException {
        b();
    }


    public static void b() throws IOException {
        Function function = new Function("gameRewards",
                Arrays.<Type>asList(new Uint256(new BigInteger("19"))),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {})
        );
        String encodedFunction = FunctionEncoder.encode(function);
        String rewardPhone = WalletUtil.getReward(encodedFunction);
        System.out.println(rewardPhone);
    }


    public static void a() throws ExecutionException, InterruptedException, IOException, CipherException {
        String ethUrl = "http://https://ropsten.infura.io:8545";
        String gameAddress = "0xe3b7c9d2dc7819b73bf64a982e3d0d8afdb2390d";
        String adminPassword = "123";
        String keystorePath = "C:\\Users\\think\\Desktop\\key";
        String adminAddress = "0xe559eddf4367634912316d71d4f0b52766c64a79";
        BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_700_000);
        Web3j web3 = null;
        Credentials credentials = null;
        Game guessGameContract = null;

        BigInteger nonce = WalletUtil.getNonce(WalletUtil.getWeb3jInstance(),adminAddress);
        Function function = new Function("newGame",
                Arrays.<Type>asList(new Uint256(2),new Uint256(new BigInteger("80")),new Uint256(new BigInteger("60")),new Uint256(new BigInteger("200"))),
                Collections.<TypeReference<?>>emptyList()
        );
        String encodedFunction = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,GAS_PRICE,GAS_LIMIT,gameAddress,encodedFunction);
        credentials = WalletUtils.loadCredentials(adminPassword,keystorePath);
        byte[] signed = TransactionEncoder.signMessage(rawTransaction,credentials);
        String hexValue = Numeric.toHexString(signed);
        String resultStr = WalletUtil.eth_sendRawTransaction(hexValue);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(resultStr);
        if(jsonNode.has("error")){
            System.out.println("err");
        }else{
            String txHash = jsonNode.get("result").asText();
            System.out.println(txHash);;
        }
    }
}


