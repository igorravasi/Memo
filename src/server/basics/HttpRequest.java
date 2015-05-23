package server.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {

	private String uri;
	
	private Map<String, String> headers = new HashMap<String, String>();
	private String content = null;
	
	
	public HttpRequest(final Socket clientSocket) throws IOException{
		
		super();
		BufferedReader in = new BufferedReader(	new InputStreamReader( clientSocket.getInputStream() ) );
		
		String line = in.readLine();
		String[] lineElements=line.split(" ");
		
		//Controllo se il metodo della richiesta � POST
		boolean isPost = lineElements[0].equalsIgnoreCase("POST") ? true : false;
		
		//L'uri � nella prima riga, al secondo posto.
		uri=lineElements[1];
		
		
		while(line!=null && line.length() != 0){
			//System.err.println(line);
			String[] headerElements = line.split(" ", 2);
			headers.put(headerElements[0], headerElements[1]);
			
			line = in.readLine();
		}
		
		
		if (isPost) {
			content = readContent(in);
		}

	}
	
	
	private String readContent(BufferedReader in) throws IOException{
				
		int contentLength;
		try {
			contentLength = Integer.parseInt( headers.get("Content-Length:") );
		} catch (NumberFormatException e) {
			contentLength = 0;
		}
		

		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < contentLength; i++) {
			int c = in.read();
			builder.append((char) c);
		}
	
		return builder.toString();
	}
	
	public String getUri() {
		return uri;
	}
	
	public String getContent(){
		return content;
	}
	
	public Map<String,String> getCookies(){
		
		String cookies = headers.get("Cookie:");
			
		
		
		Map<String, String> cookieMap = new HashMap<String, String>();
		
		
		if (cookies != null) {
			StringTokenizer tokenizer = new StringTokenizer(cookies, "; ");

			while (tokenizer.hasMoreTokens()) {
				String cookie = tokenizer.nextToken();
				
				StringTokenizer tokenizer2 = new StringTokenizer(cookie,"=");
				String cookieName = tokenizer2.nextToken();
				String cookieValue = tokenizer2.nextToken();
				
				cookieMap.put(cookieName, cookieValue);
			}
		}
		
		
		
		return cookieMap;
	}

}
