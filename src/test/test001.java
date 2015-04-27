package test;

import engine.MemoSequence;

public class test001 {

	public static void main(String[] args) {

		MemoSequence myMemoSequence = new MemoSequence();
		
		System.out.println(myMemoSequence);
	
		for (int i = 0; i < 5; i++) {
			myMemoSequence.secondRound();
			
			System.out.println(myMemoSequence);

		}
	}

}
