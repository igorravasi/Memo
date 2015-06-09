

doTheRightThing(loadServerResponse(null));


function loadServerResponse( postContent ) {
	 

	var url = "notifiche";
	
	response = request(url, postContent);
	
	return response;
}


function doTheRightThing(response) {

	var command = response.substring(0,response.indexOf(":"));
	var message = response.substring(response.indexOf(":")+1);
	
	
	switch (command) {
	
	case "N":
		writeTable(message);
		
		break;
	case "E":
	default:
		//writeAMessage(getErrorMessage(message));
		break;
	}
	
	
}


function writeTable(text){
	//1433842014524	3	marti	2-developer 3-marti
	
	var otherRow = text.indexOf("\n") >= 0 ? true : false;
	
	
	var remaining = text;
	var line = text.substring(0, text.indexOf("\n"));
	
	remaining = remaining.substring(text.indexOf("\n") + 1);
	
	while (otherRow == true) {
		var row = "<tr>";
				
		var maxPoint;
		var winners;
		var board;
		var date;
		
		date = line.substring(0, line.indexOf("\t"));
		line = line.substring(line.indexOf("\t") + 1);	
		
		maxPoint = line.substring(0, line.indexOf("\t"));
		line = line.substring(line.indexOf("\t") + 1);
		
		winners = line.substring(0, line.indexOf("\t"));
		line = trim(line.substring(line.indexOf("\t") + 1));
		winners = replaceAll(" ","<br/>",winners);
		
		board = replaceAll(" ", "<br/>", line);
		
		row += "<td>" + date + "</td>";
		row += "<td>" + maxPoint + "</td>";
		row += "<td>" + winners +  "</td>";
		
		row += "<td>" + board + "</td>";
		row += "</tr>";
		
		document.getElementById("notifiche-table").innerHTML = row + document.getElementById("notifiche-table").innerHTML;
		
		line = remaining.substring(0, remaining.indexOf("\n"));
		remaining = remaining.substring(remaining.indexOf("\n") + 1);
		
		otherRow = remaining.indexOf("\n") >= 0 ? true : false;
		
	}
	
	
	
}

function twoDigits(value) {
	   if(value < 10) {
	    return '0' + value;
	   }
	   return value;
	}

	
	// display in format HH:MM:SS
	var formattedTime = twoDigits(date.getHours()) 
	      + ':' + twoDigits(date.getMinutes()) 
	      + ':' + twoDigits(date.getSeconds());
