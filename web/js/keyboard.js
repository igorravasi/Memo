/*
 * EmojiKeyboardPrinter
 */

function keyboard(emoji){
	//TODO: Posizionare nella posizione del cursore
	document.getElementById("sequenza").value += emoji;
	}

function writeTheKeyboard(content) {
	document.getElementById("emojikeyboard").innerHTML=content;
}

function loadKeyboard(){
	
	//TODO: If (tastiera non c'è ancora) then scrivila altrimenti, se c'è ed è nascosta mostrala.
	//Nasconderla dopo l'invio
	writeTheKeyboard(''+
	'<button class="emoji" onclick="keyboard(\'😄\');">😄</button>'+
	'<button class="emoji" onclick="keyboard(\'😆\');">😆</button>'+
	'<button class="emoji" onclick="keyboard(\'😉\');">😉</button>'+
	'<button class="emoji" onclick="keyboard(\'😊\');">😊</button>'+
	'<button class="emoji" onclick="keyboard(\'😃\');">😃</button>'+
	'<button class="emoji" onclick="keyboard(\'😁\');">😁</button>'+
	'<button class="emoji" onclick="keyboard(\'😅\');">😅</button>'+
	'<button class="emoji" onclick="keyboard(\'😂\');">😂</button>'+
	'<button class="emoji" onclick="keyboard(\'😇\');">😇</button>'+
	'<button class="emoji" onclick="keyboard(\'😈\');">😈</button>')
	
}