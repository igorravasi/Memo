/*
 * 	Motore di gioco lato client, per la modalità SinglePlayer
 */

var playId = null;
var xmlHttp = null;
var intervalTimer;

function start(){
	loadPlayId();
	document.getElementById("start_button").innerHTML="Restart";
	window.clearInterval(intervalTimer);
	var response = loadServerResponse( null );
	var command = getCommand(response);
	var message = getMessage(response);
	
	doTheRightThing(command,message);
}


function showLoser(message){
	if (message == 1) {
		writeTheGame("<h3>Hai perso al primo colpo. Sembra che per te sia necessario allenare la memoria, continua a giocare!</h3>");
	} else if (message == 2) {
		writeTheGame("<h3>La tua memoria non &egrave; delle peggiori, ma neanche delle migliori! Dai, riprova e diventa un maestro della memoria</h3>");
	} else {
		writeTheGame("<h3>Congratulazioni! hai superato " + (message.trim()-1) + " round, riprova e supera te stesso!</h3>");
	}
	
}

function showError(message) {
	writeTheGame("<h3>Si &egrave; verificato il seguente errore: <br/>" + message + "<br/> Per favore riprova a giocare.</h3>");
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
	writeTheGame("" +
	"<h3>Dai un'occhiata alla sequenza! Hai solo <span id='countDown'>7</span> secondi! <br7><span id='sequence'></span></h3>");
	
	
	
	document.getElementById("sequence").innerHTML=sequence;
	intervalTimer = setInterval(function() {
	var timerText = document.getElementById("countDown");
	
	//Per evitare che se si preme su riprova il timer continui a girare
	if (document.getElementById("countDown")) {
		if (timerText.innerHTML > 1) {
			
			timerText.innerHTML = timerText.innerHTML -1;
		}else {
			window.clearInterval(intervalTimer);
			document.getElementById("sequence").innerHTML="";
			timerText.innerHTML = 0;
			
			document.getElementById("start_button").innerHTML = "Riparti";
			readSequence();
			
		}
	} else {
		window.clearInterval(intervalTimer);
		
	}
	
	},1000);
}

function validate(){
	
	unloadKeyboard();
	document.getElementById("controls").style.display="none";
	writtenSequence = document.getElementById("sequenza").value;

	writtenSequence = "S:" + writtenSequence;
	var response = loadServerResponse(writtenSequence);
	var command = getCommand(response);
	var message = getMessage(response);
	
	doTheRightThing(command, message);
	
}

function readSequence(){
	document.getElementById("controls").style.display="block";
	loadKeyboard();
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



function unloadKeyboard(){
	document.getElementById("emojikeyboard").style.display= "none";
}

function loadKeyboard(){
	document.getElementById("emojikeyboard").style.display= "block"
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



function writeTheGame(content) {
	document.getElementById("game").innerHTML=content;
}

function keyboard(emoji){
	document.getElementById("sequenza").value += emoji;
}



