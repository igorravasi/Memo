package server.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import server.HttpMessage;
import server.HttpRequest;
import server.IService;

public class TextFileService implements IService {

	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException{

		BufferedReader fileReader;
		String uri = request.getUri();
		File file = new File("web"+uri);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		fileReader = new BufferedReader(new FileReader(file));
		String contentType = "text/html"+" ; charset=utf-8";
		
		
		if (uri.endsWith(".css")) {
			contentType = "text/css";
		} else if (uri.endsWith(".js")) {
			contentType = "text/javascript";
		} 
		
		
		HttpMessage message = new HttpMessage();
		message.setContentType(contentType);
		 
		message.sendResponseHeader(clientSocket);

		String fileLine=fileReader.readLine();
		while(fileLine != null){
			message.getOut().write(fileLine+"\r\n");
			fileLine = fileReader.readLine();
			
		}
		
		fileReader.close();
		
		message.closeHttpResponse();

	
		

	}

}
