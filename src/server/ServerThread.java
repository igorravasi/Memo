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

import server.services.BinaryFileService;
import server.services.TextFileService;
import engine.SinglePlayerGame;

//TODO: pesante refactoring. Questa classe è assolutamente troppo lunga e poco coesa!

public class ServerThread extends Thread implements Runnable{

	private Socket clientSocket;
	private Map<Integer, SinglePlayerGame> singleGames = new HashMap<Integer, SinglePlayerGame>();
	private Map<String, IService> services = new HashMap<String, IService>();
	
	public ServerThread(Socket clientSocket,
			Map<Integer, SinglePlayerGame> singleGames, Map<String, IService> services) {
		super();
		this.clientSocket = clientSocket;
		this.singleGames = singleGames;
		this.services = services;
	}

	@Override
	public void run() {

	
		try {
			
			HttpRequest request = new HttpRequest(clientSocket);
			System.out.println(request.getUri());
			handleRequest(request);
			clientSocket.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void handleRequest(HttpRequest request)throws FileNotFoundException, IOException {
			
		String page = request.getUri();
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
				new BinaryFileService().sendHttpResponse(clientSocket, request);
			} else {
				new TextFileService().sendHttpResponse(clientSocket, request);
			}
			
		}
		
	}
	
	private void startSinglePlayerGame() throws IOException{
	
		Integer playId = singleGames.size();
		singleGames.put(playId, new SinglePlayerGame());
		writeOutputMessage(playId+"");
		
	}
		
	private void writeOutputMessage(String bodyMessage) throws IOException{
	OutputStreamWriter out = new 
			OutputStreamWriter(
					clientSocket.getOutputStream(),
					Charset.forName("UTF-8").newEncoder()
			);

		out.write("HTTP/1.1 200 OK\n");
		out.write("Date: Tue, 17 Mar 2014 14:47:00\n");
		out.write("Content-Type: text/html\n");
		out.write("\n");
		out.write(bodyMessage);
		out.write("\n");
		out.close();
	}
	
	
	
}
