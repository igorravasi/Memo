package model.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class MultiPlayerGame extends Observable{

	//TODO: valore da configurator
	private static final int sequenceLength = 4;
	
	private static final int LOOSER = -1;
	private static final int ALIVE_TIMER = 1000*60*10; //DIECI MINUTI PER GIOCARE LA PARTITA INTERA
	private static final String SEQUENCE_ID ="S:";
	
	//TODO: da configurator
	private static final int MAX_ROOM_SIZE = 2;
	
	private MemoSequence sequence = new MemoSequence();
	private Timer isAliveTimer = new Timer();
	private Map<String, Integer> gamers = new HashMap<String, Integer>();
	private Set<String> gamersOver = new HashSet<String>();
	
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
			int addNow = random.nextInt(3) + 1;
			addNow = addNow < remaining ? addNow : remaining;
			sequence.nextRound(addNow);
			remaining -= addNow;
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
	
	public boolean isAGamer(String gamer){
		return gamers.containsKey(gamer);
		
	}
	
	public boolean canPlay(String user){
		return !(gamers.containsKey(user) || gamersOver.contains(user));
	}
	
	private int playerMoved(String playerSequence, String user){
		playerSequence = playerSequence.substring(playerSequence.indexOf(SEQUENCE_ID) + SEQUENCE_ID.length());
		playerSequence = playerSequence.trim();
		
		String actualSequence = getEscapedSequence(user);
		
		if (actualSequence.equalsIgnoreCase(playerSequence)) {
			int round = gamers.get(user);
			round++;
			gamers.put(user, round);
			return round;
		} else {
			
			return LOOSER;
		}
	}

	public Iterator<String> getUsers(){
		return gamers.keySet().iterator();
	}
	
	public String readRequest(String content, String user){
		
		if (!isAGamer(user) || gamersOver.contains(user)) {
			//TODO: da configurator
			return "E: User is not a valid gamer (now)";
		}
		
		
		
		if (gamers.get(user) == 0) {
			gamers.put(user, 1);
			return SEQUENCE_ID + getSequence(user);
		} else {
			
			if (content.indexOf(SEQUENCE_ID) >= 0) {
				return getTheResult(playerMoved(content, user), user);
			} else {
				return "E: Malformed request, Reload the page";
			} 
			
		}
		
	}

	

	private String getTheResult(int roundResult, String user){
		
		if (roundResult == LOOSER) {
			//TODO: RIMUOVERE GIOCATORE dal gioco, lasciandone traccia
			gamersOver.add(user);
			return "L: "+ gamers.get(user);
		} else if (roundResult > sequence.roundsNumber()) {
			gamersOver.add(user);
			return "W: completed";
		} else {
			return SEQUENCE_ID + getSequence(user);
		}
		
	}


	public void changed(){
		setChanged();
		notifyObservers();
	}
	
	public boolean ended(){
		
		if (gamers.size() == gamersOver.size()) {
			return true;
		}
		return false;
	}
	
	private String getSequence(String user){
		Integer round = gamers.get(user);
		return sequence.toString(round);
	
	}
	
	private String getEscapedSequence(String user){
		Integer round = gamers.get(user);
		return sequence.getEscapedString(round);
	}
	public String getfinalResult(){
		
		Integer reachedRound = 0;
		
		for (Iterator<String> iterator = gamers.keySet().iterator(); iterator.hasNext();) {
			String user = (String) iterator.next();
			if (gamers.get(user) > reachedRound) {
				reachedRound = gamers.get(user);	
			}
		}
		
		String winners = "";
		for (Iterator<String> iterator = gamers.keySet().iterator(); iterator.hasNext();) {
			String user = (String) iterator.next();
			if (gamers.get(user) == reachedRound) {
				winners += user + " ";
			}
		}
		winners = winners.trim();
		
		String players = "";
		for (Iterator<String> iterator = gamers.keySet().iterator(); iterator.hasNext();) {
			String user = (String) iterator.next();
			players += gamers.get(user) + "-" + user + " ";
		}
		players = players.trim();
		
		return reachedRound + "\t" + winners + "\t" + players;
		
	}


}
