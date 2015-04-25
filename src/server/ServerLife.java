package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import engine.SinglePlayerGame;

public class ServerLife {

	public static void main(String[] args) {
		
		Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();

		
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
