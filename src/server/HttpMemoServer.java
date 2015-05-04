package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpMemoServer {

	
	private int port;
	private Map<String, IService> services = new HashMap<String, IService>();
		
	public void setPort(int port){
		this.port = port;
	}
	
	public void addService(String path, IService newService){
		services.put(path,newService);
	}
	
	
	public void launch() {

		try {
			@SuppressWarnings("resource")
			ServerSocket socket=new ServerSocket(port);
			Socket clientSocket = null;	
			
			while (true) {
				try {
					clientSocket = socket.accept();
					new ServerThread(clientSocket, services).run();
				} catch (Exception e) {
				}
				
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
