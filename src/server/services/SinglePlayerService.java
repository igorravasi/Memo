package server.services;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.engine.SinglePlayerGame;
import server.basics.HttpRequest;
import server.basics.HttpStringMessage;
import server.config.MemoServerConfigurator;

/**
 * IService che funge da:
 * Gestore dei giochi Single Player: li inizializza ed indicizza, per poi inoltrargli le giocate dell'utente ed ottenere le risposte
 * 
 */
public class SinglePlayerService implements IService, Observer{

	private static final String noGameIdName = "no_game_id";
	private static final String noGameIdAvailableName = "no_game_id_available";
	private MemoServerConfigurator configurator = MemoServerConfigurator.getInstance();
	
	private Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
	private HttpStringMessage message = new HttpStringMessage();
	
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		
		String uri = request.getUri();
		String response;
		
		if (!uri.contains("singleplayer/")) {	
			response = initializeGame();
		}else {
			response = play(clientSocket, uri, request.getContent());
		}

		message.sendMessage(clientSocket, response);
		
	}

	
	
	private String play(Socket clientSocket, String uri, String content) throws IOException{
		
		Integer playId;
		

		try {
			playId = Integer.parseInt(uri.substring("/singleplayer".length()+1));
		} catch (NumberFormatException e) {
			playId = null;
		}

		
		SinglePlayerGame game = singleGames.get(playId);
		String response = configurator.getValue(noGameIdName);
		
		if (game != null) {
			response = game.readRequest(content);
		}
		
		return response;
		
		
	}
	
	
	
	private String initializeGame() throws IOException{
		
		Integer playId = getFreeIndex();
		
		if (playId == null) {
			return configurator.getValue(noGameIdAvailableName);
		} else {
			SinglePlayerGame aSingleGame = new SinglePlayerGame(playId);
			aSingleGame.addObserver(this);
			
			singleGames.put(playId, aSingleGame);
			
			return playId+"";
		}
		

	}
	
	
	
	private Integer getFreeIndex(){

		int startNumber;
	
		if (singleGames.size() <= Integer.MAX_VALUE) {
			startNumber = singleGames.size();		
		} else {
			startNumber = 0;
		}
		
		for (int i = startNumber; i < Integer.MAX_VALUE; i++) {
			if (singleGames.get(i) == null) {
				return i;
			}
		}
		
		return null;
	}



	@Override
	public void update(Observable obs, Object arg1) {
		
		Integer playId = ((SinglePlayerGame) obs).getPlayId();
		singleGames.remove(playId);
		
	}
	
	
}
