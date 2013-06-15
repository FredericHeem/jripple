package org.opencoin.client.bom;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opencoin.bom.RippleJsonDecoder;
import org.opencoin.client.RippleWsClientListener;

public class RippleJsonDecoderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInvalidJson()
	{
		final String jsonContentIn = "";
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		jsonDecoder.decode(jsonContentIn, new RippleWsClientListener() {
			@Override
			public void onDecodingError(String errorMessage, String jsonContent) {
				Assert.assertEquals(jsonContentIn, jsonContent);
			}
		});
	}
	@Test
	public void testMissingResult()
	{
		final String jsonContentIn = "{}";
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		jsonDecoder.decode(jsonContentIn, new RippleWsClientListener() {
			@Override
			public void onDecodingError(String errorMessage, String jsonContent) {
				Assert.assertEquals(jsonContentIn, jsonContent);
			}
		});
	}
	
	@Test
	public void testMissingCommandResponse()
	{
		final String jsonContentIn = "{\"id\":0,\"result\":{}}";
		RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
		jsonDecoder.decode(jsonContentIn, new RippleWsClientListener() {
			@Override
			public void onDecodingError(String errorMessage, String jsonContent) {
				Assert.assertEquals(jsonContentIn, jsonContent);
			}
		});
	}
}
