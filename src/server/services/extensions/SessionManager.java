package server.services.extensions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Classe che gestisce le sessioni aperte e valide e permette a chi la utilizza
 * di verificare se la coppia utente-sessione è corretta.
 * 
 */

public class SessionManager {

	private class Session {
		
	
//		private String user;
		private Long sessionId;
		
		
		public Session(Long sessionId, String user){
//			this.user = user;
			this.sessionId = sessionId;
			
		}
		
		public Long getSessionId(){
			return sessionId;
		}
		
//		public String getUser(){
//			return user;
//		}
	}

	
	private Random randomizer = new Random(new Random().nextLong());
	private Map<Long, Session> sessions = new HashMap<Long, Session>();
	
	public Long newSession(String user){
		
		Session session = new Session(randomizer.nextLong(), user);
		Long id = session.getSessionId();
		sessions.put(id, session);
		return id;
	}
	
	



	
}
