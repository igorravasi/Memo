//
//Lettura delle sequenze
//

function readSequence(sequence){
	document.getElementById("game").innerHTML = null;
	
	for(i=0;i<sequence.length; i++){
		writeTheReader('<center><input type="text" class="form-control" style="width: 20%" id="sequenza" name="S" value=""></center>');
	}
	loadKeyboard();
	
//	var timer = setInterval(function() {
//		showError("Tempo scaduto");
//	},1000*60*2)
}


function writeTheReader(content) {
	document.getElementById("game").innerHTML = document.getElementById("game").innerHTML + content;
}