/*
 * EmojiKeyboardPrinter
 */

window.onload=writeTheKeyboard();

function keyboard(emoji){
	//TODO: Posizionare nella posizione del cursore
	if (document.selection) {
		sequenza.focus();
		sel = document.selection.createRange();
		sel.text = emoji;
		}
		else if (sequenza.selectionStart || sequenza.selectionStart == '0') {
		var startPos = sequenza.selectionStart;
		var endPos = sequenza.selectionEnd;
		sequenza.value = sequenza.value.substring(0, startPos)
		+ emoji
		+ sequenza.value.substring(endPos, sequenza.value.length);
		} else {
		sequenza.value += emoji;
		}
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