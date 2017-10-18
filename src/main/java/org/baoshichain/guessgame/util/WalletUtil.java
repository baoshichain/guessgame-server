package org.baoshichain.guessgame.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WalletUtil {
	private static Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/"));
	
	public final static ObjectMapper mapper = new ObjectMapper(); 
	
	public static Web3j getWeb3jInstance() {
		return web3j;
	}
	
    public static BigInteger getNonce(Web3j web3j,String address) throws InterruptedException, ExecutionException {
    	EthGetTransactionCount ethGetTransactionCount;
		ethGetTransactionCount = web3j.ethGetTransactionCount(
				address, DefaultBlockParameterName.LATEST).sendAsync().get();
    	return ethGetTransactionCount.getTransactionCount();
    }
    
	public static final String getKeystoreStr(String password) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CipherException, JsonProcessingException{
		return new ObjectMapper()
				.writeValueAsString(Wallet
						.createStandard(password,
								Keys.createEcKeyPair()));
	}
	
	
	public static String doPost(String url, List<NameValuePair> params) {  
        String result = null;  
        HttpPost post = null;  
        try {
        	CloseableHttpClient httpclient = HttpClients.createDefault();  
            post = new HttpPost(url);  
            post.setHeader("Accept", "application/json; charset=UTF-8");  
            post.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));  
            HttpResponse response = httpclient.execute(post);  
            result = EntityUtils.toString(response.getEntity(), "UTF-8");  
            return result;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if(post != null) {  
                post.releaseConnection();  
            }  
        }  
        return null;  
    }

    public static String getLocalBlockNumber() throws IOException {
		//https://ropsten.etherscan.io/api?module=proxy&action=eth_blockNumber&apikey=YourApiKeyToken
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("module", "proxy"));
		params.add(new BasicNameValuePair("action", "eth_blockNumber"));
		params.add(new BasicNameValuePair("apikey", "YourApiKeyToken"));
		String resultStr = doPost("http://ropsten.etherscan.io/api",params);
		ObjectMapper mapper = new ObjectMapper();
		String value = mapper.readTree(resultStr).get("result").asText().substring(2);
		System.out.println(value);
		return String.valueOf(Long.parseLong(value,16));
	}
	
	public static String PrefixHexToDec(String Str) {
		return String.valueOf(Long.parseLong(Str.substring(2),16));
	}
	

	
	
	public static String eth_sendRawTransaction(String signedStr) {
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("module", "proxy"));
		params.add(new BasicNameValuePair("action", "eth_sendRawTransaction"));
		params.add(new BasicNameValuePair("hex", signedStr));
		params.add(new BasicNameValuePair("apikey", "YourApiKeyToken"));
		String resultStr = doPost("http://ropsten.etherscan.io/api",params);
		return resultStr;
	}

	public static String getReward(String data) throws IOException {
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("module", "proxy")); //页数
		params.add(new BasicNameValuePair("action", "eth_call"));
		params.add(new BasicNameValuePair("to", "0xe3b7c9d2dc7819b73bf64a982e3d0d8afdb2390d"));
		params.add(new BasicNameValuePair("data", data));
		params.add(new BasicNameValuePair("tag", "latest"));
		params.add(new BasicNameValuePair("apikey", "YourApiKeyToken"));
		String resultStr = doPost("http://ropsten.etherscan.io/api",params);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode results = mapper.readTree(resultStr);
		return PrefixHexToDec(results.get("result").asText());
	}


	
	public static void getStockRelaxStr(String address) {
		Function function = new Function("stockCantransactionOf", 
                Arrays.<Type>asList(new Address(address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        String encodedFunction = FunctionEncoder.encode(function);
        System.out.println(encodedFunction);
	}
	

	public static void getApproveTxCenterStr(String address , String value) {
	
	}
	
	public static String eventLogs(String address, String topic) {
		
		//test
		
		
		return null;
	}
	
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		/*JsonNode table = getTransactionListByAddrAndPage("0xe559edDF4367634912316D71d4f0b52766c64a79","1");
		System.out.println(table.get("result").get(0).get("blockNumber").asText());*/
		//getStockRelaxStr("0xc3ee4e4f1e26c039c43df1f2d72366a73a46b529");
		//System.out.println(getTxCenterStockRelaxByAddress("0xc3ee4e4f1e26c039c43df1f2d72366a73a46b529"));
		//getStockRelaxStr("0xb88e9dbf907207dd9b5b78f2ce248de670822282");
		//System.out.println(getTxCenterStockRelaxByAddress("0xb88e9dbf907207dd9b5b78f2ce248de670822282"));
	}
    
}
