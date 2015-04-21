package server;

import java.io.IOException;
import java.net.ServerSocket;

public class IlMain {

	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub

//		System.out.println("Prova SVN  ");
//		System.out.println("igorravasi On");
//		System.out.println(" ScrumBoy ON ");
//		System.out.println(" ghia16 ON ");
//		System.out.println(" bipavesi ON ");
//		System.out.println("edoardo OFF");
		
		try {
			ServerSocket socket=new ServerSocket(ServerParameters.PORT);
			
			
			
			ThreadForSingleClientConnection singleClientConnection = new ThreadForSingleClientConnection();
			
			while (true) {
				singleClientConnection.setClientSocket(socket.accept()).run();
			}
			
			//socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
