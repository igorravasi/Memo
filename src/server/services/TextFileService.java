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

//TextFileService e BinaryFileService sono entrambi, nella logica, FileService, dovrebbero 
//derivare da una classe comune FileService!
//Update: tuttavia visto che le linee di codice condivise non sono contigue non so se 
//la presenza di esse giustificherebbe un refactoring del genere.

public class TextFileService implements IService {

	private static final String includeRegEx = "#{3}.+#{3}";	
	private static final String singleIncldueDelimiter = "###";
	private static final String includeName = "include_dir";
	
	private MemoServerConfigurator configurator = MemoServerConfigurator.getInstance();
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException{

		String uri = request.getUri();
		
		if ( uri.equalsIgnoreCase("/") ) {
			uri = configurator.getValue("home_page");
		}
		
		BufferedReader fileReader;
		try {
			fileReader = initializeBufferedFileReader(uri);
		} catch (FileNotFoundException e1) {
			fileReader = initializeBufferedFileReader("/Errore.html");
		}
		
		HttpMessage message = initializeMessage(clientSocket, uri);

		copyLines(fileReader, message);
		
		fileReader.close();
		message.closeHttpResponse();

	}


	private void copyLines(BufferedReader fileReader, HttpMessage message)
			throws IOException {
		String fileLine = fileReader.readLine();
		while( fileLine != null ){
			if (fileLine.trim().matches(includeRegEx)) {
				
				try {
					String path = new StringTokenizer(fileLine.trim(),singleIncldueDelimiter).nextToken();
					path = "web" + configurator.getValue(includeName) + path.trim();
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
		
		String extension = uri.substring( uri.lastIndexOf("."), uri.length() );	
		String contentType = configurator.getContentType(extension);
		contentType += "; charset=utf-8";

		HttpMessage message = new HttpMessage();
		message.addHeader("Content-type", contentType);
		 
		message.sendResponseHeader( clientSocket );
		return message;
	}

	
	
}
