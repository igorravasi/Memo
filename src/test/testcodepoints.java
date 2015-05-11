package test;

public class testcodepoints {

	public static void main(String[] args) {
		
		System.out.println("😄".codePointAt(0));
		Character c = "😄".charAt(0);
		System.out.println(Character.isHighSurrogate(c));
	
	}
}
