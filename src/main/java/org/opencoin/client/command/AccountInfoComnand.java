package org.opencoin.client.command;

import org.json.simple.JSONObject;

public class AccountInfoComnand extends RippleCommand {
	private String account;

	public AccountInfoComnand(String account){
		this.account = account;
		setCommandName("accountinfo");
	}
	
	public String toJsonString(){
		 JSONObject json = new JSONObject();
		 json.put("command", getCommandName());
		 //json.put("id", getId());
		 json.put("account", getAccount());
		 return json.toJSONString();
	}
	
	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return account;
	}
}
