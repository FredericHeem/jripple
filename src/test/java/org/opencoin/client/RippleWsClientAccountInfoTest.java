package org.opencoin.client;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencoin.bom.AccountInfo;
import org.opencoin.client.command.AccountInfoCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleWsClientAccountInfoTest {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClientAccountInfoTest.class);
	String account;
	int timeout = 30; //seconds
	@Before
	public void setUp() throws Exception {
		account = "rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAccountInfo() {
		log.debug("testAccountInfo");
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		try {
			RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
				@Override
				public void onAccountInfo(AccountInfo accountInfo) {
					assertEquals(accountInfo.getAccount(), account);
					countDownLatch.countDown();
				}
			});

			AccountInfoCommand accountInfoCommand = new AccountInfoCommand(account);
			client.sendCommand(accountInfoCommand);
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				log.debug("timeout");
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}

	public AccountInfo retrieveAccountinfo(String account) throws RippleWsClientException {
		RippleWsClientSync clientSync = new RippleWsClientSync();
		return clientSync.retrieveAccountInfo(new AccountInfoCommand(account));
	}

	@Test
	public void testAccountInfoGrahamArmstrong() {
		log.debug("testAccountInfoGrahamArmstrong");
		String account = "rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh";
		try {
			AccountInfo accountInfo = retrieveAccountinfo(account);
			Assert.assertEquals(accountInfo.getAccount(), account);
			Assert.assertEquals(accountInfo.getEmailHash(), "D3FE8F927F7C2B3D398C5D6097D7E846");
			Assert.assertEquals(accountInfo.getUrlgravatar(), "https://www.gravatar.com/avatar/d3fe8f927f7c2b3d398c5d6097d7e846");
			Assert.assertEquals(accountInfo.getOwnerCount(), 3);
		} catch (RippleWsClientException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAccountInfoMe() {
		log.debug("testAccountInfoMe");
		String account = "rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj"; // Tip this addresss !!
		try {
			AccountInfo accountInfo = retrieveAccountinfo(account);
			Assert.assertEquals(accountInfo.getAccount(), account);
			Assert.assertEquals(accountInfo.getFlags(), 0);
			Assert.assertEquals(accountInfo.getEmailHash(), null);
			Assert.assertEquals(accountInfo.getUrlgravatar(), null);
			Assert.assertEquals(accountInfo.getTransferRate(), null);
			Assert.assertEquals(accountInfo.getLedgerEntryType(), "AccountRoot");
			Assert.assertEquals(accountInfo.getOwnerCount(), 2);
			log.debug("Balance: " + accountInfo.getBalance());
		} catch (RippleWsClientException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAccountBitstamp() {
		log.debug("testAccountBitstamp");
		String account = "rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B"; 
		try {
			AccountInfo accountInfo = retrieveAccountinfo(account);
			Assert.assertEquals(accountInfo.getAccount(), account);
			Assert.assertEquals(accountInfo.getEmailHash(), "5B33B93C7FFE384D53450FC666BB11FB");
			Assert.assertEquals(accountInfo.getUrlgravatar(), "https://www.gravatar.com/avatar/5b33b93c7ffe384d53450fc666bb11fb");
			Assert.assertEquals(accountInfo.getFlags(), 131072);
			Assert.assertEquals(accountInfo.getOwnerCount(), 1);
			Assert.assertEquals(accountInfo.getTransferRate(), "1002000000");
			Assert.assertEquals(accountInfo.getDomain(), "6269747374616D702E6E6574");
		} catch (RippleWsClientException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testAccountInvalidAddress() {
		log.debug("testAccountInvalidAddress");
		String account = "rKQ8ouPLwsnXZM19LR6KoNnPaaBinvalid";
		try {
			retrieveAccountinfo(account);
			fail("should have thrown exception");
		} catch (RippleWsClientException e) {

		}

	}
}
