/**
 * 
 */



function submitLogin(button) {
	
	var stringToSubmit = "";
	var elementsToSubmit = [
			document.getElementById("id_user"),
			document.getElementById("id_password"),
			button
	]
	
	for (var int = 0; int < elementsToSubmit.length; int++) {
		if (int > 0) {
			stringToSubmit += "&";
		}
		stringToSubmit += elementsToSubmit[int].name + "=" + elementsToSubmit[int].value;
	}
	
	
	var logResponse = request("login", stringToSubmit);
	
	document.getElementById("login_message").innerHTML = getLogMessage(checkLogResponse(logResponse));
}


function checkLogResponse(response){
	
	if (response.contains("Logged")) {
		return true;
	}
	return false;
}
