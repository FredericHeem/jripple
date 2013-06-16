package org.opencoin.client.bom;

import org.junit.Assert;
import org.junit.Test;
import org.opencoin.bom.RippleJsonDecoder;
import org.opencoin.client.RippleWsClient;
import org.opencoin.client.RippleWsClientListener;

public class RippleJsonDecoderTest {

	@Test
	public void testInvalidJson()
	{
		final String jsonContentIn = "";
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
			@Override
			public void onDecodingError(String errorMessage, String jsonContent) {
				Assert.assertEquals(jsonContentIn, jsonContent);
			}
		});
		jsonDecoder.decode(jsonContentIn, client);
	}
	
	@Test
	public void testMissingResult()
	{
		final String jsonContentIn = "{}";
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		
		RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
			@Override
			public void onDecodingError(String errorMessage, String jsonContent) {
				Assert.assertEquals(jsonContentIn, jsonContent);
			}
		});
		
		jsonDecoder.decode(jsonContentIn, client);
	}
	
	@Test
	public void testMissingCommandResponse()
	{
		final String jsonContentIn = "{\"id\":0,\"result\":{}}";
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		RippleWsClient client = new RippleWsClient(new RippleWsClientListener() {
			@Override
			public void onDecodingError(String errorMessage, String jsonContent) {
				Assert.assertEquals(jsonContentIn, jsonContent);
			}
		});
		
		jsonDecoder.decode(jsonContentIn, client);
	}
}
