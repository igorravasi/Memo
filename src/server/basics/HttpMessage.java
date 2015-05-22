package server.basics;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HttpMessage {
	
	

	private OutputStream outStream;
	private OutputStreamWriter out;
	private String contentLength = null;
	private String contentType = null;
	private List<String> cookies = null;
	
	public HttpMessage() {
		super();
	
	}

	public void sendResponseHeader(Socket clientSocket) throws IOException{
		
		outStream = clientSocket.getOutputStream();
		out = new OutputStreamWriter(outStream, Charset.forName("UTF-8").newEncoder());
		
		String serverDate = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
		
		out.write("HTTP/1.1 200 OK\n");
		out.write("Date: " + serverDate + "\n");
		
		//TODO: refactoring
		if (contentType != null) {
			out.write("Content-Type: " + contentType +"\n");
		}
		if (contentLength != null) {
			out.write("Content-Length: " + contentLength+"\n");
		}
		if (cookies != null) {
			for (Iterator<String> iterator = cookies.iterator(); iterator.hasNext();) {
				String singleCookie = (String) iterator.next();
				out.write("Set-cookie: " + singleCookie + "\n");
			}
			
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
	
	
	
	
	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setCookie (String cookie){
		
		if (this.cookies == null) {
			this.cookies = new LinkedList<String>();
		}
		
		this.cookies.add(cookie);
	}

	
}
