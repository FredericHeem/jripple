package org.opencoin.client;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;
import org.opencoin.bom.AccountInfo;
import org.opencoin.client.command.AccountInfoCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleWsClientSyncTest {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClientSync.class);
	@Test
	public void testConnect() {
		try {
			RippleWsClientSync clientSync = new RippleWsClientSync();
			clientSync.connect();
		} catch(Exception exception){
			fail("error " + exception.getMessage());
		}
	}
	
	@Test
	public void testConnectTwice() {
		try {
			RippleWsClientSync clientSync = new RippleWsClientSync();
			clientSync.connect();
			// This should do nothing
			clientSync.connect();
		} catch(Exception exception){
			fail("error " + exception.getMessage());
		}
	}
	
	@Test
	public void testConnectDisconnect() {
		try {
			RippleWsClientSync clientSync = new RippleWsClientSync();
			clientSync.connect();
			clientSync.disconnect();
		} catch(Exception exception){
			fail("error " + exception.getMessage());
		}
	}
	
	@Test
	public void testConnectInvalidHost() {
		log.debug("testConnectInvalidHost");
		try {
			RippleWsClientSync clientSync = new RippleWsClientSync();
			clientSync.getConfig().setBaseUrl("notexist.ripple.com");
			clientSync.connect();
			fail("cannot be connected");
		} catch(RippleWsClientException rippleWsClientException){
			//We should be here
		} catch(Exception exception){
			fail("error " + exception.getMessage());
		}
	}
	
	@Test
	public void testConnectInvalidPort() {
		try {
			RippleWsClientSync clientSync = new RippleWsClientSync();
			clientSync.getConfig().setPort(12345);
			clientSync.connect();
			fail("cannot be connected");
		} catch(RippleWsClientException rippleWsClientException){
			//We should be here
		} catch(Exception exception){
			fail("error " + exception.getMessage());
		}
	}
	
	public AccountInfo retrieveAccountinfo(String account) throws RippleWsClientException {
			RippleWsClientSync clientSync = new RippleWsClientSync();
			return clientSync.retrieveAccountinfo(new AccountInfoCommand(account));
	}
	
	@Test
	public void testAccountInfoGrahamAmrstrong() {
		log.debug("testAccountInfoGrahamAmrstrong");
		String account = "rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh";
		try {
			AccountInfo accountInfo = retrieveAccountinfo(account);
			Assert.assertEquals(accountInfo.getAccount(), account);
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
