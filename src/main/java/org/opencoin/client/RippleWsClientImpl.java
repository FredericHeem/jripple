package org.opencoin.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

//import se.cgbystrom.netty.http.websocket.WebSocketClient;
//import se.cgbystrom.netty.http.websocket.WebSocketClientFactory;


public class RippleWsClientImpl {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClient.class);
	private RippleWsClient client;
	private WebSocketClient webSocketClient;
	private URI uri;
	
	public RippleWsClientImpl(RippleWsClient client) throws URISyntaxException{
		this.client = client;
		RippleClientConfig config = client.getConfig();
		this.uri = new URI("ws://" + config.getBaseUrl() + ":" + config.getPort());
	    //this.webSocketClient = new WebSocketClientFactory().newClient(uri, client);
		this.webSocketClient = new WebSocketClient();
	}
	
	public void doConnect()
	{
		log.debug("doConnect " + uri.toASCIIString());
		try {
			this.webSocketClient.start();
			log.debug("connecting");
			this.webSocketClient.connect(this.client, this.uri);
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void doDisconnect()
	{
		log.debug("doDisconnect");
		//webSocketClient.disconnect();
	}
}
