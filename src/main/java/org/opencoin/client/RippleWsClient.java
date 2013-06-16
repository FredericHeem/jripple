package org.opencoin.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketFrame;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.extensions.Frame;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.opencoin.bom.AccountInfo;
import org.opencoin.bom.RippleJsonDecoder;
import org.opencoin.client.command.RippleCommand;
import org.opencoin.client.RippleWsClientContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebSocket(maxMessageSize = 64 * 1024)
public class RippleWsClient {
	private static final Logger log = LoggerFactory.getLogger(RippleWsClient.class);
	private RippleClientConfig config = new RippleClientConfig();
	private RippleWsClientListener listener;
	private RippleWsClientContext context;
	private RippleJsonDecoder jsonDecoder = new RippleJsonDecoder();
	private WebSocketClient webSocketClient;
	private Session session;
	private URI uri;
	private Queue<RippleCommand> commandQueue = new LinkedList<RippleCommand>();

	public RippleWsClient() {
		this.webSocketClient = new WebSocketClient();
		this.listener = new RippleWsClientListener();
	}

	public RippleWsClient(RippleWsClientListener listener) {
		this();
		this.listener = listener;
	}
	
	public void connect(){
		log.debug("connect");
		getContext().evConnect();
	}
	
	public void disconnect() {
		log.debug("disconnect");
		getContext().evDisconnect();
	}
	
	public void sendCommand(RippleCommand command){
		commandQueue.add(command);
		log.debug("sendCommand #command " + commandQueue.size());
		getContext().evCommand();
	}
	
	@OnWebSocketConnect
	public void onWebSocketConnect(Session session) {
		log.debug("onWebSocketConnect");
		this.session = session;
		this.context.evConnected();
	}

	@OnWebSocketClose
	public void onWebSocketClose(int statusCode, String reason) {
		log.debug("onWebSocketClose: " + statusCode);
		this.context.evDisconnect();
		this.session = null; 
	}

	@OnWebSocketError
	public void onWebSocketError(Session session, Throwable throwable) {
		log.debug("onWebSocketError: " + throwable.getMessage());
		this.context.evError();
		this.session = null;
	}

	@OnWebSocketMessage
	public void onWebSocketMessage(Session session, String message) {
		log.info("onWebSocketMessage: \n" + message);
		jsonDecoder.decode(message, this);
	}
	
	@OnWebSocketFrame
	public void onWebSocketFrame(Frame frame) {
		log.info("onWebSocketFrame: " + frame.toString());
	}

	public void doConnect()
	{
		try {
			log.debug("doConnect " + getUri().toASCIIString());
			this.webSocketClient.start();
			this.webSocketClient.connect(this, getUri());
		} catch (URISyntaxException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	void onConnected(){
		listener.onConnected();
	}
	
	void onDisconnected(){
		listener.onDisconnected();
	}
	
	void onConnectionError(){
		listener.onConnectionError();
		listener.onError("connection error");
	}
	
    public void onDecodingError(String errorMessage, String jsonMessage){
    	listener.onDecodingError(errorMessage, jsonMessage);
    	listener.onError("decoding error");
    }
    
	public void onAccountInfo(AccountInfo accountInfo) {
		listener.onAccountInfo(accountInfo);
	}
	
	public void setConfig(RippleClientConfig config) {
		this.config = config;
	}

	public RippleClientConfig getConfig() {
		return config;
	}
	
	public URI getUri() throws URISyntaxException {
		this.uri = new URI("ws://" + config.getBaseUrl() + ":" + config.getPort());
		return this.uri;
	}
	
	public void setListener(RippleWsClientListener listener) {
		this.listener = listener;
	}
	
	public RippleWsClientContext getContext() {
		if(this.context == null){
			this.context = new RippleWsClientContext(this);
			this.context.setObserver(new StateMachineObserver(log));
		}
		return context;
	}
	
	void doDisconnect()
	{
		log.debug("doDisconnect");
		if(this.session != null){
			try {
				this.session.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	void doSendCommand()
	{
		log.debug("doSendCommand #command: " + commandQueue.size());
		RippleCommand command = commandQueue.peek();
		if(command != null){
			String message = command.toJsonString();
			log.debug("doSendCommand " + message);
			try {
				this.session.getRemote().sendString(message);
			} catch (IOException e) {
				log.error("doSendCommand error " + e.getMessage());
				this.context.evError();
			}
		} else {
			log.debug("no command to send");
		}
	}
}
