package org.opencoin.bom;

import com.google.gson.annotations.SerializedName;

public class AccountInfo {
	@SerializedName("Account")
	private String account;
	@SerializedName("Balance")
	/**
	 * Balance in drop unit, 1 million drop = 1 XRP
	 */
	private String balance;
	
	@SerializedName("Flags")
	private int flags;
	
	@SerializedName("Sequence")
	private String sequence;
	
	@SerializedName("OwnerCount")
	private int ownerCount;
	
	@SerializedName("PreviousTxnLgrSeq")
	private String previousTxnLgrSeq;
	
	@SerializedName("PreviousTxnID")
	private String previousTxnID;
	
	@SerializedName("LedgerEntryType")
	private String ledgerEntryType ;
	
	@SerializedName("index")
	private String index;
	
	@SerializedName("EmailHash")
	private String emailHash;
	
	@SerializedName("urlgravatar")
	private String urlgravatar;
	
	@SerializedName("TransferRate")
	private String transferRate;
	
	@SerializedName("Domain")
	private String domain;
	
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
	public void setFlags(int flags) {
		this.flags = flags;
	}
	public int getFlags() {
		return flags;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getSequence() {
		return sequence;
	}
	public void setOwnerCount(int ownerCount) {
		this.ownerCount = ownerCount;
	}
	public int getOwnerCount() {
		return ownerCount;
	}
	public void setPreviousTxnLgrSeq(String previousTxnLgrSeq) {
		this.previousTxnLgrSeq = previousTxnLgrSeq;
	}
	public String getPreviousTxnLgrSeq() {
		return previousTxnLgrSeq;
	}
	public void setPreviousTxnID(String previousTxnID) {
		this.previousTxnID = previousTxnID;
	}
	public String getPreviousTxnID() {
		return previousTxnID;
	}
	public void setLedgerEntryType(String ledgerEntryType) {
		this.ledgerEntryType = ledgerEntryType;
	}
	public String getLedgerEntryType() {
		return ledgerEntryType;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getIndex() {
		return index;
	}
	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}
	public String getEmailHash() {
		return emailHash;
	}
	public void setUrlgravatar(String urlgravatar) {
		this.urlgravatar = urlgravatar;
	}
	public String getUrlgravatar() {
		return urlgravatar;
	}
	public void setTransferRate(String transferRate) {
		this.transferRate = transferRate;
	}
	public String getTransferRate() {
		return transferRate;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDomain() {
		return domain;
	}
}
