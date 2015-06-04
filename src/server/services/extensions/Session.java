package server.services.extensions;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Session extends Observable{
	
	
//	private String user;
	private Long sessionId;
	private Timer expire = new Timer();
	
	public Session(Long sessionId, String user){
//		this.user = user;
		this.sessionId = sessionId;
		this.expire.schedule(new TimerTask() {
			
			@Override
			public void run() {
				setChanged();
				notifyObservers();	
			}
		}, 1000*60*60);
		
	}
	
	public Long getSessionId(){
		return sessionId;
	}
	
	public void stopTimer(){
		this.expire.cancel();
	}
//	public String getUser(){
//		return user;
//	}
}