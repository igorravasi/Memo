/**
 * 
 */

var playId = null;
var xmlHttp = null;
var contentContainer = document.getElementById("game");


function start(){
	loadPlayId();
	response = loadServerResponse();
	command = getCommand(response);
	message = getMessage(response);
	
	showSequence(message);
	
	
}


function getMessage(response) {
	
	return response.substring(response.indexOf(":")+1);
}

function getCommand(response) {
	
	return response.substring(0,response.indexOf(":"));
}

function showSequence(sequence) {
	document.getElementById("game").innerHTML="" +
	"Dai un'occhiata alla sequenza!, hai solo <span id='countDown'>7</span> secondi! -><span id='sequence'></span>";

	document.getElementById("sequence").innerHTML=sequence;
	timer = setInterval(function() {
	time = document.getElementById("countDown");
	if (time.innerHTML > 1) {
		time.innerHTML = time.innerHTML -1;
	}else {
		window.clearInterval(timer);
		document.getElementById("sequence").innerHTML="";
		time.innerHTML = 0;
		document.getElementById("startButton").innerHTML = "Restart";
		readSequence();
		
	}
	},1000);
}

function readSequence(){
	document.getElementById("game").innerHTML = '<form id="send" name="sequenza" method="get" action="singleplayer/'+playId+'"><input type="text" id="sequenza" name="S" value=""><br><input type="submit" value="Invia"></form>';
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
	  {// code for IE6, IE5
	  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
    
	xmlHttp.onreadystatechange=cfunc;
    xmlHttp.open( "GET", theUrl, false );
    xmlHttp.send( null );
  
    
    
}


function loadServerResponse() {
	sequence = null;
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 response = xmlHttp.responseText;   
	    }else{
	    	response = null;
		}
	}		     
	httpGet("singleplayer/"+playId,cfunc);
	return response;
}


//function loadSequence() {
//	sequence = null;
//	cfunc = function(){
//		if (xmlHttp.readyState==4 && xmlHttp.status==200){
//	    	 sequence = xmlHttp.responseText;   
//	    }else{
//	    	sequence = null;
//		}
//	}		     
//	httpGet("singleplayer/"+playId,cfunc);
//	return sequence;
//}