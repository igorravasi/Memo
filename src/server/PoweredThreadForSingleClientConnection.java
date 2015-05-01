package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import engine.SinglePlayerGame;

//TODO: pesante refactoring. Questa classe è assolutamente troppo lunga e poco coesa!

public class PoweredThreadForSingleClientConnection extends Thread implements Runnable{



	private Socket clientSocket;
	private Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
	
	@SuppressWarnings("serial")
	private class BadStringException extends Exception{

	}
	

	public PoweredThreadForSingleClientConnection(Socket clientSocket,
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
			//TODO: error
			page = "/error";
		}
		
		//TODO Anzichè l'if-else sarebbe opportuno usare ad esempio una mappa, con chiavi gli url e valore la classe gestore
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
			if (page.endsWith(".jpeg")||page.endsWith(".jpg")||page.endsWith(".png")||page.endsWith(".ico")) {
				writeOutputFromBinaryFile(page);
			} else {
				writeOutputFromTextFile(page);
			}
			
		}
		
	}
	
	private void startSinglePlayerGame() throws IOException{
	
		Integer playId = singleGames.size();
		singleGames.put(playId, new SinglePlayerGame());
		writeOutputMessage(playId+"");
		
	}
		
	private void writeOutputMessage(String bodyMessage) throws IOException{
		OutputStreamWriter out = initializeOutput("text/html",null);
		out.write(bodyMessage);
		out.write("\n");
		out.close();
	}
	
	private void writeOutputFromTextFile(String path) throws FileNotFoundException, IOException {
		
		BufferedReader fileReader;
		try {
			File file = new File("web"+path);
			fileReader = new BufferedReader(new FileReader(file));
			String contentLength = null;
			
			String contentType = "text/html"+" ; charset=utf-8";
			
			
			//TODO: Refactoring!!! Introduco questa gestione del contenttyoe solo epr verificare che funzionino  i css. Da rifare in altro modo!
			
			if (path.endsWith(".css")) {
				contentType = "text/css";
			} else if (path.endsWith(".js")) {
				contentType = "text/javascript";
			} 
			
			OutputStreamWriter out = initializeOutput(contentType,contentLength);

			String fileLine=fileReader.readLine();
			while(fileLine != null){
				out.write(fileLine+"\r\n");
				fileLine = fileReader.readLine();
				
			}
			
			fileReader.close();
			
			out.close();
			
		} catch (FileNotFoundException e) {
//			TODO: error
			writeOutputMessage("file non trovato");
			return;
		}
		
	
	
	}

	
	private void writeOutputFromBinaryFile(String path){
		
		try {
			File file = new File("web"+path);
			String contentLength = file.length() +"";
			
			String contentType = "image/ico";
			
			
			//TODO: Refactoring!!! Introduco questa gestione del contenttype solo per verificare che funzionino  i css. Da rifare in altro modo!
			
			if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
				contentType = "image/jpeg";
			} else if (path.endsWith(".png")) {
				contentType = "image/png";
			} else if (path.endsWith(".bmp")) {
				contentType = "image/bmp";
			}
			
			
			OutputStreamWriter out = initializeOutput(contentType, contentLength);
			out.flush();
			Files.copy(file.toPath(), clientSocket.getOutputStream());
			
			out.close();
			
		} catch (IOException e) {
			System.err.println("ioexception");
			try {
				writeOutputMessage("error");
			} catch (IOException e1) {
				
			}
		}
		
		
	}
	private OutputStreamWriter initializeOutput(String contentType, String contentLength) throws IOException {
		OutputStreamWriter out = new 
			OutputStreamWriter(
					clientSocket.getOutputStream(),
					Charset.forName("UTF-8").newEncoder()
			);

		out.write("HTTP/1.1 200 OK\n");
		out.write("Date: Tue, 17 Mar 2014 14:47:00\n");
	
	
		out.write("Content-Type: "+ contentType + "\n");
		
		if (contentLength != null) {
			
			out.write("Content-Length: " + contentLength+"\n");
			
		}
		
		out.write("\n");
		
		return out;
	}
	
}
