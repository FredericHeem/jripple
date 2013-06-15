package org.opencoin.client;

import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.opencoin.bom.AccountInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleWsClientSync extends RippleWsClientListener {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClientSync.class);
	private int timeout = 30;
	private CountDownLatch countDownLatch = new CountDownLatch(1);
	private boolean error = false;
	private RippleWsClient client;
	
	public RippleWsClientSync() throws URISyntaxException
	{
		this.client = new RippleWsClient(this);
	}
	
	public void connect() throws RippleWsClientException
	{
		log.debug("connect");
		countDownLatch = new CountDownLatch(1);
		error = false;
		try {
			this.client.connect();
			
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				throw new RippleWsClientException("connect timeout");
			};
			
			if(this.error == true){
				throw new RippleWsClientException("error connecting");
			}
			
			log.debug("connected !");
		} 
		catch(Exception exception){
			throw new RippleWsClientException(exception);
		}
	}

	public void disconnect() throws RippleWsClientException
	{
		log.debug("disconnect");
		try {
			client.disconnect();
		} 
		catch(Exception exception){
			throw new RippleWsClientException(exception);
		}
	}
	
	@Override
	public void onConnected() {
		log.debug("onConnected");
		countDownLatch.countDown();
	}

	@Override
	public void onConnectionError() {
		log.debug("onConnectionError");
		error = true;
		countDownLatch.countDown();
	}

	@Override
	public void onDisconnected() {
		log.debug("onDisconnected");
	}

	@Override
	public void onAccountInfo(AccountInfo accountInfo) {
	}

	@Override
	public void onDecodingError(String errorMessage, String jsonMessage) {
		log.debug("onDecodingError");
	}

	public void setConfig(RippleClientConfig config) {
		this.client.setConfig(config);
	}

	public RippleClientConfig getConfig() {
		return this.client.getConfig();
	}
}
