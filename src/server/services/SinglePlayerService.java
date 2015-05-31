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
 * Inizializzo il gioco per il Single Player
 *
 */
public class SinglePlayerService implements IService, Observer{

	private static final String noGameIdName = "no_game_id";
	private static final String noGameIdAvailableName = "no_game_id_available";
	MemoServerConfigurator configurator = MemoServerConfigurator.getInstance();
	
	private Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
	private HttpStringMessage message = new HttpStringMessage();
	
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		
		String uri = request.getUri();
		String response;
		
		if (!uri.contains("singleplayer/")) {	
			response = initializeGame(clientSocket);
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
	
	
	
	private String initializeGame(Socket clientSocket) throws IOException{
		
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
	
	
	//TODO: Questo metodo porta ad una responsabilita' multipla?! Meglio estendere la map?
	private Integer getFreeIndex(){

	
		if (singleGames.size() <= Integer.MAX_VALUE) {
			return singleGames.size();		
			
		} else {
			
			for (int i = 0; i < singleGames.size(); i++) {
				if (singleGames.get(i) == null) {
					return i;
				}
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
