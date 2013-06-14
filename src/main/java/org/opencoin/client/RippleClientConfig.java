package org.opencoin.client;

public class RippleClientConfig {
	public String baseUrl = "s1.ripple.com";
	public int port = 443;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
