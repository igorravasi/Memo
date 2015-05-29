package server;

import server.config.MemoServerConfigurator;
import server.services.BinaryFileService;
import server.services.LoggingService;
import server.services.SinglePlayerService;
import server.services.TextFileService;

public class ServerLauncher {

	public static String PORT_NUMBER_IDENTIFIER = "port_number";
	
	public static void main(String[] args) {
		
		HttpMemoServer server = new HttpMemoServer();
		
		int port = Integer.parseInt(MemoServerConfigurator.getInstance().getValue(PORT_NUMBER_IDENTIFIER));
		server.setPort(port);
		
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
