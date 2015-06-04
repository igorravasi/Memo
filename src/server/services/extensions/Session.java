package server.services.extensions;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import server.config.MemoServerConfigurator;

public class Session extends Observable{
	
	private static final String expiringTimeName = "session-expiring-time";
	private String user;
	private Long sessionId;
	private Timer expire = new Timer();
	
	public Session(Long sessionId, String user){
		this.user = user;
		this.sessionId = sessionId;
		
		long expiringTime = 0;
		
		try {
			expiringTime = Long.parseLong( MemoServerConfigurator.getInstance().getValue(expiringTimeName) );
		} catch (NumberFormatException e) {
		}
		
		this.expire.schedule(new TimerTask() {
			
			@Override
			public void run() {
				setChanged();
				notifyObservers();	
			}
		}, expiringTime);
		
	}
	
	public Long getSessionId(){
		return sessionId;
	}
	
	public void stopTimer(){
		this.expire.cancel();
	}
	public String getUser(){
		return user;
	}
}