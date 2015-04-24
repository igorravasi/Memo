package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class ThreadForSingleClientConnection extends Thread implements Runnable{

	private Socket clientSocket;
	private ServerSocket socket;

	

	@Override
	public void run() {

		try {
		
			LinkedList<String> lines=new LinkedList<String>();
			
			getInputLines(lines);

		
			OutputStreamWriter out = initializeOutput();
			writeOutput(out);
			out.close();
			
			clientSocket.close();
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeOutput(OutputStreamWriter out)
			throws FileNotFoundException, IOException {
		BufferedReader fileReader=new BufferedReader(
			new FileReader("web/index01.html")
		);
		String fileLine=fileReader.readLine();
		while(fileLine!=null){
			out.write(fileLine+"\n");
			fileLine=fileReader.readLine();
		}
		fileReader.close();
		out.write("\n");
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

	private void getInputLines(LinkedList<String> lines) throws IOException {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						clientSocket.getInputStream()
				)	
			);
			
			String line = in.readLine();
			
			
			while(line!=null){
				System.out.println(line);
				lines.add(line);
				line = in.readLine();
				if(line.length()==0){
					line=null;
				}
			}
			//in.close();
	}

	public ThreadForSingleClientConnection setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
		return this;
	}

	public ThreadForSingleClientConnection setSocket(ServerSocket socket) {
		this.socket = socket;
		return this;
	}

}


