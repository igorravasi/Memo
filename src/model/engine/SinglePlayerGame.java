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
public class SinglePlayerGame extends Observable{

	private static final int LOOSER = -1;
	private static final int ALIVE_TIMER = 1000*60*2; //DUE MINUTI
	private static final String SEQUENCE_ID ="S:";
	
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
//		System.err.println(playId + " started @ " + (System.currentTimeMillis()/1000));
		keepAlive();
	}

	private void keepAlive(){
		isAliveTimer.cancel();
		isAliveTimer = new Timer();
//		System.err.println(playId + " restarted @ " + (System.currentTimeMillis()/1000));
		isAliveTimer.schedule(new TimerTask() {
			
			/* (non-Javadoc)
			 * @see java.util.TimerTask#run()
			 */
			@Override
			public void run() {
//				System.err.println(playId + " ended @ " + (System.currentTimeMillis()/1000));
				changed();
				
			}
		}, ALIVE_TIMER); //Dopo due minuti di inattivita' notifica l'observer
	}
	
	private int playerMoved(String playerSequence){
		keepAlive();
		playerSequence = playerSequence.substring(playerSequence.indexOf(SEQUENCE_ID)+SEQUENCE_ID.length());
		playerSequence = playerSequence.trim();
				
		String actualSequence = getEscapedSequence();
			
		if (actualSequence.equalsIgnoreCase(playerSequence)) {
			round++;
			sequence.nextRound();
			return round;
		}else {
			
			return LOOSER;
		}
		
		
	}
	
	/**
	 * Leggo la sequenza inserita dal giocatore
	 * @param content
	 * @return sequenza inserita o errore nel leggerla
	 */
	public String readRequest(String content){
		
		if (round == 0) {
			keepAlive();
			round++;
			return "S:"+ getSequence();
		} else {
			
			if (content.indexOf(SEQUENCE_ID) >= 0) {
				
				return getTheResult(playerMoved(content));
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
			return SEQUENCE_ID + getSequence();
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
