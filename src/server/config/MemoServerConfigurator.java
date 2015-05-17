package server.config;

import java.util.HashMap;
import java.util.Map;

public class MemoServerConfigurator {

	//Singleton
	private static MemoServerConfigurator  configurator = null;
	
	private Map<String, String> contentTypes = new HashMap<String, String>();
	
	private MemoServerConfigurator(){
		
	}
	
	public static MemoServerConfigurator getInstance(){
		if (configurator == null) {
			configurator = new MemoServerConfigurator();
		} 
		
		return configurator;
	}
	
	
	public String addContentType(String extension, String contentType){
		return contentTypes.put(extension, contentType);
	}
	
	public String getContentType(String extension){
		return contentTypes.get(extension);
	}
}
