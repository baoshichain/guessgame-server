package org.baoshichain.guessgame.contract;

import org.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by think on 2017-08-30.
 */
public class GenerateContractFile {
    public static void wrapper(){
        Web3j web3;
        web3 = Web3j.build(new HttpService("http://192.168.132.133:8545/"));
        Web3ClientVersion web3ClientVersion;
        try {
            web3ClientVersion = web3.web3ClientVersion().sendAsync().get();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            System.out.println(clientVersion);
            Credentials credentials = WalletUtils.loadCredentials("123", "src/main/resources/contract/UTC--2017-07-26T10-38-48.569288272Z--e559eddf4367634912316d71d4f0b52766c64a79");
            String[] strArray={"generate","src/main/resources/contract/Game.bin","src/main/resources/contract/Game.abi","-o","src/main/java","-p","org.baoshichain.guessgame.contract"};
            SolidityFunctionWrapperGenerator.run(strArray);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        wrapper();
    }

}
