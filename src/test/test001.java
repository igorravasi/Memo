package test;

import model.engine.MemoSequence;

/**
 *	Verifico l'incremento di due elementi per ogni serie, 5 sequenze in questo caso.
 *	Viene stampata myMemoSequence. Passo la struttura da MemoSequence.
*/
public class test001 {

	public static void main(String[] args) {

		//int num= 2;
		MemoSequence myMemoSequence = new MemoSequence();
		
		System.out.println(myMemoSequence);
	
		for (int i = 0; i < 5; i++) {
			myMemoSequence.secondRound();
			
			System.out.println(myMemoSequence);

		}
	}

}
