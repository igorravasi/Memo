/**
 * StringLib for some useful function of string manipulation.
 * 
 * IgorR.
 */

function trim(str){
	return str.replace(/^\s+|\s+$/g,'');
}
function replaceAll(find, replace, str) {
	  return str.replace(new RegExp(find, 'g'), replace);
	}
