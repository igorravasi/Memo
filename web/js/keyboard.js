
function keyboard(id, emoji){
	
	textArea = document.getElementById(id);
	  if (document.selection) {
		textArea.focus();
	    var sel = document.selection.createRange();
	    sel.text = word;
	    sel.select();
	  } else if (textArea.selectionStart || textArea.selectionStart == '0') {
	    var start = textArea.selectionStart;
	    var end = textArea.selectionEnd;
	    var scroll = textArea.scrollTop;
	    textArea.value = textArea.value.substring(0, start) + emoji + textArea.value.substring(end, textArea.value.length);
	    textArea.focus();
	    textArea.selectionStart = start + emoji.length;
	    textArea.selectionEnd = start + emoji.length;
	    textArea.scrollTop = scroll;
	  } else {
		  textArea.value += emoji;
		  textArea.focus();
	  }
	
}


function unLoadKeyboard(){
	document.getElementById("emojikeyboard").style.display= "none";
}

function loadKeyboard(){
	document.getElementById("emojikeyboard").style.display= "block"
}