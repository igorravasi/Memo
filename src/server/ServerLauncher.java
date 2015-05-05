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
		BinaryFileService binaryService = new BinaryFileService();
		
		
		Map<String, String> contentTypes = new HashMap<String, String>();
		loadContentTypes(contentTypes);
		
		fileService.setContentTypeMap(contentTypes);
		binaryService.setContentTypeMap(contentTypes);
		
		server.addService("/", fileService);
		server.addService("/css", fileService);
		server.addService("/js", fileService);
		
		server.addService("/resources", binaryService);
		server.addService("/favicon.ico", binaryService);
		
		server.addService("/singleplayer", new SinglePlayerService());
		
		server.launch();
		
	}
	
	
	public static void loadContentTypes(Map<String, String> contentTypes){
		
		contentTypes.put(".html", "text/html");
		contentTypes.put(".css", "text/css");
		contentTypes.put(".js", "text/javascript");
		contentTypes.put(".jpg", "image/jpeg");
		contentTypes.put(".jpeg", "image/jpeg");
		contentTypes.put(".png", "image/jpeg");
		contentTypes.put(".bmp", "image/jpeg");
		contentTypes.put("ico", "image/ico");
	}
}
