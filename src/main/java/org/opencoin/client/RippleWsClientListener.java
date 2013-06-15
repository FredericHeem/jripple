package org.opencoin.client;

import org.opencoin.bom.AccountInfo;

public abstract class RippleWsClientListener {
    public void onConnected(){}
    public void onConnectionError(){}
    public void onDisconnected(){}
    public void onAccountInfo(AccountInfo accountInfo){}
    
    public void onDecodingError(String errorMessage, String jsonMessage){}
}
