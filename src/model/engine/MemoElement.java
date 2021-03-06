package model.engine;

import java.nio.charset.Charset;


//TODO: Refactoring della classe, possibili design pattern che migliorerebbero l'architettura e le prestazioni: flyweight e prototype
public class MemoElement {

	byte leftBound = (byte) 0x81;
	byte[] emojBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, leftBound};


	public MemoElement(Integer index){
		emojBytes[emojBytes.length -1] = (byte) (leftBound + index);
	}
	
	
	public String getEmoj() {
		return new String(emojBytes, Charset.forName("UTF-8"));
	}
	
}
