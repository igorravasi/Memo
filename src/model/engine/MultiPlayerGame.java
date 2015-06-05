package model.engine;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe per gestire il gioco singolo.
 * Inizializzo i valori 
 * ex. imposto il timer di due minuti per inserire la sequenza
 *
 */
public class MultiPlayerGame extends Observable{

	private static final int LOOSER = -1;
	private static final int ALIVE_TIMER = 1000*60*20; //VENTI MINUTI PER GIOCARE LA PARTITA INTERA
	private static final String SEQUENCE_ID ="S:";
	
	private MemoSequence sequence = new MemoSequence();
	private Integer round = 0;
	private Timer isAliveTimer = new Timer();
	private String gamers[] = {null, null};
	
	private Integer playId;
	
	public Integer getPlayId() {
		return playId;
	}

	public MultiPlayerGame(Integer playId) {
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
				
				//TODO: decretare il vincitore e mandare notifiche
				changed();
				
			}
		}, ALIVE_TIMER); 
	}
	
	
	public boolean isStarted(){

		for (int i = 0; i < gamers.length; i++) {
			if (gamers[i] == null) {
				return false;
			}
		}
		
		return true;
		
	}
	

//	private int playerMoved(String playerSequence){
//		keepAlive();
//		playerSequence = playerSequence.substring(playerSequence.indexOf(SEQUENCE_ID)+SEQUENCE_ID.length());
//		playerSequence = playerSequence.trim();
//				
//		String actualSequence = getEscapedSequence();
//			
//		if (actualSequence.equalsIgnoreCase(playerSequence)) {
//			round++;
//			sequence.nextRound();
//			return round;
//		}else {
//			
//			return LOOSER;
//		}
//		
//		
//	}
	


//	public String readRequest(String content){
//		
//		if (round == 0) {
//			keepAlive();
//			round++;
//			return "S:"+ getSequence();
//		} else {
//			
//			if (content.indexOf(SEQUENCE_ID) >= 0) {
//				
//				return getTheResult(playerMoved(content));
//			} else {
//				return "E: Malformed request, Reload the page";
//			}	
//		}
//		
//	}
	
//	private String getTheResult(int roundResult){
//		
//		if (roundResult == LOOSER) {
//			changed();
//			return "L: "+ round;
//		} else {
//			return SEQUENCE_ID + getSequence();
//		}
//			
//	}


	private void changed(){
		setChanged();
		notifyObservers();
	}
	
//	private String getSequence(){
//		return sequence.toString();
//	
//	}
//	
//	private String getEscapedSequence(){
//		return sequence.getEscapedString();
//	}


}
