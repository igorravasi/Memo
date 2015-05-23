package server.services;

import java.io.IOException;
import java.net.Socket;

import server.IService;
import server.basics.HttpRequest;
import server.basics.HttpStringMessage;

public class LoggingService implements IService{

	private static final String userField = "Utente";
	
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		// TODO Auto-generated method stub
		
		String user = request.getCookies().get(userField);
		
		if (user != null) {
			
		}
		
		HttpStringMessage message = new HttpStringMessage();
		message.setCookie("Utente=provauser; path=/");
		message.sendMessage(clientSocket, "");
		
	}

}
