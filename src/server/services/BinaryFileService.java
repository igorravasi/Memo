package server.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import server.IService;
import server.basics.HttpMessage;
import server.basics.HttpRequest;

//TODO: TextFileService e BinaryFileService sono entrambi, nella logica, FileServici, dovrebbero 
//derivate da una classe comuna FileService!

public class BinaryFileService implements IService{

	private Map<String, String> contentTypes;
	
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
		
		
		String extension = uri.substring(uri.lastIndexOf("."), uri.length());	
		contentType = contentTypes.get(extension);
	

		HttpMessage message = new HttpMessage();
		message.setContentLength(contentLength);
		message.setContentType(contentType);
		message.sendResponseHeader(clientSocket);
				
		Files.copy(file.toPath(), message.getOutStream());
		
		message.closeHttpResponse();
		
	}
	
	
	public void setContentTypeMap(Map<String, String> contentTypes){
		
		this.contentTypes = contentTypes;
	}
	

}
