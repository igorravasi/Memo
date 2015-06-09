
function loadServerResponse( postContent ) {
	 

	var url = "notifiche";
	if ( playId!=null ) {
		url += "/" + playId;
	}
	response = request(url, postContent);
	return response;
}


function doTheRightThing(response) {

	var command = response.substring(0,response.indexOf(":"));
	var message = response.substring(response.indexOf(":")+1);
	

	switch (command) {
	case "N":
		writeTable(message);
		
		break;
	case "E":
	default:
		writeAMessage(getErrorMessage(message));
		break;
	}
	
	
}


function writeTable(text){
	
}
