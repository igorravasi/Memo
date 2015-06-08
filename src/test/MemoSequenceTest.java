package test;

import javax.sound.midi.Sequence;

import model.engine.MemoSequence;
/**
 * Verifico l'incremento di 2 elementi per ogni serie, 5 sequenze in questo caso.
 * Viene stampata myMemoSequence. Passo la struttura da MemoSequence.
 */
public class MemoSequenceTest {

	public static void main(String[] args) {

		//int num= 2;
		MemoSequence myMemoSequence = new MemoSequence();
		
		System.out.println(myMemoSequence);
	
		for (int i = 0; i < 1; i++) {
			myMemoSequence.nextRound();
			
			System.out.println(myMemoSequence);

		}
		
		System.err.println(myMemoSequence.getEscapedString());
		System.err.println(myMemoSequence.getEscapedString(0));
		System.err.println(myMemoSequence.getEscapedString(1));
		
	}

}
