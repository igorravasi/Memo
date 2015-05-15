package server.basics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class HttpRequest {

	private String uri;
	private LinkedList<String> lines;
	
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
		lines=new LinkedList<String>();
		while(line!=null){
			
			lines.add(line);
			line = in.readLine();
			if(line.length()==0){
				line=null;
			}
		}
		this.uri=uri;
		
		
		
		//TODO: rimuovere dopo al debug
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.err.println(string);
		}
		
	}
	
	public String getUri() {
		return uri;
	}
	
	public LinkedList<String> getLines(){
		return lines;
	}

}
