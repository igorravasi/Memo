/*
 * 	Motore di gioco lato client, per la modalità SinglePlayer
 */

var playId = null;
var intervalTimer;
var timeoutTimer = null;
var factorySeconds = 7;
var timeoutMilliSeconds = 2*60*1000 - 2*1000;  //Sottraggo 2 secondi perchè per ritardi sulla linea e sui buffer di I/O il timer sul server potrebbe essere già scaduto
var lastBox = 0;
var isValidable = false;

display("start_div",true);
setShortcuts();


function start(){
	playId = request("singleplayer", null );
	
	display("controls",false);
	document.getElementById("start_button").innerHTML="Restart";
	document.getElementById("sequence_container").innerHTML = getSequenceHTML();
	display("message",false);
	
	window.clearInterval(intervalTimer);
		
	doTheRightThing( loadServerResponse( null ));
}


function doTheRightThing(response) {

	var command = response.substring(0,response.indexOf(":"));
	var message = response.substring(response.indexOf(":")+1);
	
	if (timeoutTimer != null) {
		window.clearInterval(timeoutTimer);
	}
	
	switch (command) {
	case "S":
		timeoutTimer = setInterval(function (){
			writeAMessage(getErrorMessage("Tempo scaduto"));
		}, timeoutMilliSeconds);
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

