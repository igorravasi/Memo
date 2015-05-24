package server;

import server.config.MemoServerConfigurator;
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
		
	
		addContentTypes();


		server.addService("/", fileService);
		server.addService("/css", fileService);
		server.addService("/js", fileService);
		
		server.addService("/resources", binaryService);
		server.addService("/favicon.ico", binaryService);
		
		server.addService("/singleplayer", new SinglePlayerService());
		server.addService("/login", new LoggingService());
		
		
		server.launch();
		
	}
	
	
//TODO:	
//	public static void generateKeyboardFile(){
//	
//		
//	}
	
	
	public static void addContentTypes(){
		
		MemoServerConfigurator configurator = MemoServerConfigurator.getInstance();

		configurator.addContentType(".html", "text/html");
		configurator.addContentType(".css", "text/css");
		configurator.addContentType(".js", "text/javascript");
		configurator.addContentType(".jpg", "image/jpeg");
		configurator.addContentType(".jpeg", "image/jpeg");
		configurator.addContentType(".png", "image/jpeg");
		configurator.addContentType(".bmp", "image/jpeg");
		configurator.addContentType("ico", "image/ico");
	}
}
