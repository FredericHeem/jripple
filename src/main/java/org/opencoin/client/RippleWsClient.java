package org.opencoin.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketFrame;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.extensions.Frame;
//import org.jboss.netty.handler.codec.http.websocket.WebSocketFrame;
import org.opencoin.client.command.RippleCommand;
import org.opencoin.client.statemachine.RippleWsClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import se.cgbystrom.netty.http.websocket.WebSocketCallback;
//import se.cgbystrom.netty.http.websocket.WebSocketClient;

@WebSocket(maxMessageSize = 64 * 1024)
public class RippleWsClient {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClient.class);
	private RippleClientConfig config;
	private RippleWsClientListener listener;
	
	private RippleWsClientContext context;
	private RippleWsClientImpl impl;
	public RippleWsClient(RippleClientConfig config, RippleWsClientListener listener) throws URISyntaxException{
		this.setConfig(config);
		this.listener = listener;
		this.impl = new RippleWsClientImpl(this);
		this.context = new RippleWsClientContext(this.impl);
	}
	
	public void connect(){
		log.debug("connect");
		context.evConnect();
	}
	
	public void sendCommand(RippleCommand command){
		
	}
	
	@OnWebSocketConnect
	public void onWebSocketConnect(Session session) {
		log.debug("onWebSocketConnect");
		this.context.evConnected();
		//String command = "{\"command\":\"subscribe\",\"id\":0,\"streams\":[\"ledger\"]}";
		String command = "{\"command\":\"account_info\",\"id\":0,\"account\":\"rHb9CJAWyB4rj91VRWn96DkukG4bwdtyTh\"}";
		session.getRemote().sendStringByFuture(command);
	}

	@OnWebSocketClose
	public void onWebSocketClose(int statusCode, String reason) {
		log.debug("onWebSocketClose: " + statusCode);
		this.context.evDisconnect();
	}

	@OnWebSocketError
	public void onWebSocketError(Session session, Throwable throwable) {
		log.debug("onWebSocketError: " + throwable.getMessage());
		this.context.evError();
	}

	@OnWebSocketMessage
	public void onWebSocketMessage(Session session, String text) {
		log.info("onWebSocketMessage: \n" + text);
	}
	
	@OnWebSocketFrame
	public void onWebSocketFrame(Frame frame) {
		log.info("onWebSocketFrame: " + frame.toString());
		log.info("onWebSocketFrame: " + frame.getPayload().toString());
	}

	public void setConfig(RippleClientConfig config) {
		this.config = config;
	}

	public RippleClientConfig getConfig() {
		return config;
	}
}
