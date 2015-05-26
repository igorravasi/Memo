
function display(elementId, boolDisplay){
	var element = document.getElementById(elementId);
	
	var toBeSet = boolDisplay ? "visibile" : "nascosto";
	var toBeDeleted = !boolDisplay ? "visibile" : "nascosto";
	
	var elementClasses = document.getElementById(elementId).className;
	var regex = new RegExp("\s*"+toBeDeleted+"\s*");
	elementClasses = elementClasses.replace(regex,"");
	if (!elementClasses.match("\s*"+toBeSet+"\s*")) {
		elementClasses += " " + toBeSet;
	}
	
	element.className = elementClasses;
		
}