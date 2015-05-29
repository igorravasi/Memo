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
	private static final String enter ="\n";
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
		
		writeln("HTTP/1.1 200 OK");
		
		Set<String> headerNames = headers.keySet();
		for (String name : headerNames) {
			String line = name + headers.get(name);
			writeln(line);
		}

				
		for (String singleCookie : cookies) {
			writeln("Set-cookie" + separator + singleCookie);
		}
		
		writeln("");
		
	}
	
	public void writeln(String line) throws IOException{
		out.write(line + enter);
	}
	
	public void closeHttpResponse() throws IOException{
		writeln("");
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
