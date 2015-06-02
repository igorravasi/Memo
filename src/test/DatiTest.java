package test;

import java.net.MalformedURLException;
import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

public class DatiTest {
	
		public static void main(String[] argv) throws MalformedURIException, MalformedURLException{
			    	
		        URI uri = new URI("http://127.0.0.1:4444/MyMemo");

				

				System.out.println("Host: " + uri.getHost());

				System.out.println("Porta: " + uri.getPort());

				System.out.println("File: " + uri.getPath());
	
		 
	
		    }
	
		}

