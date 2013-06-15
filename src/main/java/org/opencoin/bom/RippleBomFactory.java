package org.opencoin.bom;

import java.util.HashMap;
import org.opencoin.client.RippleWsClientListener;

public class RippleBomFactory {
	
	interface BomCreator {
		public void create(RippleWsClientListener listener, String message);
	}
	
	//private static final Logger log = LoggerFactory.getLogger(RippleBomFactory.class);
	HashMap<String, BomCreator> mapBomCreation = new HashMap<String, BomCreator>();
	
	HashMap<String, BomCreator> getMapBomCreation() {
		return mapBomCreation;
	}

	public RippleBomFactory(){
		new AccountInfoFactory(this);
	}
	
	public void register(String name, BomCreator creator){
		//log.debug("register: " + name);
		mapBomCreation.put(name, creator);
	}
}
