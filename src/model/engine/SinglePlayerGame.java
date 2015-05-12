package model.engine;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class SinglePlayerGame extends Observable{

	private static final int LOOSER = -1;
	private static final int ALIVE_TIMER = 1000*60*2; //DUE MINUTI
	
	private MemoSequence sequence = new MemoSequence();
	private Integer round = 0;
//	private boolean status = true;
	private Timer isAliveTimer = new Timer();
	private Integer playId;
	
	public Integer getPlayId() {
		return playId;
	}

	public SinglePlayerGame(Integer playId) {
		super();
		this.playId = playId;
		keepAlive();
	}

	private void keepAlive(){
		isAliveTimer.cancel();
		isAliveTimer = new Timer();
		
		isAliveTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				changed();
			}
		}, ALIVE_TIMER); //Dopo due minuti di inattivit� notifica l'observer
	}
	private int playerMoved(String playerSequence){
		keepAlive();
		playerSequence = playerSequence.substring(playerSequence.indexOf("?S=")+"?S=".length());
		playerSequence = playerSequence.replace("+", " ");
		playerSequence = playerSequence.trim();
				
		
		String actualSequence = getEscapedSequence();
		
		
		if (actualSequence.equalsIgnoreCase(playerSequence)) {
			round++;
			sequence.secondRound();
			return round;
		}else {
			
			return LOOSER;
		}
		
		
	}
	
	public String readRequest(String request){
		
		if (round == 0) {
			keepAlive();
			round++;
			return "S:"+ getSequence();
		} else {
			
			if (request.indexOf("?S=") >= 0) {
				
				return getTheResult(playerMoved(request));
			} else {
				return "E: Malformed request, Reload the page";
			}	
		}
		
	}
	
	private String getTheResult(int roundResult){
		
		if (roundResult == LOOSER) {
			changed();
			return "L: "+ round;
		} else {
			return "S:"+ getSequence();
		}
			
	}


	private void changed(){
		setChanged();
		notifyObservers();
	}
	
	private String getSequence(){
		return sequence.toString();
	
	}
	
	private String getEscapedSequence(){
		return sequence.getEscapedString();
	}


}