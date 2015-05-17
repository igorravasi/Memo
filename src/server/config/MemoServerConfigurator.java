package server.config;

public class MemoServerConfigurator {

	//Singleton
	private static MemoServerConfigurator  configurator = null;
	
	private MemoServerConfigurator(){
		
	}
	
	public static MemoServerConfigurator getInstance(){
		if (configurator == null) {
			configurator = new MemoServerConfigurator();
		} 
		
		return configurator;
	}
	
	
	
}
