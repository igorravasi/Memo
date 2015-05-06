package engine;

import java.nio.charset.Charset;
import java.util.Random;

public class MemoElement {

	byte[] emojBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x81};
	String emoj = new String(emojBytes, Charset.forName("UTF-8"));
//	TODO: Rimpiazzare la logica MAX_INT_CODE e switch-case con un'enumerazione. 
//	TODO: Refactoring della classe, possibili design pattern che migliorerebbero l'architettura e le prestazioni: flyweight e prototype 
	
	
	public MemoElement(String emoj) {
		super();
		this.emoj = emoj;
	}

	public MemoElement() {
		super();
		
		Random randomizer = new Random();
		randomizer.setSeed(randomizer.nextLong());
		//setEmoj(randomizer.nextInt(emoj.length()));
		
	}

	
	
	public String getEmoj() {
		return emoj;
	}

	public void setEmoj(String emoj) {
		this.emoj = emoj;
	}
	
}
