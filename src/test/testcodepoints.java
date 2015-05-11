package test;

public class testcodepoints {

	public static void main(String[] args) {
		
		int baseCodePoint = "😄".codePointAt(0); 
		System.out.println(baseCodePoint);
		
		System.out.println(Character.toChars(baseCodePoint));
		
		for (int i = 0; i < 10; i++) {
			System.out.println( Character.toChars( baseCodePoint + i ) );
			
			System.err.println( Character.getName( baseCodePoint + i ) );
		}
	
	}
}
