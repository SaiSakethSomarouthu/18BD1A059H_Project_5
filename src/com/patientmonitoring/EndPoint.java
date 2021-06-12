package com.patientmonitoring;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.json.*;
@ServerEndpoint(value="/EndPoint", configurator=Configurator.class)
public class EndPoint {
	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	@OnOpen
	public void handleOpen(EndpointConfig endPointConfig, Session session) {
		session.getUserProperties().put("user", endPointConfig.getUserProperties().get("user"));
		users.add(session);
	}
	@OnMessage
	public void handleMessage(String message, Session session) {
		String user = (String) session.getUserProperties().get("user");
		if(!user.equals("doctor")) {
			users.stream().forEach(i -> {
				try {
					String[] messages = message.split(",");
					if(Integer.parseInt(messages[0]) < 90) {
						if(i.getUserProperties().get("user").equals("doctor")) {
							i.getBasicRemote().sendText(buildJSON(user, message));
						}
					}
				}
				catch (Exception e) {
					e.getStackTrace();
				}
			});
		}
		else if(user.equals("doctor")) {
			String[] messages = message.split(",");
			String patient = messages[0];
			String subject = messages[1];
			users.stream().forEach(i -> {
				try {
					if(subject.equals("ambulance")) {
						if(i.getUserProperties().get("user").equals(patient)) {
							i.getBasicRemote().sendText(buildJSON("doctor", "has summoned an ambulance"));
						}
						else if(i.getUserProperties().get("user").equals("ambulance")) {
							i.getBasicRemote().sendText(buildJSON(patient, messages[2] + ",requires an ambulance"));
						}
					}
					else if(subject.equals("medication")) {
						if(i.getUserProperties().get("user").equals(patient)) {
							i.getBasicRemote().sendText(buildJSON("doctor", messages[2] + "," + messages[3]));
						}
					}
				} catch (Exception e) {
					e.getStackTrace();
				}
			});
		}
	}
	@OnClose
	public void handleClose(Session session) {
		users.remove(session);
	}
	@OnError
	public void handleError(Throwable throwable) {
		
	}
	private String buildJSON(String user, String message) {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", user + "," + message).build();
		StringWriter stringWriter = new StringWriter();
		try(JsonWriter jsonWriter = Json.createWriter(stringWriter)){
			jsonWriter.write(jsonObject);
		}
		return stringWriter.toString();
	}
}