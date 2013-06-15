package org.opencoin.client.bom;

import org.junit.Assert;
import org.junit.Test;
import org.opencoin.bom.AccountInfo;
import org.opencoin.bom.RippleJsonDecoder;
import org.opencoin.client.RippleWsClientListener;

public class AccountInfoTest {

	@Test
	public void testFactory()
	{
		final String account = "rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh";
		String message = "{\"id\":0,\"result\":{\"account_data\":{\"Account\":\"" + account + "\"}}}";
		
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		jsonDecoder.decode(message, new RippleWsClientListener() {
			@Override
			public void onAccountInfo(AccountInfo accountInfo) {
				Assert.assertEquals(accountInfo.getAccount(), account);
			}
		});
	}
}
