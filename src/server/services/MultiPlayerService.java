package server.services;

import java.io.IOException;
import java.net.Socket;

import server.basics.HttpRequest;
import server.services.extensions.SessionManager;

public class MultiPlayerService implements IService {

	private SessionManager sessionManager;
	
	public MultiPlayerService(SessionManager sessionManager) {

		this.sessionManager = sessionManager;
	}
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException {
		

	}

}
