package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import server.services.SinglePlayerService;

public class ServerLife {

	public static void main(String[] args) {
		
//		Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
		//TODO: Aggiungere un observer che osservi le notifiche dello status delle partite, che dovranno essere observable
		
		try {
			
			@SuppressWarnings("resource")
			ServerSocket socket=new ServerSocket(ServerParameters.PORT);
			Socket clientSocket = null;
			
			Map<String, IService> services = new HashMap<String, IService>();
//			services.put("/", new TextFileService());
			
			IService singleService = new SinglePlayerService();
			
			while (true) {
				
				try {
					clientSocket = socket.accept();
					new ServerThread(clientSocket, services,singleService).run();
				} catch (Exception e) {
					
				}
				
			}
			
			//socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
