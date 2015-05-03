package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.basics.HttpRequest;


public class ServerThread extends Thread implements Runnable{

	private Socket clientSocket;
	
	private Map<String, IService> services = new HashMap<String, IService>();
	
	
	
	public ServerThread(Socket clientSocket,
			 Map<String, IService> services) {
		super();
		this.clientSocket = clientSocket;
		this.services = services;
	}
	

	@Override
	public void run() {

	
		try {
			
			HttpRequest request = new HttpRequest(clientSocket);
			System.out.println(request.getUri());
			handleRequest(request);
			clientSocket.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void handleRequest(HttpRequest request)throws FileNotFoundException, IOException {
			
		String page = request.getUri();

		IService service = services.get(page);
		
		while (service == null) {
			
			page = page.substring(0, page.lastIndexOf("/"));
			if (page.equalsIgnoreCase("")) {
				page = "/";
			}
			service = services.get(page);
		}
		
		service.sendHttpResponse(clientSocket, request);
			
		
	}
	
	
	
}
