package server.services;

import java.io.IOException;
import java.net.Socket;

import server.basics.HttpRequest;
import server.basics.HttpStringMessage;
import server.services.extensions.SessionManager;

public class MultiPlayerService implements IService {

	private SessionManager sessionManager;
	private HttpStringMessage message = new HttpStringMessage();
	
	public MultiPlayerService(SessionManager sessionManager) {

		this.sessionManager = sessionManager;
	}
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException {
		
		
		if (!validRequest(request)) {
			message.sendMessage(clientSocket, "E:Relog");
		} else {
			message.sendMessage(clientSocket, "TUTTOK");
		}

		
	}
	
	private boolean validRequest ( HttpRequest request ){
		String user = request.getCookies().get("Utente");
		Long sessionId;
		
		try {
			sessionId = Long.parseLong(request.getCookies().get("Sessione"));
		} catch (NumberFormatException e) {
			sessionId = 0L;
		}
		
		if (user != null && sessionManager.checkSession(sessionId, user)) {
			return true;
		} else {
			return false;
		}
		
	}
	

}
