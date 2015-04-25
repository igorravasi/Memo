package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

public class ThreadForSingleClientConnection extends Thread implements Runnable{

	private Socket clientSocket;
	private Integer id;
	
	@SuppressWarnings("serial")
	
	private class BadStringException extends Exception{

	}
	
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
			
			String inputRequest = getRequestLines();
			
			//Riga di debug
			System.out.println(id +  "--> \n" + inputRequest);
			
			handleRequest(inputRequest);
			
			clientSocket.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadStringException e) {
//			Non fare niente e continua (chiude il thread) senza fermare l'esecuzione del programma
		}

		
	}


	private String getRequestLines() throws IOException, BadStringException {
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String inputRequest =  in.readLine();
		if (inputRequest == null) {
			throw new BadStringException();
			
		}
		String line = "\n";
		
		while(line != null && line.length() != 0){
			inputRequest += line + "\n";
			line = in.readLine();
			
		}
		return inputRequest;
	}

// TODO: gestire richieste di file non html (esempio immagini)
	
	private void handleRequest(String inputRequest)throws FileNotFoundException, IOException {
		
		
		StringTokenizer tokenizer = new StringTokenizer(inputRequest);
		tokenizer.nextToken();
		String page = tokenizer.nextToken();
		
		if (page == null) {
//			TODO: error
			page = "/error";
		}
		
		
		if (page.compareToIgnoreCase("/singleplayer") == 0) {
			writeSequence();
		} else if (page.compareToIgnoreCase("/error") == 0) {
			writeOutputMessage("Si è verificato un errore");
		
		} else {
			writeOutputFromFile(page);
		}
		
	}
	
	private void writeSequence() throws FileNotFoundException, IOException{
		
		writeOutputMessage("sequenza");
		
	}
		
	
	private void writeOutputMessage(String bodyMessage) throws IOException{
		OutputStreamWriter out = initializeOutput();
		out.write(bodyMessage);
		out.write("\n");
		out.close();;
	}
	
	
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}


	public void setId(Integer id) {
		this.id = id;
		
	}


	private void writeOutputFromFile(String path) throws FileNotFoundException, IOException {
		
		BufferedReader fileReader;
		try {
			fileReader = new BufferedReader(new FileReader("web" + path));
		} catch (FileNotFoundException e) {
//			TODO: error
			writeOutputMessage("file non trovato");
			return;
		}
		
		OutputStreamWriter out = initializeOutput();
		
		String fileLine=fileReader.readLine();
		while(fileLine!=null){
			out.write(fileLine+"\n");
			fileLine=fileReader.readLine();
		}
		
		fileReader.close();
		out.write("\n");
		out.close();
	}

	private OutputStreamWriter initializeOutput() throws IOException {
		OutputStreamWriter out = new 
			OutputStreamWriter(
					clientSocket.getOutputStream(),
					Charset.forName("UTF-8").newEncoder()
			);

		out.write("HTTP/1.1 200 OK\n");
		out.write("Date: Tue, 17 Mar 2014 14:47:00\n");
		out.write("Content-Type: text/html; charset=utf-8\n");
		out.write("\n");
		
		return out;
	}


	
}


