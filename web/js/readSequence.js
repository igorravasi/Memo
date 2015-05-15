//
//Lettura delle sequenze
//

function readSequence(sequence){
	
	var lunghezzaCorrettaDellaLunghezzaEsempioDiComeRicavarla = utf_length(trim(sequence));
	alert(lunghezzaCorrettaDellaLunghezzaEsempioDiComeRicavarla);
	
	document.getElementById("game").innerHTML = null;
	
	sequence = sequence.toString();
	var form = document.createElement("form");
	form.setAttribute('id',"seq");
	form.setAttribute('class',"form-emoji");
	
	var submit = document.createElement("input");
	submit.setAttribute('type',"button");
	submit.setAttribute('value',"Controlla");
	submit.setAttribute('onClick',"validate();");
	submit.setAttribute('class',"btn btn-primary btn-lg");
	
	for(var i=0; i<sequence.length; i++){
		var input = document.createElement("input");
		input.setAttribute('type',"text");
		input.setAttribute('class',"input-emoji");
		input.setAttribute('id', "sequenza");
		input.setAttribute('size', "1");
		form.appendChild(input);
	}	

	document.getElementById('game').appendChild(form);
	document.getElementById('game').appendChild(submit);
	
	loadKeyboard();
}

function limitEmoji(){
	var textArea = document.getElementsByTagName("input");
	for(i=0; i<textArea.length; i++){
		
	}
}