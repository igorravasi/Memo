package server.services;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.engine.MultiPlayerGame;
import server.basics.HttpRequest;
import server.basics.HttpStringMessage;
import server.config.MemoServerConfigurator;
import server.services.extensions.SessionManager;

public class MultiPlayerService implements IService, Observer {

	private SessionManager sessionManager;
	private static final String noGameIdName = "no_game_id";
	private static final String noGameIdAvailableName = "no_game_id_available";
	MemoServerConfigurator configurator = MemoServerConfigurator.getInstance();
	
	private Map<Integer, MultiPlayerGame> games = new HashMap<Integer, MultiPlayerGame>();
	private HttpStringMessage message = new HttpStringMessage();
	private Integer lastInitiliazedGame = 0;
	
	
	
	public MultiPlayerService(SessionManager sessionManager){

		this.sessionManager = sessionManager;
	}
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException {
		
		String uri = request.getUri();
		String response = "";
		
		if (!validRequest(request)) {
			response = "E:Relog";
		} else {

			if (!uri.contains("multiplayer/")) {
				
				response = initializeGame();
				
			}
		}

		message.sendMessage(clientSocket, response);
		
	}
	
	private String initializeGame(){
		MultiPlayerGame lastGame = games.get(lastInitiliazedGame);
		if (lastGame != null && !lastGame.isStarted()) {
			return lastGame.getPlayId() +"";
		} else {
			return newGame();
		}
		
	}
	
	private String newGame() {
		
		Integer playId = getFreeIndex();
		
		if (playId == null) {
			return configurator.getValue(noGameIdAvailableName);
		} else {
			MultiPlayerGame aSingleGame = new MultiPlayerGame(playId);
			aSingleGame.addObserver(this);
			games.put(playId, aSingleGame);
			
			return playId+"";
		}
		

	}

	
	private Integer getFreeIndex(){

		int startNumber;
	
		if (games.size() <= Integer.MAX_VALUE) {
			startNumber = games.size();		
		} else {
			startNumber = 0;
		}
		
		for (int i = startNumber; i < Integer.MAX_VALUE; i++) {
			if (games.get(i) == null) {
				return i;
			}
		}
		
		return null;
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
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	

}
