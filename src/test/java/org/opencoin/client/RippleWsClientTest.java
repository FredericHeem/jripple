package org.opencoin.client;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencoin.bom.AccountInfo;
import org.opencoin.client.command.AccountInfoComnand;

public class RippleWsClientTest {

	String account;
	@Before
	public void setUp() throws Exception {
		account = "rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConnect() {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		try {
			RippleClientConfig config = new RippleClientConfig();
			RippleWsClient client = new RippleWsClient(config, new RippleWsClientListener() {
				@Override
				public void onConnected() {
					countDownLatch.countDown();
				}
			});

			client.connect();
			if(countDownLatch.await(60, TimeUnit.SECONDS) == false){
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}

	@Test
	public void testAccountInfo() {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		try {
			RippleClientConfig config = new RippleClientConfig();
			RippleWsClient client = new RippleWsClient(config, new RippleWsClientListener() {
				@Override
				public void onAccountInfo(AccountInfo accountInfo) {
					assertEquals(accountInfo.getAccount(), account);
					countDownLatch.countDown();
				}
			});

			AccountInfoComnand accountInfoCommand = new AccountInfoComnand(account);
			client.sendCommand(accountInfoCommand);
			if(countDownLatch.await(10, TimeUnit.SECONDS) == false){
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}
}
