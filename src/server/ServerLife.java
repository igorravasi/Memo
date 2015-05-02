package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.services.TextFileService;
import engine.SinglePlayerGame;

public class ServerLife {

	public static void main(String[] args) {
		
		Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
		//TODO: Aggiungere un observer che osservi le notifiche dello status delle partite, che dovranno essere observable
		
		try {
			
			@SuppressWarnings("resource")
			ServerSocket socket=new ServerSocket(ServerParameters.PORT);
			Socket clientSocket = null;
			
			Map<String, IService> services = new HashMap<String, IService>();
			services.put("/", new TextFileService());
//			Integer threadId = 0;
			
			while (true) {
				
				try {
					clientSocket = socket.accept();
					new ServerThread(clientSocket,singleGames, services).run();
				} catch (Exception e) {
					
				}
//				threadId++;
			}
			
			//socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
