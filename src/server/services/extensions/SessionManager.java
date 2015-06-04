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

	
	
	@Override
	public void update(Observable o, Object arg) {
		
		Session session = (Session) o;
		session.stopTimer();
		Long id  = session.getSessionId();
		if (sessions.containsKey(id)) {
			sessions.remove(id);
		}
		
	}
	
	



	
}
