/**
 * 
 */

function request(theUrl, postContent){
   
    xmlHttp = new XMLHttpRequest();
    
	if (xmlHttp == null || xmlHttp == undefined)
	  {//IE6
	  xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
    
	
	
	var method = postContent == null ? "GET" : "POST";
    xmlHttp.open( method, theUrl, false );
      
    xmlHttp.send( encodeURI(postContent) );
    
    return xmlHttp.responseText;
 
}


function loadServerResponse( postContent ) {
	 

	var url = "singleplayer";
	if ( playId!=null ) {
		url += "/" + playId;
	}
	response = request(url, postContent);
	return response;
}
