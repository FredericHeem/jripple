package org.opencoin.client;

import static org.junit.Assert.*;

import org.junit.Test;
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
}
