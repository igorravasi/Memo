package test;

public class testcodepoints {

	public static void main(String[] args) {
		
		int baseCodePoint = "😄".codePointAt(0); 
		//Stampa l'intero, posizione surrogato? 
		System.out.println(baseCodePoint+ "\n");
		//Da Unicode a UTF-8.
		//System.out.println(Character.toChars(baseCodePoint));
		
		
		for (int i = 0; i < 10; i++) {
			//Stampa emoji diverse incrementando codifica.
			System.out.println( Character.toChars( baseCodePoint + i ) );
			
			System.out.println( Character.getName( baseCodePoint + i ) );
		}
	
	}
}
