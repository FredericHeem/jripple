package org.opencoin.bom;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Business Object Model for the account lines
 * Query: {"command":"account_lines","account":"rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj"}
 * Response: {"result":{"account":"rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj","lines":[{"account":"rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B","balance":"5","currency":"USD","limit":"10","limit_peer":"0","quality_in":0,"quality_out":0},{"account":"rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B","balance":"0","currency":"BTC","limit":"0.1","limit_peer":"0","quality_in":0,"quality_out":0}]},"status":"success","type":"response"}
 * @author frederic
 *
 */
public class AccountLines {
	@SerializedName("account")
	private String account;
	
	@SerializedName("lines")
	private ArrayList<Line> lineList;
	
	public class Line {
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
	
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccount() {
		return account;
	}
	public void setLineList(ArrayList<Line> lineList) {
		this.lineList = lineList;
	}
	public ArrayList<Line> getLineList() {
		return lineList;
	}
	
}
