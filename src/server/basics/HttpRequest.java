package server.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

public class HttpRequest {

	private String uri;

	public HttpRequest(final Socket clientSocket) throws IOException{
		super();
		BufferedReader in = new BufferedReader(
			new InputStreamReader(
					clientSocket.getInputStream()
			)	
		);
		String line = in.readLine();
		
		String[] lineElements=line.split(" ");
		
		//L'uri è nella prima riga, al secondo posto.
		String uri=lineElements[1];
		LinkedList<String> lines=new LinkedList<String>();
		while(line!=null){
			
			lines.add(line);
			line = in.readLine();
			if(line.length()==0){
				line=null;
			}
		}
		this.uri=uri;
	}
	
	public String getUri() {
		return uri;
	}
	

}
