package server.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.StringTokenizer;

import server.basics.HttpMessage;
import server.basics.HttpRequest;
import server.config.MemoServerConfigurator;

//extFileService e BinaryFileService sono entrambi, nella logica, FileService, dovrebbero 
//derivare da una classe comune FileService!
//Update: tuttavia visto che le linee di codice condivise non sono contigue non so se 
//la presenza di esse giustificherebbe un refactoring del genere.

public class TextFileService implements IService {

		
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException{

		String uri = request.getUri();
		
		if ( uri.equalsIgnoreCase("/") ) {
			uri = MemoServerConfigurator.getInstance().getValue("home_page");
		}
		
		BufferedReader fileReader;
		try {
			fileReader = initializeBufferedFileReader(uri);
		} catch (FileNotFoundException e1) {
			fileReader = initializeBufferedFileReader("/Errore.html");
		}
		
		HttpMessage message = initializeMessage(clientSocket, uri);

		String fileLine = fileReader.readLine();
		while( fileLine != null ){
			if (fileLine.trim().matches("#{3}.+#{3}")) {
				
				try {
					String path = new StringTokenizer(fileLine.trim(),"###").nextToken();
					path = "web/include" + path.trim();
					message.getOut().flush();
					Files.copy(new File(path).toPath(), message.getOutStream());
					message.getOutStream().flush();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				message.getOut().write( fileLine );
				
			}
			message.getOut().write("\r\n");
			fileLine = fileReader.readLine();
		}
		
		fileReader.close();
		message.closeHttpResponse();

	}


	private BufferedReader initializeBufferedFileReader(String uri)
			throws FileNotFoundException {
		File file = new File( "web" + uri );
		
		if ( !file.exists() ) {
			throw new FileNotFoundException();
		}
		
		
		InputStreamReader inreader = new InputStreamReader( new FileInputStream(file), Charset.forName("UTF-8") );
		BufferedReader fileReader = new BufferedReader( inreader );
		
		return fileReader;
	}


	private HttpMessage initializeMessage(Socket clientSocket, String uri)
			throws IOException {
		String contentType = null;
		
		
		String extension = uri.substring( uri.lastIndexOf("."), uri.length() );	
		contentType = MemoServerConfigurator.getInstance().getContentType(extension);
		contentType += "; charset=utf-8";
		
		HttpMessage message = new HttpMessage();
		message.addHeader("Content-type", contentType);
		 
		message.sendResponseHeader( clientSocket );
		return message;
	}

	
	
}
