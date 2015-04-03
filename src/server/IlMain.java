package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;
import server.ServerParameters;

public class IlMain {

	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub

//		System.out.println("Prova SVN  ");
//		System.out.println("igorravasi On");
//		System.out.println(" ScrumBoy ON ");
//		System.out.println(" ghia16 ON ");
//		System.out.println(" bipavesi ON ");
//		System.out.println("edoardo OFF");
		
		try {
			ServerSocket socket=new ServerSocket(ServerParameters.PORT);
			
			Socket clientSocket = socket.accept();
			
			BufferedReader in = new BufferedReader(
				new InputStreamReader(
						clientSocket.getInputStream()
				)	
			);
			
			String line = in.readLine();
			
			LinkedList<String> lines=new LinkedList<String>();
			while(line!=null){
				System.out.println(line);
				lines.add(line);
				line = in.readLine();
				if(line.length()==0){
					line=null;
				}
			}

			//in.close();
			
			OutputStreamWriter out = new 
				OutputStreamWriter(
						clientSocket.getOutputStream(),
						Charset.forName("UTF-8").newEncoder()
				);
		
			out.write("HTTP/1.1 200 OK\n");
			out.write("Date: Tue, 17 Mar 2014 14:47:00\n");
			out.write("Content-Type: text/html; charset=utf-8\n");
			out.write("\n");
				BufferedReader fileReader=new BufferedReader(
					new FileReader("web/index01.html")
				);
				String fileLine=fileReader.readLine();
				while(fileLine!=null){
					out.write(fileLine+"\n");
					fileLine=fileReader.readLine();
				}
			out.write("\n");
			
			out.close();
			clientSocket.close();
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
