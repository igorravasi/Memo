package server.basics;

import java.io.IOException;
import java.net.Socket;

public class HttpStringMessage extends HttpMessage{
	
	public void sendMessage(Socket clientSocket, String messageBody) throws IOException{
		
		setContentType("text/html; charset=utf-8");
		sendResponseHeader(clientSocket);
		getOut().write(messageBody);
		closeHttpResponse();
	}
	
}
