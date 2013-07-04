package org.opencoin.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.opencoin.bom.AccountInfo;
import org.opencoin.bom.AccountLines;
import org.opencoin.client.command.AccountInfoCommand;
import org.opencoin.client.command.AccountLinesCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleWsClientSync  {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClientSync.class);
	private int timeout = 30;
	private RippleWsClient client;
	private AccountInfo currentAccountInfo;
	private RippleWsClientException exception;
	protected AccountLines currentAccountLines;
	
	public RippleWsClientSync()
	{
		this.client = new RippleWsClient();
	}
	
	public void connect() throws RippleWsClientException
	{
		log.debug("connect");
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		setException(null);
		final RippleWsClientSync me = this;
		this.client.setListener(new RippleWsClientListener() {
			@Override
			public void onConnected() {
				log.debug("onConnected");
				countDownLatch.countDown();
			}

			@Override
			public void onError(RippleWsClientException exception){
				log.debug("onError: " + exception.getMessage());
				me.setException(exception);
				countDownLatch.countDown();
			}
		});

		client.connect();

		try {
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				throw new RippleWsClientException("timeout");
			}
		} catch (InterruptedException e) {
			throw new RippleWsClientException(e);
		}

		if(getException() != null){
			throw new RippleWsClientException(getException());
		}
		
		log.debug("connected !");
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
	
	public AccountInfo retrieveAccountInfo(AccountInfoCommand accountInfoCommand) throws RippleWsClientException {
		log.debug("retrieveAccountinfo: " + accountInfoCommand.getAccount());
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final RippleWsClientSync me = this;
		this.client.setListener(new RippleWsClientListener() {
			@Override
			public void onAccountInfo(AccountInfo accountInfo) {
				log.debug("onAccountInfo " + accountInfo.getAccount());
				me.currentAccountInfo = accountInfo;
				countDownLatch.countDown();
			}
			@Override
			public void onError(RippleWsClientException exception){
				log.debug("onError: " + exception.getMessage());
				me.setException(exception);
				countDownLatch.countDown();
			}
		});

		client.sendCommand(accountInfoCommand);

		try {
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				throw new RippleWsClientException("timeout");
			}
		} catch (InterruptedException e) {
			throw new RippleWsClientException(e);
		}

		log.debug("retrieveAccountinfo getting accountInfo");
		if(this.currentAccountInfo != null){
			return this.currentAccountInfo;	
		} else {
			log.error("retrieveAccountinfo cannot retrieve account");
			throw new RippleWsClientException(getException());
		}
	}

	public AccountLines retrieveAccountLines(AccountLinesCommand accountLinesCommand) throws RippleWsClientException {
		log.debug("retrieveAccountLines: " + accountLinesCommand.getAccount());
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final RippleWsClientSync me = this;
		this.client.setListener(new RippleWsClientListener() {
			@Override
			public void onAccountLines(AccountLines accountLines) {
				log.debug("onAccountInfo " + accountLines.getAccount());
				me.currentAccountLines = accountLines;
				countDownLatch.countDown();
			}
			@Override
			public void onError(RippleWsClientException exception){
				log.debug("onError: " + exception.getMessage());
				me.setException(exception);
				countDownLatch.countDown();
			}
		});

		client.sendCommand(accountLinesCommand);

		try {
			if(countDownLatch.await(timeout, TimeUnit.SECONDS) == false){
				throw new RippleWsClientException("timeout");
			}
		} catch (InterruptedException e) {
			throw new RippleWsClientException(e);
		}

		log.debug("retrieveAccountLines getting accountLines");
		if(this.currentAccountLines != null){
			return this.currentAccountLines;	
		} else {
			log.error("retrieveAccountLines cannot retrieve lines");
			throw new RippleWsClientException(getException());
		}
	}

	public void setConfig(RippleClientConfig config) {
		this.client.setConfig(config);
	}

	public RippleClientConfig getConfig() {
		return this.client.getConfig();
	}

	public void setException(RippleWsClientException exception) {
		this.exception = exception;
	}

	public RippleWsClientException getException() {
		return exception;
	}
}
