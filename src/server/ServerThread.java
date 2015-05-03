package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.services.BinaryFileService;
import server.services.TextFileService;

//TODO: pesante refactoring. Questa classe è assolutamente troppo lunga e poco coesa!

public class ServerThread extends Thread implements Runnable{

	private Socket clientSocket;
	
	private Map<String, IService> services = new HashMap<String, IService>();
	private IService singlesService;
	
	
	public ServerThread(Socket clientSocket,
			 Map<String, IService> services) {
		super();
		this.clientSocket = clientSocket;
		this.services = services;
	}
	
	
	public ServerThread(Socket clientSocket,
			 Map<String, IService> services, IService singleservice) {
		super();
		this.clientSocket = clientSocket;
		this.services = services;
		this.singlesService = singleservice;
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

		
		if (page.contains("/singleplayer")) {
			singlesService.sendHttpResponse(clientSocket, request);
		} else {
			if (page.endsWith(".jpeg")||page.endsWith(".jpg")||page.endsWith(".png")||page.endsWith(".ico")) {
				new BinaryFileService().sendHttpResponse(clientSocket, request);
			} else {
				new TextFileService().sendHttpResponse(clientSocket, request);
			}
		}
		
		
	}
	

	
	
	
}
