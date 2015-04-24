package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerLife {

	public static void main(String[] args) {
		
		

		
		try {
			ServerSocket socket=new ServerSocket(ServerParameters.PORT);
			Socket clientSocket = null;
			
			while (true) {
				clientSocket = socket.accept();
				new ThreadForSingleClientConnection().setClientSocket(clientSocket).run();
			}
			
			//socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
