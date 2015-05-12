package test;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


public class testRegEx {

	public static void main(String[] args) {
//		String regEx = "\\#\\#\\#.+\\#\\#\\#";
		String regEx = "#{3}.+#{3}";
		System.out.println("Ecco la regex di prova : " + regEx);
	
		List<String> stringhe = new LinkedList<String>();
		Collections.addAll(stringhe, 
				"giro giro tondo",
				"casca il mondo",
				" casca la terra ",
				"NON PIù SUPPORTATO casca la ### include: terra ### tutti giù per terra",
				"### memo.txt ###",
				"ahahhaha"
				);
		
	for (Iterator<String> iterator = stringhe.iterator(); iterator.hasNext();) {
		String string = (String) iterator.next();
		if (string.matches(regEx)) {
			System.out.print("GOOD: filename is");
			String path =  new StringTokenizer(string,"###").nextToken();
			
			System.out.println(path);
			
		}
		System.out.println(string.trim());
		
	}


	
	
	

	}
	
	
	
	
}
