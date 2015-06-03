package server.services.extensions;

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

	
	Random randomizer = new Random(new Random().nextLong());
	
	
	public Long newSession(String user){
		
		Session session = new Session(randomizer.nextLong(), user);
		return session.getSessionId();
	}
	



	
}
