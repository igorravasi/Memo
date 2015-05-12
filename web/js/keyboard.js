/*
 * EmojiKeyboardPrinter
 */

window.onload=writeTheKeyboard();

function keyboard(emoji){
	//TODO: Posizionare nella posizione del cursore
	document.getElementById("sequenza").value += emoji;
	}

function writeTheKeyboard() {
	unLoadKeyboard();
	document.getElementById("emojikeyboard").innerHTML=''+
	'<button class="emoji" onclick="keyboard(\'😄\');">😄</button>'+
	'<button class="emoji" onclick="keyboard(\'😆\');">😆</button>'+
	'<button class="emoji" onclick="keyboard(\'😉\');">😉</button>'+
	'<button class="emoji" onclick="keyboard(\'😊\');">😊</button>'+
	'<button class="emoji" onclick="keyboard(\'😃\');">😃</button>'+
	'<button class="emoji" onclick="keyboard(\'😁\');">😁</button>'+
	'<button class="emoji" onclick="keyboard(\'😅\');">😅</button>'+
	'<button class="emoji" onclick="keyboard(\'😂\');">😂</button>'+
	'<button class="emoji" onclick="keyboard(\'😇\');">😇</button>'+
	'<button class="emoji" onclick="keyboard(\'😈\');">😈</button>';
}
function unLoadKeyboard(){
	document.getElementById("emojikeyboard").style.display= "none";
}
function loadKeyboard(){
	
	document.getElementById("emojikeyboard").style.display= "block"
	
}