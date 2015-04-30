package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import engine.SinglePlayerGame;

//TODO: pesante refactoring. Questa classe è assolutamente troppo lunga e poco coesa!

public class ThreadForSingleClientConnection extends Thread implements Runnable{



	private Socket clientSocket;
	private Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
	
	@SuppressWarnings("serial")
	private class BadStringException extends Exception{

	}
	

	public ThreadForSingleClientConnection(Socket clientSocket,
			Map<Integer, SinglePlayerGame> singleGames) {
		super();
		this.clientSocket = clientSocket;
		this.singleGames = singleGames;
	}

	@Override
	public void run() {

	
		try {
			
			String inputRequest = getRequestLines();
			System.out.println(inputRequest.substring(0, inputRequest.indexOf("\n")).split(" ")[1]);
			handleRequest(inputRequest);
			clientSocket.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadStringException e) {
			//Non fare niente e continua (chiude il thread) senza fermare l'esecuzione del programma
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

// TODO: gestire richieste di file non html (esempio immagini) o usare un server http free, come apache
	
	private void handleRequest(String inputRequest)throws FileNotFoundException, IOException {
		
		
		StringTokenizer tokenizer = new StringTokenizer(inputRequest);
		tokenizer.nextToken();
		String page = tokenizer.nextToken();
		
		if (page == null) {
//			TODO: error
			page = "/error";
		}
		
		//TODO Ancihè l'if-else sarebbe opportuno usare ad esempio una mappa, con chiavi gli url e valore la classe gestore
		if (page.compareToIgnoreCase("/singleplayer") == 0) {
			startSinglePlayerGame();
		} else if (page.compareToIgnoreCase("/error") == 0) {
			writeOutputMessage("Si è verificato un errore");
		
		} else if (page.indexOf("/singleplayer") == 0) {
			Integer playId;
			try {
				int endOfPlayId = page.indexOf('?');
				if (endOfPlayId>0) {
										
					playId = Integer.parseInt(page.substring("/singleplayer".length()+1,endOfPlayId));
				}else {
					playId = Integer.parseInt(page.substring("/singleplayer".length()+1));
				}
				
				SinglePlayerGame game = singleGames.get(playId);
				if (game == null) {
					writeOutputMessage("E: No game Id");
					return;
				}
				writeOutputMessage(game.readRequest(page));
			} catch (NumberFormatException e) {
				writeOutputMessage("E: Error on parsing Game Id");
				e.printStackTrace();
			}
			
			
		}else {
			writeOutputFromFile(page);
		}
		
	}
	
	private void startSinglePlayerGame() throws IOException{
	
		Integer playId = singleGames.size();
		singleGames.put(playId, new SinglePlayerGame());
		writeOutputMessage(playId+"");
		
	}
		
	private void writeOutputMessage(String bodyMessage) throws IOException{
		OutputStreamWriter out = initializeOutput();
//		OutputStreamWriter out = new OutputStreamWriter(clientSocket.getOutputStream(),Charset.forName("UTF-8").newEncoder());
		out.write(bodyMessage);
		out.write("\n");
		out.close();
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
