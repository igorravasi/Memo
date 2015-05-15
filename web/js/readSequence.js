//
//Lettura delle sequenze
//

//function readSequence(sequence){
//	document.getElementById("game").innerHTML = null;
//	writeTheReader('<form id="seq">');
//	for(i=0;i<sequence.length; i++){
//		writeTheReader('<center><input type="text" class="form-control" style="width: 20%" id="sequenza" name="S" value=""></center>');
//	}	
//	writeTheReader('</form>');
//	writeTheReader('<input type="button" class="btn btn-primary btn-lg" value="Controlla" onClick="validate()">');
//	loadKeyboard();
//	
////	var timer = setInterval(function() {
////		showError("Tempo scaduto");
////	},1000*60*2)
//}
//
//
//function writeTheReader(content) {
//	document.getElementById("game").innerHTML = document.getElementById("game").innerHTML + content;
//}



function readSequence(sequence){
	document.getElementById("game").innerHTML = null;
	
	sequence = sequence.toString();
	var form = document.createElement("form");
	form.setAttribute('id',"seq");
	form.setAttribute('class',"form-emoji");
	
	var submit = document.createElement("input");
	submit.setAttribute('type',"button");
	submit.setAttribute('value',"Submit");
	submit.setAttribute('onClick',"validate();");
	submit.setAttribute('class',"btn btn-primary btn-lg");
	
	for(var i=0; i<sequence.length; i++){
		var input = document.createElement("input");
		input.setAttribute('type',"text");
		input.setAttribute('class',"input-emoji");
		input.setAttribute('id', "sequenza"+i);
		form.appendChild(input);
	}	
	
	form.appendChild(submit);
	document.getElementById('game').appendChild(form);
	
	loadKeyboard();
}