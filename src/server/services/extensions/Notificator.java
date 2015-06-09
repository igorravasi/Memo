package server.services.extensions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
			FileWriter writer = new FileWriter(dbn, true);
			while (users.hasNext()) {
				String user = (String) users.next();
				writer.write(user + "\t" + message + "\n");
				
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Iterator<String> getNotifies(String user){
		
		List<String> notifies = new LinkedList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(dbn));
			
			String line = reader.readLine();
			
			while (line != null) {
				
				if (line.trim().startsWith(user)) {
					
					String notify = line.substring(user.length() + 1);
					notifies.add(notify);
					
				}
				line = reader.readLine();
				
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notifies.iterator();
	}
}
