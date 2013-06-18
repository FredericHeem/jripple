package org.opencoin.client.bom;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.opencoin.bom.AccountInfo;
import org.opencoin.bom.RippleJsonDecoder;
import org.opencoin.client.RippleWsClient;
import org.opencoin.client.RippleWsClientException;
import org.opencoin.client.RippleWsClientListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountInfoTest {
	private static final Logger log = LoggerFactory.getLogger(AccountInfoTest.class);
	@Test
	public void testFactory()
	{
		log.debug("AccountInfoTest");
		final String account = "rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh";
		String message = "{\"id\":0,\"result\":{\"account_data\":{\"Account\":\"" + account + "\"}}}";
		
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
			@Override
			public void onAccountInfo(AccountInfo accountInfo) {
				Assert.assertEquals(accountInfo.getAccount(), account);
			}
			@Override
			public void onError(RippleWsClientException exception){
				fail(exception.getMessage());
			}
		});
		jsonDecoder.decode(message, client);
	}
}
