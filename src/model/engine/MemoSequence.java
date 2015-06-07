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
		addElements(startingElements);
	}
	
	
	/**
	 * Aggiunge elementi alla nuova sequenza lasciando invariati i precedenti
	 */
	public void nextRound() {
		addElements(nextElements);
	}
	
	
	private void addElements(Integer numberOfElementsToAdd){
		
		rounds.add(numberOfElementsToAdd);
		
		for (int i = 0; i < numberOfElementsToAdd; i++) {
			sequenza.put(counter, factory.generateMemoElement());
			counter++;
		}
		
	}
	
	
	public String getEscapedString(){
		
		return getEscapedString(this.toString().length());

	}
	
	public String getEscapedString(int endOfSubstring){
		/*
			La sequenza viene restituita in questo modo: 
			Ogni carattere viene rappresentato byte per byte in esadecimale, ogni byte è preceduto dal simbolo %
			Il motivo di questo è quello di poter ricevere dal client la sequenza formattata tranquillamente così,
			cioè "url-encoded". (La trasmissione non-url-encoded delle emoji cambia da  browser a browser, soprattuto con emtodo GET)
		*/
		String consideredSequence = this.toString().substring(0, endOfSubstring);
		byte[] bytes = consideredSequence.getBytes(Charset.forName("UTF-8"));
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < bytes.length; i++) {
			sb.append("%");
			sb.append(String.format("%02X", bytes[i]));
		}
		
		return sb.toString();

	}

	@Override
	public String toString() {

		String tmpString = "";
		
		for (int i = 0; i < counter; i++) {
			MemoElement tmpMemoElement = sequenza.get(i);
			tmpString += tmpMemoElement.getEmoj();
		}
		
		return tmpString;
	}
}
