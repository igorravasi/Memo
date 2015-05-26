
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

