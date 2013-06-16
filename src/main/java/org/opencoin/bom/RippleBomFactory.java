package org.opencoin.bom;

import java.util.HashMap;

import org.opencoin.client.RippleWsClient;

public class RippleBomFactory {
	
	interface BomCreator {
		public void create(RippleWsClient client, String message);
	}
	
	HashMap<String, BomCreator> mapBomCreation = new HashMap<String, BomCreator>();
	
	HashMap<String, BomCreator> getMapBomCreation() {
		return mapBomCreation;
	}

	public RippleBomFactory(){
		new AccountInfoFactory(this);
	}
	
	public void register(String name, BomCreator creator){
		mapBomCreation.put(name, creator);
	}
}
