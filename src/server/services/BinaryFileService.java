package server.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

import server.basics.HttpMessage;
import server.basics.HttpRequest;
import server.config.MemoServerConfigurator;

//TODO: TextFileService e BinaryFileService sono entrambi, nella logica, FileServici, dovrebbero 
//derivate da una classe comuna FileService!

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
		
		String extension = uri.substring(uri.lastIndexOf("."), uri.length());	
		String contentType = MemoServerConfigurator.getInstance().getContentType(extension);

		HttpMessage message = new HttpMessage();
		message.setContentLength(contentLength);
		message.setContentType(contentType);
		message.sendResponseHeader(clientSocket);
				
		Files.copy(file.toPath(), message.getOutStream());
		
		message.closeHttpResponse();
		
	}
	



}
