package org.opencoin.bom;

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
		Gson gson = new Gson();
		AccountInfo accountInfo = null;
		
		try {
			accountInfo = gson.fromJson(jsonContent, AccountInfo.class);
			log.debug(gson.toJson(accountInfo));
		} catch (JsonSyntaxException e) {
			log.error("createAccountInfo error: " + e.getMessage());
			client.onDecodingError(e.getMessage(), jsonContent);
		}
		return accountInfo;
	}
}
