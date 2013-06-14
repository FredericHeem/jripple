package org.opencoin.client;

import org.opencoin.bom.AccountInfo;

public abstract class RippleWsClientListener {
    void onConnected(){}
	void onAccountInfo(AccountInfo accountInfo){}
}
