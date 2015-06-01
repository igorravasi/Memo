package model.engine;

import java.util.HashMap;
import java.util.Random;

public class MemoElementFactory {

	private static final int numberOfElements = 10;
	
	private HashMap<Integer, MemoElement> flyweights = new HashMap<Integer, MemoElement>();
	
	public MemoElement generateMemoElement(){
			
		Integer key = randomizeKey(numberOfElements);
		
		MemoElement element = flyweights.get(key);
		
		if (element == null) {
			
			element = new MemoElement(key); 
			flyweights.put(key, element);
		}
		
		return element;
	}
	

	private Integer randomizeKey(Integer n){
		Random randomizer = new Random();
		randomizer.setSeed(randomizer.nextLong());
		
		return randomizer.nextInt(n);

	}
	
}
