package server.services;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.IService;
import server.basics.HttpRequest;
import server.basics.HttpStringMessage;
import engine.SinglePlayerGame;

public class SinglePlayerService implements IService {

	private Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
	private HttpStringMessage message = new HttpStringMessage();
	
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		
		String uri = request.getUri();
		
		if (!uri.contains("singleplayer/")) {	
			initializeGame(clientSocket);
		}else {
			play(clientSocket, uri);
		}

	}

	
	
	private void play(Socket clientSocket, String uri) throws IOException{
		
		Integer playId;
		
		int endOfPlayId = uri.indexOf('?');
		if (endOfPlayId>0) {
								
			playId = Integer.parseInt(uri.substring("/singleplayer".length()+1,endOfPlayId));
		}else {
			playId = Integer.parseInt(uri.substring("/singleplayer".length()+1));
		}
		
		SinglePlayerGame game = singleGames.get(playId);
		String response = "E: No game Id";
		if (game != null) {
			response = game.readRequest(uri);
		}
		
		message.sendMessage(clientSocket, response);
		
		
	}
	
	
	private void initializeGame(Socket clientSocket) throws IOException{
		
		//sostituire singleGames.size() con un numero calcolato appositamente, libero.
		Integer playId = singleGames.size();
		singleGames.put(playId, new SinglePlayerGame());
		message.sendMessage(clientSocket, playId+"");
	}
	
	
}
