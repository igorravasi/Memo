package server;

import server.services.BinaryFileService;
import server.services.LoggingService;
import server.services.SinglePlayerService;
import server.services.TextFileService;

public class ServerLauncher {

	
	public static void main(String[] args) {
		
		HttpMemoServer server = new HttpMemoServer();
		server.setPort(4444);
		
		TextFileService fileService = new TextFileService();
		BinaryFileService binaryService = new BinaryFileService();


		server.addService("/", fileService);
		server.addService("/css", fileService);
		server.addService("/js", fileService);
		
		server.addService("/resources", binaryService);
		
		
		server.addService("/singleplayer", new SinglePlayerService());
		server.addService("/login", new LoggingService());
		
		
		server.launch();
		
	}
	
	
//TODO:	
//	public static void generateKeyboardFile(){
//		
//	}

}
