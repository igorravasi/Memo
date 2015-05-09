package engine;

import java.nio.charset.Charset;
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
	
	public String getEscapedString(){
			
		byte[] bytes = toString().getBytes(Charset.forName("UTF-8"));
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
