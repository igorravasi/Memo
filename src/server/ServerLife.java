package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import engine.SinglePlayerGame;

public class ServerLife {

	public static void main(String[] args) {
		
		Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
		//TODO: Aggiungere un observer che osservi le notifiche dello status delle partite, che dovranno essere observable
		
		try {
			
			ServerSocket socket=new ServerSocket(ServerParameters.PORT);
			Socket clientSocket = null;
			
			Integer threadId = 0;
			
			while (true) {
				clientSocket = socket.accept();
				new ThreadForSingleClientConnection(clientSocket,singleGames).run();
				threadId++;
			}
			
			//socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
