package server.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MemoServerConfigurator {

	private static final String dir = "config/";
	private static final String valuesPath = dir + "values.cfg";
	private static final String contentTypesPath = dir + "content-types.cfg";
	
	private static final String delim = ":/t";
	
	private static MemoServerConfigurator  configurator = null;
	private Map<String, String> contentTypes = new HashMap<String, String>();
	private Map<String, String> values = new HashMap<String, String>();
	
	
	//Singleton: costruttore private
	private MemoServerConfigurator(){
		
	}
	
	
	public static MemoServerConfigurator getInstance(){
		if (configurator == null) {
			configurator = new MemoServerConfigurator();
		} 
		
		return configurator;
	}
	
	private void reload(String path, Map<String, String> mappa){
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			
			String line = reader.readLine();
			
			while (line != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, delim);
				
				String key = tokenizer.nextToken();
				String value = tokenizer.nextToken("");
				
				mappa.put(key, value);
				
				line = reader.readLine();
			}
			
			reader.close();
		}
		
		//Non faccio nulla per gestire l'eccezione, al di là del log, perchè la miglior cosa da fare è lasciare
		//i valori cosi come erano prima della chiamata 
		  catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void reloadContentTypes(){
		//TODO: check ultima modifica
		reload(contentTypesPath, contentTypes);
	}

	private void reloadValues(){
		
		//TODO: check ultima modifica
		
		reload(valuesPath, values);
	}
	public String getContentType(String extension){
		reloadContentTypes();
		return contentTypes.get(extension);
	}
	
	public String getValue(String name){
		reloadValues();
		return values.get(name);
	}

}
