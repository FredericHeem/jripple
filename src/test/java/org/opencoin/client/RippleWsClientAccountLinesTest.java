package org.opencoin.client;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.opencoin.bom.AccountLines;
import org.opencoin.client.command.AccountLinesCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleWsClientAccountLinesTest {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClientAccountLinesTest.class);
	String account;
	int timeout = 30; //seconds
	@Before
	public void setUp() throws Exception {
		account = "rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj";
	}

		
	@Test
	public void testAccountLines() {
		log.debug("testAccountLines");
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		try {
			RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
				@Override
				public void onAccountLines(AccountLines accountLines) {
					assertEquals(accountLines.getAccount(), account);
					//assertEquals(accountLines.getLineList().size(), 2);
					countDownLatch.countDown();
				}
			});

			AccountLinesCommand accountLinesCommand = new AccountLinesCommand(account);
			client.sendCommand(accountLinesCommand);
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				log.debug("timeout");
				fail("timeout");
			};
		} 
		catch(Exception exception){
			fail(exception.getMessage());
		}
	}
	
	public AccountLines retrieveAccountLines(String account) throws RippleWsClientException {
		RippleWsClientSync clientSync = new RippleWsClientSync();
		return clientSync.retrieveAccountLines(new AccountLinesCommand(account));
	}
	
	//@Test
	public void testAccountBitstamp() {
		log.debug("testAccountBitstamp");
		String account = "rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B"; 
		try {
			AccountLines accountLines = retrieveAccountLines(account);
			assertEquals(accountLines.getAccount(), account);
			log.debug("#lines: " + accountLines.getLineList().size());
		} catch (RippleWsClientException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testAccountSnapswap() {
		log.debug("testAccountSnapswap");
		String account = "rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q"; 
		try {
			AccountLines accountLines = retrieveAccountLines(account);
			assertEquals(accountLines.getAccount(), account);
			log.debug("#lines: " + accountLines.getLineList().size());
		} catch (RippleWsClientException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testAccountRippleCN() {
		log.debug("testAccountRippleCN");
		String account = "rnuF96W4SZoCJmbHYBFoJZpR8eCaxNvekK"; 
		try {
			AccountLines accountLines = retrieveAccountLines(account);
			assertEquals(accountLines.getAccount(), account);
			log.debug("#lines: " + accountLines.getLineList().size());
		} catch (RippleWsClientException e) {
			fail(e.getMessage());
		}
	}
}
