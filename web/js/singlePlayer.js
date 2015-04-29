
var playId = null;
var xmlHttp = null;



function start(){
	loadPlayId();
	var response = loadServerResponse("");
	var command = getCommand(response);
	var message = getMessage(response);
	
	doTheRightThing(command,message);
}


function showLoser(message){
	if (message == 1) {
		writeTheGame("Hai perso al primo colpo. Sembra che per te sia importante allenare la memoria, continua a giocare!");
	} else if (message == 2) {
		writeTheGame("La tua memoria non &egrave; delle peggiori, ma neanche delle migliori! Dai, riprova e diventa un maestro della memoria");
	} else {
		writeTheGame("Congratulazioni! hai superato " + (message.trim()-1) + " round, riprova e supera te stesso!");
	}
	
}

function showError(message) {
	writeTheGame("Si &egrave; verificato il seguente errore: <br/>" + message + "<br/> Per favore riprova a giocare.");
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
	"Dai un'occhiata alla sequenza!, hai solo <span id='countDown'>7</span> secondi! -><span id='sequence'></span>");

	document.getElementById("sequence").innerHTML=sequence;
	var timer = setInterval(function() {
	var time = document.getElementById("countDown");
	if (time.innerHTML > 1) {
		time.innerHTML = time.innerHTML -1;
	}else {
		window.clearInterval(timer);
		document.getElementById("sequence").innerHTML="";
		time.innerHTML = 0;
		document.getElementById("startButton").innerHTML = "Riprova";
		readSequence();
		
	}
	},1000);
}

function validate(){
	
	
	writtenSequence = document.getElementById("sequenza").value;
	writtenSequence = replaceAll(" ","+",writtenSequence);
	writtenSequence = "?S=" + writtenSequence;
	var response = loadServerResponse(writtenSequence);
	var command = getCommand(response);
	var message = getMessage(response);
	
	doTheRightThing(command, message);
	
	
}

function readSequence(){
	writeTheGame('<input type="text" id="sequenza" name="S" value=""><br/><br/><input type="button" class="btn" value="Controlla" onClick="validate()">');

	var timer = setInterval(function() {
		showError("Tempo scaduto");
	},1000*60*2)
}

function loadPlayId(){
	
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 playId = xmlHttp.responseText;   
	    }else{
	    	playId = null;
		}
	}		     
	httpGet("singleplayer",cfunc);
}



function httpGet(theUrl)
{
   
    xmlHttp = new XMLHttpRequest();
    
	if (xmlHttp == null || xmlHttp == undefined)
	  {//IE6
	  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
    
	xmlHttp.onreadystatechange=cfunc;
    xmlHttp.open( "GET", theUrl, false );
    xmlHttp.send( null );  
 
}


function loadServerResponse(parameters) {
	
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 response = xmlHttp.responseText;   
	    }else{
	    	response = null;
		}
	}		 

	httpGet("singleplayer/"+playId+parameters,cfunc);
	return response;
}


function writeTheGame(content) {
	document.getElementById("game").innerHTML=content;
}
