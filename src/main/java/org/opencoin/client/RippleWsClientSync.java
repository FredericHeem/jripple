package org.opencoin.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.opencoin.bom.AccountInfo;

public class RippleWsClientSync extends RippleWsClientListener {
	private RippleClientConfig config = new RippleClientConfig();
	private int timeout = 30;
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	private boolean error = false;
	
	public RippleWsClientSync()
	{
		
	}
	
	public void connect() throws RippleWsClientException
	{
		countDownLatch = new CountDownLatch(1);
		error = false;
		try {
			RippleWsClient client = new RippleWsClient(config, this);
			client.connect();
			
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				throw new RippleWsClientException("connect timeout");
			};
			
			if(this.error == true){
				throw new RippleWsClientException("error connecting");
			}
		} 
		catch(Exception exception){
			throw new RippleWsClientException(exception);
		}
	}

	@Override
	public void onConnected() {
		countDownLatch.countDown();
	}

	@Override
	public void onConnectionError() {
		error = true;
		countDownLatch.countDown();
	}

	@Override
	public void onDisconnected() {
	}

	@Override
	public void onAccountInfo(AccountInfo accountInfo) {
	}

	@Override
	public void onDecodingError(String errorMessage, String jsonMessage) {
	}

	public void setConfig(RippleClientConfig config) {
		this.config = config;
	}

	public RippleClientConfig getConfig() {
		return config;
	}
}
