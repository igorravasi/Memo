package test;

import server.HttpMemoServer;
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
		server.addService("/resources", new BinaryFileService());
		server.addService("/singleplayer", new SinglePlayerService());
		
		server.launch();
		
	}
	
}
