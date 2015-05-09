package engine;

import java.nio.charset.Charset;
import java.util.Random;


//TODO: Refactoring della classe, possibili design pattern che migliorerebbero l'architettura e le prestazioni: flyweight e prototype
public class MemoElement {

	byte leftBound = (byte) 0x81;
	byte[] emojBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, leftBound};

//Valori per debug con numeri anzich� emoticon
//	byte leftBound = (byte)0x30;
//	byte[] emojBytes = {leftBound};
	
 
	
	
//	public MemoElement(byte lastByte) {
//		super();
//		this.emojBytes[emojBytes.length -1] = lastByte;
//		
//	}

	public MemoElement() {
		super();
		randomizeLastByte();
		
	}

	
	
	public String getEmoj() {
		return new String(emojBytes, Charset.forName("UTF-8"));
	}

	
	private void randomizeLastByte(){
		Random randomizer = new Random();
		randomizer.setSeed(randomizer.nextLong());
		
		emojBytes[emojBytes.length -1] = (byte) (leftBound + randomizer.nextInt(10));


	}
	

	
}
