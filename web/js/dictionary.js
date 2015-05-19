/**
 * 
 */

function getErrorMessage(error){
	
	var head = "<h3>Si &egrave; verificato il seguente errore: <br/>";
	var tail = "<br/> Per favore riprova a giocare.</h3>";
	
	return head + error + tail;
}

function getGameOverMessage(round){
	
	return "";
}