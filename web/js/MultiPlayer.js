/*
 * 	Motore di gioco lato client, per la modalità MultiPlayer (fork del singleplayer)
 */

var playId = null;
var intervalTimer;

var factorySeconds = 7;

var lastBox = 0;
var isValidable = false;

display("start_button",true);
setShortcuts();


function start(){
	playId = request("multiplayer", null );
	
	display("controls",false);
	document.getElementById("start_button").innerHTML="Nuova sfida";
	document.getElementById("sequence_container").innerHTML = getSequenceHTML();
	display("message",false);
	
	window.clearInterval(intervalTimer);
		
	doTheRightThing( loadServerResponse( null ));
}

function loadServerResponse( postContent ) {
	 

	var url = "multiplayer";
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
	case "S":
		showSequence(message);
		
		break;
	case "L":
		writeAMessage(getMultiGameOverMessage(message));
		
		break;
	case "W":
		writeAMessage(getGameCompleted(message));
		break;
	case "E":
	default:
		writeAMessage(getErrorMessage(message));
		break;
	}
}


function validate(){
	if (!isValidable) {
		return;	
	}
	
	display("controls",false);
	writtenSequence = "";
	
	var boxes = document.getElementById("box_container").childNodes;
	
	for (var i = 0; i < boxes.length; i++) {
		writtenSequence += boxes[i].value;
	}

	writtenSequence = "S:" + writtenSequence;

	var response = loadServerResponse(writtenSequence);
	isValidable = false;
	doTheRightThing(response);
	
}



function readSequence(numberOfChars){

	display("sequence_container",false);
	document.getElementById("sequence").innerHTML="";
	
	generateBoxes(numberOfChars);
	
	display("controls",true);
	
}



function keyboard(emoji){
	
	var char = emoji.innerHTML;
	var box = document.getElementById("box"+lastBox);
	
	box.value = emoji.innerHTML;
	var nextBox = document.getElementById("box"+(lastBox + 1));
	if (nextBox) {
		lastBox++;
		nextBox.focus();
	}
}


function loadServerResponse( postContent ) {
	 

	var url = "multiplayer";
	if ( playId!=null ) {
		url += "/" + playId;
	}
	response = request(url, postContent);
	return response;
}
