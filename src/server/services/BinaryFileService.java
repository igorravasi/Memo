package server.services;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;

import server.HttpRequest;
import server.IService;

public class BinaryFileService implements IService{

	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request)
			throws IOException {
		
		String uri = request.getUri();
		File file = new File("web"+uri);
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
		

		OutputStreamWriter out = new 
				OutputStreamWriter(
						clientSocket.getOutputStream(),
						Charset.forName("UTF-8").newEncoder()
				);

		out.write("HTTP/1.1 200 OK\n");
		out.write("Date: Tue, 17 Mar 2014 14:47:00\n");
		
		if (contentType != null) {
			out.write("Content-Type: "+ contentType + "\n");
		}
		
		if (contentLength != null) {
			out.write("Content-Length: " + contentLength+"\n");
		}
		
		out.write("\n");
		
		
		out.flush();
		Files.copy(file.toPath(), clientSocket.getOutputStream());
		
		out.close();
		
	}

}
