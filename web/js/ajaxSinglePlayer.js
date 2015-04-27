/**
 * 
 */

var playId = null;
var xmlHttp = null;
var contentContainer = document.getElementById("game");


function start(){
	loadPlayId();
	showSequence(loadSequence());
	
	
}

function showSequence(sequence) {
	document.getElementById("game").innerHTML="" +
	"Dai un'occhiata alla sequenza!, hai solo <span id='countDown'>7</span> secondi! -><span id='sequence'></span>";

	document.getElementById("sequence").innerHTML=loadSequence();
	timer = setInterval(function() {
	time = document.getElementById("countDown");
	if (time.innerHTML > 0) {
		time.innerHTML = time.innerHTML -1;
	}else {
		window.clearInterval(timer);
		document.getElementById("sequence").innerHTML="";
	}
	},1000);
}
function loadPlayId(){
	
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 playId = xmlHttp.responseText;   
	    }else{
	    	playId = null;
		}
	}		     
	httpGet("http://127.0.0.1:4444/singleplayer",cfunc);
}



function httpGet(theUrl)
{
   
    xmlHttp = new XMLHttpRequest();
    
	if (xmlHttp == null || xmlHttp == undefined)
	  {// code for IE6, IE5
	  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
    
	xmlHttp.onreadystatechange=cfunc;
    xmlHttp.open( "GET", theUrl, false );
    xmlHttp.send( null );
//    if (xmlHttp.readyState==4 && xmlHttp.status==200){
//	    return xmlHttp.responseText;   
//    }else{
//    	return null;
//    	}
//    
    
    
    
    
}


function loadSequence() {
	sequence = null;
	cfunc = function(){
		if (xmlHttp.readyState==4 && xmlHttp.status==200){
	    	 sequence = xmlHttp.responseText;   
	    }else{
	    	sequence = null;
		}
	}		     
	httpGet("http://127.0.0.1:4444/singleplayer/"+playId,cfunc);
	return sequence;
}