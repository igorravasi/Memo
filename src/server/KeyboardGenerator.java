package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;


/**
 * Creo la tastiera per poter inserire da browser la sequenza di emoji desiderata
 *
 */
public class KeyboardGenerator {

	/**
	 * Carico nel file keyboard.inc l'html per la tastiera di emoticons. 
	 * 
	 * @param args
	 */
	
	public static void main(String[] args)  {
		
		
		File file = new File("web/include/keyboard.inc");
		
		byte leftBound = (byte) 0x81;
		byte[] emojBytes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, leftBound};

		String templatePattern = "<button class=\"emoji\" onclick=\"keyboard(this);\">###emoji###</button>\r\n";
		
		
		try {
			
			if (!file.exists()){
				file.createNewFile();
			}


			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8").newEncoder());
			
			
			
			for (int i = 0; i < 10; i++) {
				emojBytes[emojBytes.length - 1] = (byte) (leftBound + i);
				String emoji = new String(emojBytes, Charset.forName("UTF-8"));

				
				String line = templatePattern.replaceAll("###emoji###", emoji);
				
				writer.write(line);
				
			}
			
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
