package org.opencoin.client.bom;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;
import org.opencoin.bom.AccountLines;
import org.opencoin.bom.RippleJsonDecoder;
import org.opencoin.client.RippleWsClient;
import org.opencoin.client.RippleWsClientException;
import org.opencoin.client.RippleWsClientListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountLinesTest {
	private static final Logger log = LoggerFactory.getLogger(AccountInfoTest.class);
	@Test
	public void testDecode()
	{
		log.debug("testDecode");
		final String account = "rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj";
		String message = "{\"result\":{\"account\":\"rKQ8ouPLwsnXZM19LR6KoNnPaaBy8L5bVj\",\"lines\":[{\"account\":\"rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B\",\"balance\":\"5\",\"currency\":\"USD\",\"limit\":\"10\",\"limit_peer\":\"0\",\"quality_in\":0,\"quality_out\":0},{\"account\":\"rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B\",\"balance\":\"0\",\"currency\":\"BTC\",\"limit\":\"0.1\",\"limit_peer\":\"0\",\"quality_in\":0,\"quality_out\":0}]},\"status\":\"success\",\"type\":\"response\"}";
		
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
			@Override
			public void onAccountLines(AccountLines accountLines) {
				Assert.assertEquals(accountLines.getAccount(), account);
				Assert.assertEquals(accountLines.getLineList().size(), 2);
				for(AccountLines.Line line : accountLines.getLineList()){
					log.debug("Account     " + line.getAccount());
					log.debug("Balance     " + line.getBalance());
					log.debug("Currency    " + line.getCurrency());
					log.debug("Limit       " + line.getLimit());
					log.debug("Limit peer  " + line.getLimitPeer());
					log.debug("Quality in  " + line.getQualityIn());
					log.debug("Quality in  " + line.getQualityOut());
				}
			}
			@Override
			public void onError(RippleWsClientException exception){
				fail(exception.getMessage());
			}
		});
		jsonDecoder.decode(message, client);
	}
}







