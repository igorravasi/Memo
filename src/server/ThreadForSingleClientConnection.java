package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class ThreadForSingleClientConnection extends Thread implements Runnable{

	private Socket clientSocket;
	private Integer id;
	
	public ThreadForSingleClientConnection(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
		
	}


	public ThreadForSingleClientConnection(Socket clientSocket, Integer threadId) {
		super();
		this.clientSocket = clientSocket;
		this.id = threadId;
		
	}



	

	@Override
	public void run() {

			
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputRequest =  in.readLine();
			if (inputRequest == null) {
				throw new Exception("not input lines");
			}
			String line = "\n";
			
			while(line != null && line.length() != 0){
				inputRequest += line + "\n";
				line = in.readLine();
				
			}
			
			//Riga di debug
			System.out.println(id +  "--> \n" + inputRequest);
			
			StringTokenizer tokenizer = new StringTokenizer(inputRequest);
			tokenizer.nextToken();
			if (tokenizer.nextToken().compareToIgnoreCase("/singleplayer") == 0) {
				writeSequence();
			}

		
		} catch (Exception e) {
			//Non fare niente e termina il thread
		}
		
		
	}
	
	private void writeSequence() throws FileNotFoundException, IOException{
		
		write("sequenza");
		
	}
		
	
	private void write(String body) throws IOException{
		
		OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream(), Charset.forName("UTF-8"));
		out.write("HTTP/1.1 200 OK\n");
		out.write("Date: Tue, 17 Mar 2014 14:47:00\n");
		out.write("Content-Type: text/html; charset=utf-8\n");
		out.write("\n");
		out.write(body);
		out.write("\n");
		out.flush();
	}
	
	
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}


	public void setId(Integer id) {
		this.id = id;
		
	}

//
//	private void writeOutput(OutputStreamWriter out)
//			throws FileNotFoundException, IOException {
//		BufferedReader fileReader=new BufferedReader(
//			new FileReader("web/index01.html")
//		);
//		String fileLine=fileReader.readLine();
//		while(fileLine!=null){
//			out.write(fileLine+"\n");
//			fileLine=fileReader.readLine();
//		}
//		fileReader.close();
//		out.write("\n");
//	}
//
//	private OutputStreamWriter initializeOutput() throws IOException {
//		OutputStreamWriter out = new 
//			OutputStreamWriter(
//					clientSocket.getOutputStream(),
//					Charset.forName("UTF-8").newEncoder()
//			);
//
//		out.write("HTTP/1.1 200 OK\n");
//		out.write("Date: Tue, 17 Mar 2014 14:47:00\n");
//		out.write("Content-Type: text/html; charset=utf-8\n");
//		out.write("\n");
//		
//		return out;
//	}
//
//	private void getInputLines(LinkedList<String> lines) throws IOException {
//		BufferedReader in = new BufferedReader(
//				new InputStreamReader(
//						clientSocket.getInputStream()
//				)	
//			);
//			
//			String line = in.readLine();
//			
//			
//			while(line!=null){
//				System.out.println(line);
//				lines.add(line);
//				line = in.readLine();
//				if(line.length()==0){
//					line=null;
//				}
//			}
//			//in.close();
//	}
	
}


