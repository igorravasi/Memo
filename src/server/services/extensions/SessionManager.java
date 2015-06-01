package server.services.extensions;

import java.util.Random;

public class SessionManager {


	
	Random randomizer = new Random(new Random().nextLong());
	
	public Long newSession(String user){
		
		return randomizer.nextLong();
	}
	

	
}
