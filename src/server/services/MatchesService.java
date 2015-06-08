package server.services;

import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import server.basics.HttpRequest;
import server.basics.HttpStringMessage;
import server.config.MemoServerConfigurator;
import server.services.extensions.MatchTracker;
import server.services.extensions.SessionManager;

public class MatchesService implements IService {

	private SessionManager sessionManager;
	
	
	
	private HttpStringMessage message = new HttpStringMessage();
	
	
	
	
	public MatchesService(SessionManager sessionManager){

		this.sessionManager = sessionManager;
		
	}
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException {
		

		String response = "";
		
		if (!validRequest(request)) {
			response = "E:Relog";	
		} else {
			List<Integer> ids = MatchTracker.instance().getGames(request.getCookies().get("Utente"));
			for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext();) {
				Integer id = (Integer) iterator.next();
				response += id + "\n";
			}
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
