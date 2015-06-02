
function getSequenceHTML(){
	
	var head = "<h3> Dai un'occhiata alla sequenza! Hai solo "; 
	var central = " secondi!";
	var tail = "</h3>";
	
	return head + "<span id='countDown'></span>" + central + "<br/><br/><span id='sequence'></span>" + tail; 
	
}

function getErrorMessage(error){
	
	var head = "<h3>Si &egrave; verificato il seguente errore: <br/>";
	var tail = "<br/> Per favore riprova a giocare.</h3>";
	
	
	return head + error + tail;
}




function getGameOverMessage(round){
	
	if (round > 2) {
		var head = "<h3>Congratulazioni! hai superato ";
		var tail = " round, riprova e supera te stesso!</h3>";
		
		return head + (round.trim() -1) + tail;
		
	} else if (round == 2) {
		
		return "<h3>La tua memoria non &egrave; delle peggiori, ma neanche delle migliori! Dai, riprova e diventa un maestro della memoria</h3>";
		
	} else if (round == 1){
		
		return "<h3>Hai perso al primo colpo. Sembra che per te sia necessario allenare la memoria, continua a giocare!</h3>";
	}
}


function getLogMessage(bool){
	
	var head = bool ? "<p class='testoverde'>" : "<p class='testorosso'>";
	var tail = "</p>";
	
	var content = bool ? "Ok!" :  "Errore, riprova.";
	
	return head + content + tail;
}