package engine;

import java.util.Random;

public class MemoElement {

	private Integer number;
	private Character colourCode;
	private static final Integer MAX_INT_CODE = 3;	
//	TODO: Rimpiazzare la logica MAX_INT_CODE e switch-case con un'enumerazione. 
//	TODO: Refactoring della classe, possibili design pattern che migliorerebbero l'architettura e le prestazioni: flyweight e prototype 
	
	
	public MemoElement(Integer number, Integer intColourCode) {
		super();
		this.number = number;
		setColourCode(intColourCode);
	}

	public MemoElement() {
		super();
		
		Random randomizer = new Random();
		
		setNumber(randomizer.nextInt(10));
		setColourCode(randomizer.nextInt(MAX_INT_CODE + 1));
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Character getColourCode() {
		return colourCode;
	}

	public void setColourCode(Integer intColourCode) {

		Character tmpChar = null;
		
		if (intColourCode < 0) {
			intColourCode = 0;
		} else if (intColourCode > MAX_INT_CODE) {
			intColourCode = MAX_INT_CODE;
		}
		
		switch (intColourCode) {
		case 0:
			tmpChar = 'r';
			break;
		case 1:
			tmpChar = 'g';
			break;
		case 2:
			tmpChar = 'b';
			break;
		case 3:
			tmpChar = 'y';
			break;	
		default:
			break;
		}
		
		
		this.colourCode = tmpChar;
	}
	
	
}
