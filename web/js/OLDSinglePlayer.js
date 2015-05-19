/*
 * 	Motore di gioco lato client, per la modalità SinglePlayer
 */

var playId = null;
var xmlHttp = null;
var intervalTimer;
var factorySeconds = 7;


function start(){
	loadPlayId();
	
	document.getElementById("start_button").innerHTML="Restart";
	document.getElementById("sequence_container").innerHTML = getSequenceHTML();
	display("message",false);
	
	window.clearInterval(intervalTimer);
		
	doTheRightThing( loadServerResponse( null ));
}


function doTheRightThing(response) {

	var command = response.substring(0,response.indexOf(":"));
	var message = response.substring(response.indexOf(":")+1);
	
	switch (command) {
	case "S":
		showSequence(message);
		break;
	case "L":
		writeAMessage(getGameOverMessage(message));
		break;
	case "E":
	default:
		writeAMessage(getErrorMessage(message));
		break;
	}
}



function showSequence(sequence) {

	document.getElementById("sequence").innerHTML=sequence;
	display("sequence_container",true);
	
	
	var timerText = document.getElementById("countDown");
	timerText.innerHTML = factorySeconds;
	
	intervalTimer = setInterval(function() {

		if (document.getElementById("countDown")) {
			if (timerText.innerHTML > 1) {
				
				timerText.innerHTML = timerText.innerHTML -1;
			}else {
				window.clearInterval(intervalTimer);
				var numberOfChars = utf_length( trim(sequence) );
				readSequence(numberOfChars);
				
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
	doTheRightThing(response);
	
}



function readSequence(numberOfChars){

	display("sequence_container",false);
	document.getElementById("sequence").innerHTML="";
	
	generateBoxes(numberOfChars);
	
	display("controls",true);
	
}



function generateBoxes(n){
	
	
	if (document.getElementById("box_container")) {

		document.getElementById("box_container").parentNode.removeChild(document.getElementById("box_container"));
	}
	
	
	var boxContainer = document.createElement("div");
	var controls = document.getElementById("controls");
	boxContainer.setAttribute("id","box_container");
	
	controls.insertBefore(boxContainer,controls.childNodes[0]);
	
	for (var i = 0; i < n; i++) {
		
		var box = document.createElement("span");
		boxContainer.setAttribute("id","box["+i+"]");
		boxContainer.setAttribute("onclick","setFocusedBox(this)");
			
		box.innerHTML = "prova"+i;
		boxContainer.appendChild(box);
		
	}
	
}



function loadPlayId(){
	
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 playId = xmlHttp.responseText;   
	    }else{
	    	playId = null;
		}
	}		     
	request("singleplayer", null );
}



function request(theUrl, postContent){
   
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
	request(url, postContent);
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