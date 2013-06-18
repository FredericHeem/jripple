package org.opencoin.bom;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.opencoin.bom.RippleBomFactory.BomCreator;
import org.opencoin.client.RippleWsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class AccountInfoFactory {
	private static final Logger log = LoggerFactory.getLogger(AccountInfoFactory.class);
	public static final String name = "account_data";
	public AccountInfoFactory(RippleBomFactory factory){
		factory.register(name, new BomCreator() {
			@Override
			public void create(RippleWsClient client, String message) {
				AccountInfo accountInfo = AccountInfoFactory.create(client, message);
				if(accountInfo != null){
					client.onAccountInfo(accountInfo);
				}
			}
		});
	}
	
	public static AccountInfo create(
			RippleWsClient client, 
			String jsonContent)
	{
		log.debug("create account info from: " + jsonContent);
		JSONObject json = (JSONObject) JSONValue.parse(jsonContent);
		if(json == null){
			client.onDecodingError("cannot parse to json", jsonContent);
			return null;
		}

		JSONObject accountData = (JSONObject)json.get("account_data");
		log.debug("create: " + accountData);
		if(accountData == null){
			client.onDecodingError("cannot decode result", jsonContent);
			return null;
		}
		
		Gson gson = new Gson();
		AccountInfo accountInfo = null;
		
		try {
			log.debug("create account info data from: " + accountData.toJSONString());
			accountInfo = gson.fromJson(accountData.toJSONString(), AccountInfo.class);
			log.debug(gson.toJson(accountInfo));
		} catch (JsonSyntaxException e) {
			log.error("create AccountInfo error: " + e.getMessage());
			client.onDecodingError(e.getMessage(), jsonContent);
		}
		return accountInfo;
	}
}
