package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;


public class testingClientForSinglePlayer {

	public static final String baseUrl = "http://127.0.0.1:4444/singleplayer";
	
	
	public static String send(String path, String post) throws IOException{
		if (path != null && path.length() > 0) {
			path = "/" + path;
		}
		URL url = new URL(baseUrl + path);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if (post == null || post.length() == 0) {
			con.setRequestMethod("GET");
		}else {
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), Charset.forName("UTF-8").newEncoder());
			writer.write(post);
			writer.flush();
			writer.close();
		}
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),Charset.forName("UTF-8")));
		
		String line;
		StringBuffer response = new StringBuffer();
		
		line = in.readLine();
		while ( line != null ) {
			
			response.append(line);
			line = in.readLine();
		}
		
		in.close();
		
 
		return response.toString();
	}
	
	public static void main(String[] args) throws IOException {
		
		String playid = "";
		//Inizio 500 partite singleplayer e stampo il playid ricevuto
		for (int i = 0; i < 500; i++) {
			playid = send("","");
			System.out.println("Playid : " + playid);
		}
		
		//Estrapolo la sequenza ricevuta, dando per scontato che sia S:....
		String received = send(playid, "");
		
		
		if (!received.startsWith("S:")) {
			System.err.println("errore");
			return;
		}
		
		
		byte[] bytes = received.substring(2).getBytes(Charset.forName("UTF-8"));
		
		String outSequence = "S:";
		
		for (int i = 0; i < bytes.length; i++) {
			outSequence += "%" + String.format("%02X", bytes[i]);
		}
		

		String result = send(playid, outSequence);


		if (!result.startsWith("S:")) {

			//o errore o partita persa
			System.err.println(result);
			return;
		} 
		
		System.out.println("primo round ok");
		bytes = result.substring(2).getBytes(Charset.forName("UTF-8"));
		
		bytes[3] = (byte) 12;
		outSequence = "S:";
		
		for (int i = 0; i < bytes.length; i++) {
			outSequence += "%" + String.format("%02X", bytes[i]);
		}
		
		result = send(playid, outSequence);
		
		if (result.startsWith("S:")) {
			System.out.println("passato");
		} else if (result.startsWith("L:")) {
			System.err.println("perso");
		}
		
	}
}
