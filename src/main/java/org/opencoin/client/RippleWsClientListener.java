package org.opencoin.client;

import org.opencoin.bom.AccountInfo;

public abstract class RippleWsClientListener {
    public void onConnected(){}
    public void onDisconnected(){}
    public void onAccountInfo(AccountInfo accountInfo){}
}
