
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
			
			document.getElementById("stringStart").innerHTML = "Riparti";
			readSequence();
			
		}
	} else {
		window.clearInterval(intervalTimer);
		
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
	writeTheGame('<center><input type="text" class="form-control" style="width: 20%" id="sequenza" name="S" value=""></center><br/><br/><input type="button" class="btn btn-primary btn-lg" value="Controlla" onClick="validate()">');

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

function trim(str){
	return str.replace(/^\s+|\s+$/g,'');
}
function replaceAll(find, replace, str) {
	  return str.replace(new RegExp(find, 'g'), replace);
	}

function writeTheGame(content) {
	document.getElementById("game").innerHTML=content;
}
