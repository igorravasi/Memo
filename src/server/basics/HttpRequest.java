package server.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private String uri;
	
	private Map<String, String> headers = new HashMap<String, String>();
	private String content = null;
	
	public HttpRequest(final Socket clientSocket) throws IOException{
		super();
		BufferedReader in = new BufferedReader(	new InputStreamReader( clientSocket.getInputStream() ) );
		
		String line = in.readLine();
		String[] lineElements=line.split(" ");
		
		//L'uri è nella prima riga, al secondo posto.
		
		boolean isPost = lineElements[0].equalsIgnoreCase("POST") ? true : false;
		
		String uri=lineElements[1];
		

		headers.put("Request:", line);

		
		while(line!=null && line.length() != 0){
			String[] headerElements = line.split(" ", 2);
			headers.put(headerElements[0], headerElements[1]);
			
			line = in.readLine();
		}
		
		if (isPost) {
			
			int contentLength;
			
			try {
				contentLength = Integer.parseInt( headers.get("Content-Length:") );
			} catch (NumberFormatException e) {
				contentLength = 0;
			}
			
			int c = 0;
			content = "";
			
			for (int i = 0; i < contentLength; i++) {
				c = in.read();
				content += (char) c;
			}
			
		}
		
		this.uri=uri;
	
		
	}
	
	public String getUri() {
		return uri;
	}
	
	public String getContent(){
		return content;
	}

}
