/**
 * 
 */

writeLoginName();

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
	var logged = checkLogResponse(logResponse);
	document.getElementById("login_message").innerHTML = getLogMessage(logged);
	if (logged) {
		window.setTimeout(function(){
			clearAllMenus();
		},300);
	}
	
	
}


function checkLogResponse(response){
	
	if (trim(response) == "Logged") {
		writeLoginName();
		
		return true;
	}
	return false;
}


function writeLoginName(){
	var username = getCookie("Utente");
	if (username == "") {
		username = "Login";
		
	}
	
	document.getElementById("user_name").innerHTML = username; 
}