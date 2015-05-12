package test;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class testRegEx {

	public static void main(String[] args) {
		String regEx = ".*\\*\\*\\* include: .+ \\*\\*\\*.*";
		System.out.println("Ecco la regex di prova : " + regEx);
	
		List<String> stringhe = new LinkedList<String>();
		Collections.addAll(stringhe, 
				"giro giro tondo",
				"casca il mondo",
				"casca la terra",
				"casca la *** include: terra *** tutti giù per terra",
				"*** include: memo.txt ***",
				"ahahhaha"
				);
		
	for (Iterator<String> iterator = stringhe.iterator(); iterator.hasNext();) {
		String string = (String) iterator.next();
		if (string.matches(regEx)) {
			System.out.print("GOOD: ");
		}
		System.out.println(string);
		
	}
	
	
	}
	
	
	
	
}
