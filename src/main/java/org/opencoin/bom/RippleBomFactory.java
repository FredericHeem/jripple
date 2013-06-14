package org.opencoin.bom;

import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.opencoin.client.RippleWsClientListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleBomFactory {
	
	interface BomCreator {
		public void create(RippleWsClientListener listener, String message);
	}
	
	private static final Logger log = LoggerFactory.getLogger(RippleBomFactory.class);
	HashMap<String, BomCreator> mapBomCreation = new HashMap<String, BomCreator>();
	
	private HashMap<String, BomCreator> getMapBomCreation() {
		return mapBomCreation;
	}

	public RippleBomFactory(){
		new AccountInfoFactory(this);
	}
	
	public void register(String name, BomCreator creator){
		log.debug("register: " + name);
		mapBomCreation.put(name, creator);
	}
	
	public void decode(String message, RippleWsClientListener listener){
		log.debug("decode: " + message);
		JSONObject jsonMessage = (JSONObject) JSONValue.parse(message);
		if(jsonMessage == null){
			log.error("cannot parse to json: " + message);
			return;
		}
		
		JSONObject result = (JSONObject)jsonMessage.get("result");
		log.debug("decode result: " + result);
		if(result == null){
		}
		Set<Object> set = result.keySet();
		if(set.isEmpty()){
			log.error("cannot parse to json: " + message);
		}
		
		Object responseName = set.iterator().next();
		log.debug("responseName: " + responseName);
		BomCreator creator = getMapBomCreation().get(responseName);
		if(creator != null){
			creator.create(listener, result.get(responseName).toString());
		} else {
			log.error("response is not supported: " + responseName);
		}
	}
}
