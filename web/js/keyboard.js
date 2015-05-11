/**
 * EmojiKeyboardPrinter
 */

function keyboard(emoji){
	//TODO: Posizionare nella posizione del cursore
	document.getElementById("sequenza").value += emoji;
	}

function writeTheKeyboard() {
	//TODO: In lontano futuro: supporto anche ai browser che non supportano emoji (in quel caso vanno caricate come immagini)
	var baseCodePoint = "😄".codePointAt(0);
//	var baseCodePoint = "😄".charCodeAt(0);
		
	var keyboard = "";
	for (var int = 0; int < 10; int++) {
		var code = baseCodePoint + int;
		var emoji = String.fromCodePoint(code)+"";
		keyboard += '<button class="emoji" onclick="keyboard(\'' + emoji + '\');">' + emoji + '</button>';
	}
//	keyboard ="";
//	for (var int = 120000; int < 130000; int++) {
//		keyboard += int + ": " + String.fromCharCode(int) + "\n";
//	}
//	
	document.getElementById("emojikeyboard").innerHTML=keyboard;
}

function loadKeyboard(){
	
	//TODO: If (tastiera non c'è ancora) then scrivila altrimenti, se c'è ed è nascosta mostrala.
	//Nasconderla dopo l'invio
	
	writeTheKeyboard();
	
}