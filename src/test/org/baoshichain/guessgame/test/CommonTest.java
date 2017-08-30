package org.baoshichain.guessgame.test;

import org.baoshichain.guessgame.contract.Game;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;
import java.util.Locale;
import java.util.ResourceBundle;

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
}
