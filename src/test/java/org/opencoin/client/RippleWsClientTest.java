package org.opencoin.client;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.opencoin.bom.AccountInfo;
import org.opencoin.client.command.AccountInfoCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleWsClientTest {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClientTest.class);
	String account;
	int timeout = 30; //seconds
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
			RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
				@Override
				public void onConnected() {
					countDownLatch.countDown();
				}
			});

			client.connect();
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}

	@Test
	public void testDisconnect() {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		try {
			RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
				@Override
				public void onDisconnected() {
					countDownLatch.countDown();
				}
			});

			client.disconnect();
			
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}
	
	@Test
	public void testConnectingDisconnect() {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		try {
			RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
				@Override
				public void onDisconnected() {
					countDownLatch.countDown();
				}
			});

			client.connect();
			client.disconnect();
			
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}
	@Test
	public void testConnectInvalidHost() {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		try {
			RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
				@Override
				public void onConnectionError()
				{
					log.debug("onConnectionError");
					countDownLatch.countDown();
				}
			});
			client.getConfig().setBaseUrl("s12345.ripple.com");
			client.connect();
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}

	@Test
	public void testConnectInvalidPort() {
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		
		try {
			RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
				@Override
				public void onConnectionError()
				{
					log.debug("onConnectionError");
					countDownLatch.countDown();
				}
			});
			client.getConfig().setPort(12345);
			client.connect();
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
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
}
