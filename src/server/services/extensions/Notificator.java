package server.services.extensions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Notificator {

	private final static Notificator notificator = new Notificator();
	private final static String dbn = "db/notifiche.txt";
	
	private Notificator(){
		
	}
	
	public static Notificator it(){
		return notificator;
	}
	
	public void writeNotify(Iterator<String> users, String message){
		
		try {
			FileWriter writer = new FileWriter(dbn);
			while (users.hasNext()) {
				String user = (String) users.next();
				writer.write(user + "\t" + message + "\n");
				System.err.println(user + "\t" + message + "\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
