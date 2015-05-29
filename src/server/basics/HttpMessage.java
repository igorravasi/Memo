package server.basics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpMessage {
	
	private static final String separator = ": ";
	
	private OutputStream outStream;
	private OutputStreamWriter out;

	private Map<String, String> headers = new HashMap<String, String>();
	private List<String> cookies = new LinkedList<String>();
	
	public HttpMessage() {
	
		String serverDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
		headers.put("Date", serverDate);
	}

	public void sendResponseHeader(Socket clientSocket) throws IOException{
		
		outStream = clientSocket.getOutputStream();
		out = new OutputStreamWriter(outStream, Charset.forName("UTF-8").newEncoder());
		
		out.write("HTTP/1.1 200 OK\n");
		
		Set<String> headerNames = headers.keySet();
		for (String name : headerNames) {
			String line = name + headers.get(name) + "\n";
			out.write(line);
		}

				
		for (String singleCookie : cookies) {
			out.write("Set-cookie: " + singleCookie + "\n");
		}
		
		out.write("\n");
		
	}
	
	
	public void closeHttpResponse() throws IOException{
		out.write("\n");
		out.close();
	}
	
	public OutputStream getOutStream() {
		return outStream;
	}
	
	public OutputStreamWriter getOut(){
		return out;
	}
	
	
	public void addHeader(String headerName, String headerValue){
		
		headers.put(headerValue + separator, headerValue);
	}
	
	public void setCookie (String cookie){
			
		this.cookies.add(cookie);
	}

	
}
