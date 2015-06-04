package server.services.extensions;

public class Session {
	
	
//	private String user;
	private Long sessionId;
	
	
	public Session(Long sessionId, String user){
//		this.user = user;
		this.sessionId = sessionId;
		
	}
	
	public Long getSessionId(){
		return sessionId;
	}
	
//	public String getUser(){
//		return user;
//	}
}