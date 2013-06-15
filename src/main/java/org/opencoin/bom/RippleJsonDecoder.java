package org.opencoin.bom;

import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.opencoin.bom.RippleBomFactory.BomCreator;
import org.opencoin.client.RippleWsClientListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RippleJsonDecoder {
	private static final Logger log = LoggerFactory.getLogger(RippleJsonDecoder.class);
	private RippleBomFactory bomFactory = new RippleBomFactory();
	
	public void decode(
			String jsonMessage, 
			RippleWsClientListener listener)
	{
		try {
			log.debug("decode: " + jsonMessage);
			JSONObject json = (JSONObject) JSONValue.parse(jsonMessage);
			if(json == null){
				reportError(listener, "cannot parse to json", jsonMessage);
				return;
			}

			JSONObject result = (JSONObject)json.get("result");
			log.debug("decode result: " + result);
			if(result == null){
				reportError(listener, "cannot decode result", jsonMessage);
				return;
			}

			Set<Object> set = result.keySet();

			boolean found = false;
			for(Object responseKey : set){
				BomCreator creator = bomFactory.getMapBomCreation().get(responseKey);
				if(creator != null){
					creator.create(listener, result.get(responseKey).toString());
					found = true;
					break;
				}	
			}

			if(found == false){
				reportError(listener, "not a supported response", jsonMessage);
			}
		} catch(Exception exception){
			reportError(listener, "unknow error", exception.getMessage());
		}
	}
	
	private void reportError(
			RippleWsClientListener listener, 
			String errorMessage,
			String jsonMessage)
	{
		log.error(errorMessage + ": " + jsonMessage);
		listener.onDecodingError(errorMessage, jsonMessage);
	}
}
