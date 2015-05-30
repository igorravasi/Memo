package server.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import server.basics.HttpRequest;
import server.basics.HttpStringMessage;
import server.config.MemoServerConfigurator;

public class LoggingService implements IService{

	private static final String delete = "=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
	
	private static final String databaseFileName = "users_database";
	private static final String okResponseName = "logging_ok_response";
	private static final String badResponseName = "logging_bad_response";
	
	private static final String userField = "Utente";
	private static final String sessionField = "Sessione";
	private static final String passwordField = "Password";
	private static final String doField = "Do";
	
	private static final String logoutValue = "logout";
	
	private MemoServerConfigurator configurator = MemoServerConfigurator.getInstance();
		
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException {
		
		
		Map<String, String> contentFields = readPostedContent(request);
		
		HttpStringMessage message = new HttpStringMessage();
		
		boolean isLogout = isLogout( contentFields.get(doField) );
		boolean logged = login( contentFields.get(userField), contentFields.get(passwordField) );
		
		String response = configurator.getValue(okResponseName);
		
		if (isLogout) {
			//Annullo i cookie di sessione
			message.setCookie(userField + delete);
			message.setCookie(sessionField + delete);
			
		} else if ( logged ){
			//Invio i cookie di sessione
			message.setCookie(userField + "=" + contentFields.get(userField) + "; path/");
			long session = new Random().nextLong();
			message.setCookie(sessionField + "=" + session + "; path=/");
			
		} else {
			
			response = configurator.getValue(badResponseName);
			
		}
				
		message.sendMessage(clientSocket, response);
		
	}

	
	private boolean isLogout(String command){
		//Restituisce true se il comando è quello di logout
		
		if (command != null && command.equalsIgnoreCase(logoutValue)) {
			return true;
		}
	
		return false;
	
	}
	
	
	private boolean login(String user, String password){
		//Ritorna true solo nel caso in cui si trova con successo la corrispondenza utente-password nel database
		
		//Viene dato per scontato che utente e password non contengono caratteri speciali del tipo spazio, andata a capo..
		if (user == null || password == null) {
			return false;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader( configurator.getValue(databaseFileName) ));
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
		
		//Divido il contenuto postato in elementi (separati da &)
		String singleContents[] = request.getContent().split("&");
		Map<String,String> fields = new HashMap<String, String>(); 
		
		//Divido ogni elemento in nome e valore (separati da = ) e lo inserisco nella mappa
		for (int i = 0; i < singleContents.length; i++) {
			if (singleContents[i].matches("\\w+=\\w+")) {
				String pair[] = singleContents[i].split("=",2);
				fields.put(pair[0], pair[1]);
			}
		}
		
		return fields;
	}
	
}
