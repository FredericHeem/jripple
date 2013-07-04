package org.opencoin.bom;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Business Object Model for the account lines
 * Query: {"command":"account_lines","account":"rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj"}
 * Response: {"result":{"account":"rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj","lines":[{"account":"rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B","balance":"5","currency":"USD","limit":"10","limit_peer":"0","quality_in":0,"quality_out":0},{"account":"rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B","balance":"0","currency":"BTC","limit":"0.1","limit_peer":"0","quality_in":0,"quality_out":0}]},"status":"success","type":"response"}
 *
 */
public class AccountLines {
	@SerializedName("account")
	private String account;
	
	@SerializedName("lines")
	private ArrayList<AccountLine> lineList;
	
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccount() {
		return account;
	}
	public void setLineList(ArrayList<AccountLine> lineList) {
		this.lineList = lineList;
	}
	public ArrayList<AccountLine> getLineList() {
		return lineList;
	}
	
}
