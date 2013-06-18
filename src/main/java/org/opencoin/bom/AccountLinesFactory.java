package org.opencoin.bom;

import org.opencoin.bom.RippleBomFactory.BomCreator;
import org.opencoin.client.RippleWsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class AccountLinesFactory {
	private static final Logger log = LoggerFactory.getLogger(AccountLinesFactory.class);
	public static final String name = "lines";
	public AccountLinesFactory(RippleBomFactory factory){
		factory.register(name, new BomCreator() {
			@Override
			public void create(RippleWsClient client, String message) {
				AccountLines accountLines = AccountLinesFactory.create(client, message);
				if(accountLines != null){
					client.onAccountLines(accountLines);
				}
			}
		});
	}
	
	public static AccountLines create(
			RippleWsClient client, 
			String jsonContent)
	{
		Gson gson = new Gson();
		AccountLines accountLines = null;
		
		try {
			log.debug("create account lines from: " + jsonContent);
			accountLines = gson.fromJson(jsonContent, AccountLines.class);
			log.debug(gson.toJson(accountLines));
		} catch (JsonSyntaxException e) {
			log.error("create error: " + e.getMessage());
			client.onDecodingError(e.getMessage(), jsonContent);
		}
		return accountLines;
	}
}
