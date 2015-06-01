package model.engine;

import java.util.HashMap;

public class MemoElementFactory {

	private static final int numberOfElements = 10;
	private HashMap<Integer, MemoElement> flyweights = new HashMap<Integer, MemoElement>();
	
	public MemoElement generateMemoElement(){
			
		//Integer key = randomizeKey();
		Integer key = null;
		MemoElement element = flyweights.get(key);
		
		if (element == null) {
			
			element = new MemoElement(); //MemoElement(key)
			flyweights.put(key, element);
		}
		
		return element;
	}
	
	
	
}
