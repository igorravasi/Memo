package server;

import java.util.HashMap;
import java.util.Map;

import server.services.BinaryFileService;
import server.services.SinglePlayerService;
import server.services.TextFileService;

public class ServerLauncher {

	public static void main(String[] args) {
		
		HttpMemoServer server = new HttpMemoServer();
		server.setPort(4444);
		
		TextFileService fileService = new TextFileService();
		
		Map<String, String> contentTypes = new HashMap<String, String>();
		
		contentTypes.put(".html", "text/html ; charset=utf-8");
		contentTypes.put(".css", "text/css");
		contentTypes.put(".js", "text/javascript");
	
		fileService.setContentTypeMap(contentTypes);
		
		server.addService("/", fileService);
		server.addService("/css", fileService);
		server.addService("/js", fileService);
		
		BinaryFileService binaryService = new BinaryFileService();
		
		contentTypes = new HashMap<String, String>();
		
		contentTypes.put(".jpg", "image/jpeg");
		contentTypes.put(".jpeg", "image/jpeg");
		contentTypes.put(".png", "image/jpeg");
		contentTypes.put(".bmp", "image/jpeg");
		contentTypes.put("ico", "image/ico");
		
		binaryService.setContentTypeMap(contentTypes);
		

		
		server.addService("/resources", binaryService);
		server.addService("/favicon.ico", binaryService);
		
		server.addService("/singleplayer", new SinglePlayerService());
		
		server.launch();
		
	}
	
}
