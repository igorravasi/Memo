/**
 * Libreria contenente le funzioni di manipolazioni stringhe usate nel gioco.
 * Esse rappresentano metodi/funzioni comuni di stringhe che in JavaScript non 
 * sono nativamente supportate
 * 
 */


function trim(str){
	return str.replace(/^\s+|\s+$/g,'');
	return str;
}

function replaceAll(find, replace, str) {
	return str.replace(new RegExp(find, 'g'), replace);
}

function utf_length(str){
	
	/*Alcuni (molti) caratteri,  come le emoji, hanno un codePoint non rappresentabile
	* in un unico codice unicode, che usa 4 cifre hex. 
	* Per questo si usa una coppia di codici chiamati surrogatePairs.
	* La normale string.length di javascript conta la coppia come 2 caratteri, ma in realtà
	* ne rappresentano solo 1! 
	* 
	*/
	var regexSurrogatePairs = /[\uD800-\uDBFF][\uDC00-\uDFFF]/g;
	
	str = str.replace(regexSurrogatePairs,'0');
	return str.length;
	
}