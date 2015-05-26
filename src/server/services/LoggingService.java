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
	private static final String doField = "Do";
	
	private static final String logoutValue = "logout";
	
	private static final String databaseFile = "db/utenti.txt";
	
	private static final String okResponse = "Logged";
	private static final String badResponse = "Error";
	
	
	
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		
		
		Map<String, String> contentFields = readPostedContent(request);
		if (contentFields == null) {
			contentFields = new HashMap<String, String>();
		}
		HttpStringMessage message = new HttpStringMessage();
		
		boolean isLogout = isLogout( contentFields.get(doField) );
		boolean logged = login( contentFields.get(userField), contentFields.get(passwordField) );
		
		String response = okResponse;
		
		if (isLogout) {
			String delete = "=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
			
			message.setCookie(userField + delete);
			message.setCookie(sessionField + delete);
			
		} else if ( logged ){
			
			message.setCookie(userField + "=" + contentFields.get(userField) + "; path/");
			long session = new Random().nextLong();
			message.setCookie(sessionField + "=" + session + "; path=/");
			
		} else {
			
			response = badResponse;
			
		}
				
		message.sendMessage(clientSocket, response);
		
	}

	
	private boolean isLogout(String command){
		
		if (command != null && command.equalsIgnoreCase(logoutValue)) {
			return true;
		}
	
		return false;
	
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
