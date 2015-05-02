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
		
		OutputStreamWriter out = new 
				OutputStreamWriter(
						clientSocket.getOutputStream(),
						Charset.forName("UTF-8").newEncoder()
				);

		out.write("HTTP/1.1 200 OK\n");
		String serverDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
		out.write("Date: " + serverDate + "\n");
		out.write("Content-Type: "+ contentType + "\n");
					
		out.write("\n");

		String fileLine=fileReader.readLine();
		while(fileLine != null){
			out.write(fileLine+"\r\n");
			fileLine = fileReader.readLine();
			
		}
		
		fileReader.close();
		
		out.close();

	
		

	}

}
