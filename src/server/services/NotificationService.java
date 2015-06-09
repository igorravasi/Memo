package server.services;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

import server.basics.HttpRequest;
import server.basics.HttpStringMessage;
import server.services.extensions.Notificator;
import server.services.extensions.SessionManager;

public class NotificationService implements IService{

	private SessionManager sessionManager;
	
	private HttpStringMessage message = new HttpStringMessage();
	
	
	
	
	public NotificationService(SessionManager sessionManager){

		this.sessionManager = sessionManager;
		
	}
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException {
		

		String response = "";
		
		if (!validRequest(request)) {
			response = "E:Relog";
		} else {
			StringBuilder sb = new StringBuilder();
			Iterator<String> it = Notificator.it().getNotifies(request.getCookies().get("Utente"));
			while (it.hasNext()) {
				String n = (String) it.next();
				sb.append(n);
			}
			response = sb.toString();
		}

		message.sendMessage(clientSocket, response);
		
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
