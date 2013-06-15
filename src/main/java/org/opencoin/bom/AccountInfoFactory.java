package org.opencoin.bom;

import org.opencoin.bom.RippleBomFactory.BomCreator;
import org.opencoin.client.RippleWsClientListener;
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
			public void create(RippleWsClientListener listener, String message) {
				AccountInfo accountInfo = AccountInfoFactory.create(listener, message);
				if(accountInfo != null){
					listener.onAccountInfo(accountInfo);
				}
			}
		});
	}
	
	public static AccountInfo create(
			RippleWsClientListener listener, 
			String jsonContent)
	{
		Gson gson = new Gson();
		AccountInfo accountInfo = null;
		
		try {
			accountInfo = gson.fromJson(jsonContent, AccountInfo.class);
			log.debug(gson.toJson(accountInfo));
		} catch (JsonSyntaxException e) {
			log.error("createAccountInfo error: " + e.getMessage());
			listener.onDecodingError(e.getMessage(), jsonContent);
		}
		return accountInfo;
	}
}
