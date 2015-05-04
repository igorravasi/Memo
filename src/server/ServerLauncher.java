package server;

import server.services.BinaryFileService;
import server.services.SinglePlayerService;
import server.services.TextFileService;

public class ServerLauncher {

	public static void main(String[] args) {
		
		HttpMemoServer server = new HttpMemoServer();
		server.setPort(4444);
		
		TextFileService fileService = new TextFileService();
		
		server.addService("/", fileService);
		server.addService("/css", fileService);
		server.addService("/js", fileService);
		
		BinaryFileService binaryService = new BinaryFileService();
		
		server.addService("/resources", binaryService);
		server.addService("/favicon.ico", binaryService);
		
		server.addService("/singleplayer", new SinglePlayerService());
		
		server.launch();
		
	}
	
}
