package engine;

import java.nio.charset.Charset;
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
				setChanged();
				notifyObservers();
			}
		}, ALIVE_TIMER); //Dopo due minuti di inattività notifica l'observer
	}
	private int playerMoved(String playerSequence){
		keepAlive();
		playerSequence = playerSequence.substring(playerSequence.indexOf("?S=")+"?S=".length());
		playerSequence = playerSequence.replace("+", " ");
		playerSequence = playerSequence.trim();
		
//		playerSequence = new String(playerSequence.getBytes(Charset.forName("UTF-8")),Charset.forName("UTF-8"));
		
		String actualSequence = getSequence().trim();


		byte[] actualSequenceBytes = actualSequence.getBytes(Charset.forName("UTF-8"));
		byte[] playerSequenceBytes = playerSequence.getBytes(Charset.forName("UTF-8"));
		
		
		if (compareSequencesBytes(actualSequenceBytes, playerSequenceBytes)) {
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
			return "L: "+ round;
		} else {
			return "S:"+ getSequence();
		}
			
	}
	

	private boolean compareSequencesBytes(byte[] sequence1, byte[] sequence2){
		
		
		if (sequence1.length == sequence2.length) {
			for (int i = 0; i < sequence1.length; i++) {
				if (!(sequence1[i] == sequence2[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	
	
	private String getSequence(){
		return sequence.toString();
	}


}
