package org.opencoin.bom;

import com.google.gson.annotations.SerializedName;

public class AccountInfo {
	@SerializedName("Account")
	private String account;
	private String balance;
	
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccount() {
		return account;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getBalance() {
		return balance;
	}
}
