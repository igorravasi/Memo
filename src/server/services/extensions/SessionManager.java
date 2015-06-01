package server.services.extensions;

public class SessionManager {

	private static SessionManager manager = new SessionManager();
	
	private SessionManager(){
		
	}
	
	public SessionManager getSessionManagerInstance(){
		return manager;
	}
	
	
	
	
}
