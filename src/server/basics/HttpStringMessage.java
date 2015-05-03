package server.basics;

import java.io.IOException;
import java.net.Socket;

public class HttpStringMessage {

	HttpMessage message = new HttpMessage();
	
	public void sendMessage(Socket clientSocket, String messageBody) throws IOException{
		
		message.setContentType("text/html");
		message.sendResponseHeader(clientSocket);
		message.getOut().write(messageBody);
		message.closeHttpResponse();
	}
	
}
