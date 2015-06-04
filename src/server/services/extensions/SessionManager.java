package server.services.extensions;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Classe che gestisce le sessioni aperte e valide e permette a chi la utilizza
 * di verificare se la coppia utente-sessione è corretta.
 * 
 */

public class SessionManager implements Observer{


	
	private Random randomizer = new Random(new Random().nextLong());
	private Map<Long, Session> sessions = new HashMap<Long, Session>();
	
	public Long newSession(String user){
		
		Session session = new Session(randomizer.nextLong(), user);
		Long id = session.getSessionId();
		session.addObserver(this);
		sessions.put(id, session);
		return id;
		
	}

	public void deleteSession(Long sessionId){
		if (sessions.containsKey(sessionId)) {
			sessions.get(sessionId).stopTimer();
			sessions.remove(sessionId);
		}
	}
	
	
	public boolean checkSession(Long sessionId, String user){
		
		boolean result = false;
		if (sessions.containsKey(sessionId)) {
			Session session = sessions.get(sessionId);
			if (session.getUser().equals(user)) {
				result = true;
			}
		}
		
		return result;
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		Session session = (Session) o;
		deleteSession(session.getSessionId());
		
	}
	
	



	
}
