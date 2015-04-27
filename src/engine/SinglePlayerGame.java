package engine;

import java.util.Timer;
import java.util.TimerTask;

public class SinglePlayerGame {

	private static final int LOOSER = -1;
	
	
	private MemoSequence sequence = new MemoSequence();
	private Integer round = 0;
	private boolean status = true;
	private Timer isAliveTimer = new Timer();
	
	public SinglePlayerGame() {
		super();
		keepAlive();
		
	}

	private void keepAlive(){
		isAliveTimer.cancel();
		isAliveTimer = new Timer();
		
		isAliveTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				status = false;
				
			}
		}, 1000*60*3);
	}
	public int playerMoved(String playerSequence){
		keepAlive();
		if (playerSequence.equalsIgnoreCase(sequence.toString())) {
			round++;
			sequence.secondRound();
			return round;
		}else {
			
			round = LOOSER;
			return round;
		}
		
		
	}
	
	public String readRequest(String request){
		
		if (round == 0) {
			return "sequenza: "+ getSequence();
		} else {
			switch (playerMoved(request)) {
			case LOOSER:
				return "Hai perso! Continua ad allentarti e non demordere. Nonostante tutto hai completato ben " + round + " round";
							 
			default:
				return "Good, sequence: " + getSequence();
			}
		}
		
	}
	
	
	public String getSequence(){
		return sequence.toString();
	}
	
	public boolean getStatus(){
		return status;
	}
}
