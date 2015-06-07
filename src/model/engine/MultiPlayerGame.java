package model.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MultiPlayerGame extends Observable{

	//TODO: valore da configurator
	private static final int sequenceLength = 14;
	
	private static final int LOOSER = -1;
	private static final int ALIVE_TIMER = 1000*60*20; //VENTI MINUTI PER GIOCARE LA PARTITA INTERA
	private static final String SEQUENCE_ID ="S:";
	
	//TODO: da configurator
	private static final int MAX_ROOM_SIZE = 2;
	
	private MemoSequence sequence = new MemoSequence();
	private Timer isAliveTimer = new Timer();
	private Map<String, Integer> gamers = new HashMap<String, Integer>();

	
	private Integer playId;
	
	public Integer getPlayId() {
		return playId;
	}

	public MultiPlayerGame(Integer playId) {
		super();

		Random random = new Random();
		random.setSeed(random.nextLong());
		
		int remaining = sequenceLength - sequence.length();
		while (remaining > 0) {
			int addNow = random.nextInt(4);
			addNow = addNow < remaining ? addNow : remaining;
			sequence.nextRound(addNow);
		}
		
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

		if (gamers.size() < MAX_ROOM_SIZE) {
			return false;
		} else {
			return true;
		}
	}
	
	public void addGamer(String user){
		this.gamers.put(user, 0);
	}
	
	private boolean isAGamer(String gamer){
		return gamers.containsKey(gamer);
		
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
	

	public String readRequest(String content, String user){
		
		if (!isAGamer(user)) {
			//TODO: da configurator
			return "E:User is not a gamer";
		}
		
		int round = gamers.get(user);
		
		if (round == 0) {
			keepAlive();
			round++;
			gamers.put(user, round);
			return SEQUENCE_ID + getSequence(user);
		} else {
			
			if (content.indexOf(SEQUENCE_ID) >= 0) {
				return "";
				//return getTheResult(playerMoved(content));
			} else {
				return "E: Malformed request, Reload the page";
			}
			
		}
		
	}


	
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
	
	private String getSequence(String user){
		Integer round = gamers.get(user);
		return sequence.toString(round);
	
	}
	
	private String getEscapedSequence(String user){
		Integer round = gamers.get(user);
		return sequence.getEscapedString(round);
	}


}
