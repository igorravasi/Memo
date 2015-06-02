package test;

import model.engine.MemoSequence;

//Controllo emoticon.

public class MemoSequenceTest01 {

	public static void main(String[] args) {

		MemoSequence myMemoSequence = new MemoSequence();
		final int roundAggiuntivi = 5;
		final String emo = "😃";
		int lunghezzaAttesa =3 + 2*roundAggiuntivi;
	
		//SecondRound() aggiunge due elementi alla sequenza.
		for (int i = 0; i < roundAggiuntivi; i++) {
			myMemoSequence.nextRound();
				
		}
		
		String sequenza = myMemoSequence.toString();
		int lunghezzaCalcolata= utf_length(sequenza);
				
		
		if(lunghezzaCalcolata == lunghezzaAttesa){
			System.out.println("Emoticon contate giuste:\n" + lunghezzaCalcolata);
		}else {
			System.out.println("Emoticon contate sbagliate");
			System.out.println("Giuste: "+ lunghezzaAttesa);
			System.out.println("Contate: "+ lunghezzaCalcolata);
		}
		
	
		System.out.println(emo + " è lunga:\n" + emo.length() );
		System.out.println(emo + " in verità è lunga:\n" + utf_length(emo));
	}
	
	public static int utf_length(String stringa){
		
		int contatore = 0;
		for (int i = 0; i < stringa.length(); i++) {
			char c = stringa.charAt(i);
			if (Character.isSurrogate(c)) {
				contatore += 1;
			} else {
				contatore += 2;
			}
		}
		
		return contatore / 2;
	}

}

