package com.patientmonitoring;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.*;
public class Configurator extends ServerEndpointConfig.Configurator{
	public void modifyHandshake(ServerEndpointConfig serverEndpointConfig, HandshakeRequest handshakeRequest, HandshakeResponse handshakeResponse) {
		serverEndpointConfig.getUserProperties().put("user", (String) ((HttpSession) handshakeRequest.getHttpSession()).getAttribute("user"));
	}
}