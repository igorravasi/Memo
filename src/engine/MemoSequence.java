package engine;

import java.util.HashMap;
import java.util.Map;


public class MemoSequence {

	private Map<Integer, MemoElement> sequenza = new HashMap<Integer, MemoElement>();
	private Integer counter = 0;
	
	public MemoSequence() {
		super();
		addElements(3);
	}
	
	
	public void secondRound() {
		addElements(2);
	}
	
	private void addElements(Integer numberOfElementsToAdd){
		
		for (int i = 0; i < numberOfElementsToAdd; i++) {
			sequenza.put(counter, new MemoElement());
			counter++;
		}
		
	}
	
	
//	Solo a fini di test
	@Override
	public String toString() {

		String tmpString = "";
		
		for (int i = 0; i < counter; i++) {
			MemoElement tmpMemoElement = sequenza.get(i);
			tmpString += i +":" + tmpMemoElement.getNumber() + tmpMemoElement.getColourCode() + " ";
		}
		
		return tmpString;
	}
}
