/*
 * 	Motore di gioco lato client, per la modalità SinglePlayer
 */

var playId = null;
//var xmlHttp = null;
var intervalTimer;
var timeoutTimer = null;
var factorySeconds = 7;
var timeoutMilliSeconds = 2*60*1000 - 2*1000;  //Sottraggo 2 secondi perchè per ritardi sulla linea e sui buffer di I/O il timer sul server potrebbe essere già scaduto
var lastBox = 0;


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
	writtenSequence = "";
	
	var boxes = document.getElementById("box_container").childNodes;
	
	for (var i = 0; i < boxes.length; i++) {
		writtenSequence += boxes[i].value;
	}

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
		
		var input = document.createElement("input");
		input.setAttribute('type',"text");
		input.setAttribute('class',"input-emoji");
		input.setAttribute('id', "box"+i);
		input.setAttribute('size', "1");
		input.setAttribute('readonly',"readonly");
		input.setAttribute('onclick',"lastBox="+i);
		boxContainer.appendChild(input);
		
	}
	
	lastBox = 0;
	
}



function writeAMessage(content) {
	document.getElementById("message").innerHTML=content;
	display("sequence_container",false);
	display("controls", false)
	display("message",true);
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