package server;

import java.io.IOException;
import java.net.Socket;

public interface IService {

	
	public void sendHttpResponse(final Socket clientSocket, HttpRequest request) throws IOException;
	
}
