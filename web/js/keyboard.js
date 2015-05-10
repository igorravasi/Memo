/**
 * EmojiKeyboardPrinter
 */

function keyboard(emoji){
	document.getElementById("sequenza").value += emoji;
	}

function writeTheKeyboard(content) {
	document.getElementById("emojikeyboard").innerHTML=content;
}

function loadKeyboard(){
	writeTheKeyboard(''+
	'<button class="emoji" onclick="keyboard("😄");">😄</button>'+
	'<button class="emoji" onclick="keyboard("😆");">😆</button>'+
	'<button class="emoji" onclick="keyboard("😉");">😉</button>'+
	'<button class="emoji" onclick="keyboard("😊");">😊</button>'+
	'<button class="emoji" onclick="keyboard("😃");">😃</button>'+
	'<button class="emoji" onclick="keyboard("😁");">😁</button>'+
	'<button class="emoji" onclick="keyboard("😅");">😅</button>'+
	'<button class="emoji" onclick="keyboard("😂");">😂</button>'+
	'<button class="emoji" onclick="keyboard("😇");">😇</button>'+
	'<button class="emoji" onclick="keyboard("😈");">😈</button>')
}