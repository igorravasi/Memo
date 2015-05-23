package server.services;

import java.io.IOException;
import java.net.Socket;

import server.IService;
import server.basics.HttpRequest;

public class LoggingService implements IService{

	private static final String userField = "Utente";
	
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		// TODO Auto-generated method stub
		
		
		
	}

}
