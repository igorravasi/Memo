/*
 * 	Motore di gioco lato client, per la modalità SinglePlayer
 */

var playId = null;
var xmlHttp = null;
var intervalTimer;

function start(){
	loadPlayId();
	document.getElementById("start_button").innerHTML="Restart";
	
	document.getElementById("sequence_container").innerHTML = getSequenceHTML();
	
	window.clearInterval(intervalTimer);
	var response = loadServerResponse( null );
	var command = getCommand(response);
	var message = getMessage(response);
	
	doTheRightThing(command,message);
}


function showLoser(message){
	
	writeAMessage(getGameOverMessage(message));
	
}

function showError(message) {
	writeAMessage(getErrorMessage(message));
}

function doTheRightThing(command,message) {


	switch (command) {
	case "S":
		showSequence(message);
		break;
	case "L":
		showLoser(message);
		break;
	case "E":
	default:
		showError(message);
		break;
	}
}


function getMessage(response) {
	
	return response.substring(response.indexOf(":")+1);
}

function getCommand(response) {
	
	return response.substring(0,response.indexOf(":"));
}

function showSequence(sequence) {

	
	
	document.getElementById("sequence").innerHTML=sequence;
	display("sequence_container",true);
	display("message",false);
	
	var timerText = document.getElementById("countDown");
	timerText.innerHTML = 7;
	
	intervalTimer = setInterval(function() {

	//Per evitare che se si preme su riprova il timer continui a girare
	if (document.getElementById("countDown")) {
		if (timerText.innerHTML > 1) {
			
			timerText.innerHTML = timerText.innerHTML -1;
		}else {
			window.clearInterval(intervalTimer);
			readSequence();
			
		}
	} else {
		window.clearInterval(intervalTimer);
		
	}
	
	},1000);
}

function validate(){
	
	
	display("controls",false);
	writtenSequence = document.getElementById("sequenza").value;

	writtenSequence = "S:" + writtenSequence;
	var response = loadServerResponse(writtenSequence);
	var command = getCommand(response);
	var message = getMessage(response);
	
	doTheRightThing(command, message);
	
}



function readSequence(){
	display("sequence_container",false);
	display("controls",true);
	
}


function display(elementId, boolDisplay){
	var element = document.getElementById(elementId);
	
	var toBeSet = boolDisplay ? "visibile" : "nascosto";
	var toBeDeleted = !boolDisplay ? "visibile" : "nascosto";
	
	var elementClasses = document.getElementById(elementId).className;
	var regex = new RegExp("\s*"+toBeDeleted+"\s*");
	elementClasses = elementClasses.replace(regex,"");
	if (!elementClasses.match("\s*"+toBeSet+"\s*")) {
		elementClasses += " " + toBeSet;
	}
	
	element.className = elementClasses;
	
	
}





function loadPlayId(){
	
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 playId = xmlHttp.responseText;   
	    }else{
	    	playId = null;
		}
	}		     
	httpGet("singleplayer", null );
}



function httpGet(theUrl, postContent){
   
    xmlHttp = new XMLHttpRequest();
    
	if (xmlHttp == null || xmlHttp == undefined)
	  {//IE6
	  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
    
	xmlHttp.onreadystatechange=cfunc;
	
	var method = postContent == null ? "GET" : "POST";
    xmlHttp.open( method, theUrl, false );
      
    xmlHttp.send( encodeURI(postContent) );
 
}


function loadServerResponse( postContent ) {
	
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 response = xmlHttp.responseText;   
	    }else{
	    	response = null;
		}
	}		 

	var url = "singleplayer";
	if ( playId!=null ) {
		url += "/" + playId;
	}
	httpGet(url, postContent);
	return response;
}



function writeAMessage(content) {
	document.getElementById("message").innerHTML=content;
	display("sequence_container",false);
	display("message",true);
}

function keyboard(emoji){
	document.getElementById("sequenza").value += emoji.innerHTML;
}



