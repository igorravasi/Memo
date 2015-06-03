package server.services.extensions;

import java.util.Random;

/**
 * Classe utilizzata per passare parametri utili alla gestione della sessione.
 * 
 * @return ritorna un valore float
 */
public class SessionManager {


	
	Random randomizer = new Random(new Random().nextLong());
	
	public Long newSession(String user){
		
		return randomizer.nextLong();
	}
	

	
}
