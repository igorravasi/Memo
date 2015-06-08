package model.engine;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Sequenza di gioco
 *
 */
public class MemoSequence {

	private final static int startingElements = 3;
	private final static int nextElements = 2;
	
	private final static MemoElementFactory factory = new MemoElementFactory();
	
	private Map<Integer, MemoElement> sequenza = new HashMap<Integer, MemoElement>();
	private Integer counter = 0;
	
	private List<Integer> rounds = new LinkedList<Integer>();
	
	/**
	 * Inizio il gioco con una sequenza di tre emoji
	 * 
	 */
	public MemoSequence() {
		super();
		nextRound(startingElements);
	}

	
	public int length(){
		return sequenza.size();
	}
	
	public int roundsNumber(){
		return (rounds.size());
	}
	/**
	 * Aggiunge n elementi (n=default) alla nuova sequenza lasciando invariati i precedenti
	 */
	public void nextRound() {
		nextRound(nextElements);
	}


	/**
	 * Aggiunge numberOfElementsToAdd elementi alla sequenza
	 * @param numberOfElementsToAdd
	 */
	public void nextRound(Integer numberOfElementsToAdd) {
		Integer previous = 0;
		if (rounds.size() > 0) {
			previous = rounds.get(roundsNumber() - 1);
		}
		
		rounds.add(numberOfElementsToAdd + previous);
		
		for (int i = 0; i < numberOfElementsToAdd; i++) {
			sequenza.put(counter, factory.generateMemoElement());
			counter++;
		}
		
	}

	
	public String getEscapedString(){
		
		return getEscapedString( this.roundsNumber() );

	}
	
	public String getEscapedString(Integer round){
		/*
			La sequenza viene restituita in questo modo: 
			Ogni carattere viene rappresentato byte per byte in esadecimale, ogni byte è preceduto dal simbolo %
			Il motivo di questo è quello di poter ricevere dal client la sequenza formattata tranquillamente così,
			cioè "url-encoded". (La trasmissione non-url-encoded delle emoji cambia da  browser a browser, soprattuto con emtodo GET)
		*/		
		
		String consideredSequence;
		if (round == null) {
			consideredSequence = "";
		} else {
			consideredSequence = this.toString(round);
		}
		
		byte[] bytes = consideredSequence.getBytes(Charset.forName("UTF-8"));
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < bytes.length; i++) {
			sb.append("%");
			sb.append(String.format("%02X", bytes[i]));
		}
		
		return sb.toString();

	}

	
	public String toString( Integer round){
		
		Integer numberOfElements;
		try {
			numberOfElements = rounds.get(round - 1);
		} catch (Exception e) {
			numberOfElements = 0;
		}
		
		String tmpString = "";
		for (int i = 0; i < numberOfElements; i++) {
			MemoElement tmpMemoElement = sequenza.get(i);
			tmpString += tmpMemoElement.getEmoj();
		}
		
		return tmpString;
	}
	
	@Override
	public String toString() {

		return this.toString(this.roundsNumber());
	}
}
