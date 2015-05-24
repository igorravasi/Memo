package server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import server.IService;
import server.basics.HttpRequest;
import server.basics.HttpStringMessage;

public class LoggingService implements IService{

	private static final String userField = "Utente";
	private static final String sessionField = "Sessione";
	private static final String passwordField = "Password";
	private static final String databaseFile = "db/utenti.txt";
	
	
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		
		
		Map<String, String> contentFields = readPostedContent(request);
		HttpStringMessage message = new HttpStringMessage();
		
		
		
		boolean logged = login( contentFields.get(userField), contentFields.get(passwordField) );
		if ( logged ) {
			message.setCookie(userField + "=" + contentFields.get(userField) + "; path/");
			long session = new Random().nextLong();
			message.setCookie(sessionField + "=" + session + "; path=/");
			message.sendMessage(clientSocket, "Logged");
		} else {
			message.sendMessage(clientSocket, "Error");
		}
		
		
	}

	
	private boolean login(String user, String password){
		
		if (user == null || password == null) {
			return false;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(databaseFile));
			String expectedLine = user + " " + password;
			
			String line = reader.readLine();
			while (line != null) {
				
				if (line.equals(expectedLine)) {
					reader.close();
					return true;
				}
				
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			return false;
		}
		
		return false;
	}
	
	private Map<String, String> readPostedContent(HttpRequest request){
		String postedLines[] = request.getContent().split("&");
		Map<String,String> fields = new HashMap<String, String>(); 
		
		for (int i = 0; i < postedLines.length; i++) {
			if (postedLines[i].matches("\\w+=\\w+")) {
				String pair[] = postedLines[i].split("=");
				fields.put(pair[0], pair[1]);
			}
		}
		
		return fields;
	}
	
}
