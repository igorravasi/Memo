package server.services.extensions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.engine.MultiPlayerGame;

public class MatchTracker {

	private Map<Integer, MultiPlayerGame> games = new HashMap<Integer, MultiPlayerGame>();
	private final static MatchTracker tracker = new MatchTracker();
	
	private MatchTracker(){
		
	}
	public void setGames(Map<Integer, MultiPlayerGame> games) {
		this.games = games;
	}
	
	public static MatchTracker instance(){
		return tracker;
	}


	public List<Integer> getGames(String user){
		Iterator<Integer> gameIdsIterator = games.keySet().iterator();
		List<Integer> userGameIds = new LinkedList<Integer>();
		
		while (gameIdsIterator.hasNext()) {
			Integer playId = (Integer) gameIdsIterator.next();
			if (games.get(playId).isAGamer(user)) {
				userGameIds.add(playId);
			}
		}
		
		return userGameIds;
	}
	
	
}
