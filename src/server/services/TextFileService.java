package server.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Map;

import server.IService;
import server.basics.HttpMessage;
import server.basics.HttpRequest;


//TODO: TextFileService e BinaryFileService sono entrambi, nella logica, FileServici, dovrebbero 
//derivate da una classe comuna FileService!
public class TextFileService implements IService {

	private Map<String, String> contentTypes;
	
	@Override
	public void sendHttpResponse(Socket clientSocket, HttpRequest request) throws IOException{

		
		BufferedReader fileReader;
		String uri = request.getUri();
		
		//TODO: uri = home settata dall'esterno
		if (uri.equalsIgnoreCase("/")) {
			uri = "/MyMemo.html";
		}
		
		File file = new File("web"+uri);
		
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		
//		FileReader fr = new FileReader(file);
//		fileReader = new BufferedReader(fr);
		
//		fileReader = new BufferedReader(new FileReader(file));
		
		
		InputStreamReader inreader = new InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8"));
		fileReader = new BufferedReader(inreader);
		
		String contentType = null;
		
		
		String extension = uri.substring( uri.lastIndexOf("."), uri.length() );	
		contentType = contentTypes.get(extension) + "; charset=utf-8";
		
		HttpMessage message = new HttpMessage();
		message.setContentType( contentType );
		 
		message.sendResponseHeader( clientSocket );

		String fileLine = fileReader.readLine();
		while(fileLine != null){
			message.getOut().write(fileLine + "\r\n");
			fileLine = fileReader.readLine();
			
		}
		
		fileReader.close();
		message.closeHttpResponse();

	}

	
	public void setContentTypeMap(Map<String, String> contentTypes){
		
		this.contentTypes = contentTypes;
	}
	
}
