package server.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

import server.IService;
import server.basics.HttpMessage;
import server.basics.HttpRequest;

public class BinaryFileService implements IService{

	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException{
		
		
		String uri = request.getUri();

		File file = new File("web"+uri);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}

		String contentLength = file.length() +"";
		String contentType = null;
		
		
		if (uri.endsWith(".jpg") || uri.endsWith(".jpeg")) {
			contentType = "image/jpeg";
		} else if (uri.endsWith(".png")) {
			contentType = "image/png";
		} else if (uri.endsWith(".bmp")) {
			contentType = "image/bmp";
		} else if (uri.endsWith(".ico")) {
			contentType = "image/ico";
		}
		

		HttpMessage message = new HttpMessage();
		message.setContentLength(contentLength);
		message.setContentType(contentType);
		message.sendResponseHeader(clientSocket);
				
		Files.copy(file.toPath(), message.getOutStream());
		
		message.closeHttpResponse();
		
	}
	
	
	

}
