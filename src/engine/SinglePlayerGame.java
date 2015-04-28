package engine;

import java.nio.charset.Charset;
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
	private int playerMoved(String playerSequence){
		keepAlive();
		playerSequence = playerSequence.substring(playerSequence.indexOf("?S=")+"?S=".length());
		playerSequence = playerSequence.replace("+", " ");
		playerSequence = playerSequence.trim();
		
		String actualSequence = getSequence().trim();
		
		if (playerSequence.equalsIgnoreCase(actualSequence)) {
			round++;
			sequence.secondRound();
			return round;
		}else {
			
			return LOOSER;
		}
		
		
	}
	
	public String readRequest(String request){
		
		if (round == 0) {
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
			
			return "L: "+ round;
		} else {
			return "S:"+ getSequence();
		}
		
		
	}
	
	
	
	public String getSequence(){
		return sequence.toString();
	}
	
	public boolean getStatus(){
		return status;
	}
}
