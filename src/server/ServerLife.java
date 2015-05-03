package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.services.BinaryFileService;
import server.services.SinglePlayerService;
import server.services.TextFileService;

public class ServerLife {

	public static void main(String[] args) {
		
		//TODO: Aggiungere un observer che osservi le notifiche dello status delle partite, che dovranno essere observable
		
		try {
			
			@SuppressWarnings("resource")
			ServerSocket socket=new ServerSocket(ServerParameters.PORT);
			Socket clientSocket = null;
			
			Map<String, IService> services = new HashMap<String, IService>();
			services.put("/", new TextFileService());
			services.put("/css", new TextFileService());
			services.put("/js", new TextFileService());
			services.put("/resources", new BinaryFileService());
			services.put("/singleplayer", new SinglePlayerService());
			
			
			while (true) {
				
				try {
					clientSocket = socket.accept();
					new ServerThread(clientSocket, services).run();
				} catch (Exception e) {
					
				}
				
			}
			
			//socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
