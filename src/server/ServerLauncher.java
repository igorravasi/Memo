package server;

import server.config.MemoServerConfigurator;
import server.services.BinaryFileService;
import server.services.LoggingService;
import server.services.MultiPlayerService;
import server.services.NotificationService;
import server.services.SinglePlayerService;
import server.services.TextFileService;
import server.services.extensions.SessionManager;

/**
 *@category serverlauncher
 *@package server
 *Questa classe serve a far partire il server del gioco
 *
 */

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
		
		SessionManager sessionManager = new SessionManager();
		server.addService("/multiplayer", new MultiPlayerService(sessionManager));
		server.addService("/login", new LoggingService(sessionManager));
		
		server.addService("/notifiche", new NotificationService(sessionManager));
		
		KeyboardGenerator.main(null);
		
		server.launch();
		
	}
	


}
