package org.opencoin.bom;

import com.google.gson.annotations.SerializedName;

public class AccountLine {
	@SerializedName("account")
	private String account;

	@SerializedName("balance")
	/**
	 * balance in XRP, unlike account_data which is in drops.
	 */
	private String balance;
	
	@SerializedName("currency")
	private String currency;
	
	@SerializedName("quality_out")
	private int qualityOut;
	
	@SerializedName("quality_in")
	private int qualityIn;
	
	@SerializedName("limit")
	private String limit;
	
	@SerializedName("limit_peer")
	private String limitPeer;
	
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

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setQualityOut(int qualityOut) {
		this.qualityOut = qualityOut;
	}

	public int getQualityOut() {
		return qualityOut;
	}

	public void setQualityIn(int qualityIn) {
		this.qualityIn = qualityIn;
	}

	public int getQualityIn() {
		return qualityIn;
	}

	public void setLimitPeer(String limitPeer) {
		this.limitPeer = limitPeer;
	}

	public String getLimitPeer() {
		return limitPeer;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimit() {
		return limit;
	}
}
