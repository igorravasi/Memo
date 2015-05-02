package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import engine.SinglePlayerGame;

//TODO: pesante refactoring. Questa classe è assolutamente troppo lunga e poco coesa!

public class PoweredThreadForSingleClientConnection extends Thread implements Runnable{



	private Socket clientSocket;
	private Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
	


	public PoweredThreadForSingleClientConnection(Socket clientSocket,
			Map<Integer, SinglePlayerGame> singleGames) {
		super();
		this.clientSocket = clientSocket;
		this.singleGames = singleGames;
	}

	@Override
	public void run() {

	
		try {
			
			HttpRequest request = new HttpRequest(clientSocket);
			System.out.println(request.getUri());
			handleRequest(request.getUri());
			clientSocket.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	
	private void handleRequest(String page)throws FileNotFoundException, IOException {
		
		

		
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
			System.err.println("ioexception: " + "web"+path);
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
