package org.opencoin.client;

import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.opencoin.bom.AccountInfo;
import org.opencoin.client.command.AccountInfoCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleWsClientSync  {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClientSync.class);
	private int timeout = 30;
	private RippleWsClient client;
	private boolean connected = false;
	private AccountInfo currentAccountInfo;
	
	public RippleWsClientSync() throws URISyntaxException
	{
		this.client = new RippleWsClient();
	}
	
	public void connect() throws RippleWsClientException
	{
		log.debug("connect");
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final RippleWsClientSync me = this;
		connected = false;
		try {
			this.client.setListener(new RippleWsClientListener() {
				@Override
				public void onConnected() {
					log.debug("onConnected");
					me.connected = true;
					countDownLatch.countDown();
				}

				@Override
				public void onError(String message){
					log.debug("onError: " + message);
					countDownLatch.countDown();
				}
			});
			
			client.connect();
			
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				throw new RippleWsClientException("connect timeout");
			};
			
			if(this.connected == false){
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
	
	public AccountInfo retrieveAccountinfo(AccountInfoCommand accountInfoCommand){
		log.debug("retrieveAccountinfo: " + accountInfoCommand.getAccount());
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final RippleWsClientSync me = this;
		try {
			this.client.setListener(new RippleWsClientListener() {
				@Override
				public void onAccountInfo(AccountInfo accountInfo) {
					log.debug("onAccountInfo " + accountInfo.getAccount());
					me.currentAccountInfo = accountInfo;
					countDownLatch.countDown();
				}
				@Override
				public void onError(String message){
					log.debug("onError: " + message);
					countDownLatch.countDown();
				}
			});
			
			client.sendCommand(accountInfoCommand);
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				log.warn("retrieveAccountinfo timeout");
			} else {
				log.debug("retrieveAccountinfo getting accountInfo");
				if(this.currentAccountInfo != null){
					return this.currentAccountInfo;	
				} else {
					log.error("retrieveAccountinfo cannot retrieve account");
				}
			}
		} 
		catch(Exception exception){
			log.error("retrieveAccountinfo error: " + exception.getMessage());
		}
		
		return null;
	}

	public void setConfig(RippleClientConfig config) {
		this.client.setConfig(config);
	}

	public RippleClientConfig getConfig() {
		return this.client.getConfig();
	}
}
