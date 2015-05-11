/**
 * NavMenùPrinter
 */

function writeNavMenu(content) {
	document.getElementById("navMenu").innerHTML=content;
}

function loadNavMenu(){
	writeNavMenu(''+
			'<div class="container">'
	  '<img class="img-responsive" src="resources/MyMemo.png" alt="MyMemo" />'
	'</div>'
		'<div class="container">'
		    '<nav class="navbar navbar-default">'
	          '<div class="navbar-header">'
	            '<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">'
	              '<span class="sr-only">Toggle navigation</span>'
	              '<span class="icon-bar"></span>'
	              '<span class="icon-bar"></span>'
	              '<span class="icon-bar"></span>'
	            '</button>'
	            '<a class="navbar-brand" href="MyMemo.html">MyMemo</a>'
	          '</div>'
	          '<div class="navbar-collapse collapse">'
	            '<ul class="nav navbar-nav">'
	              '<li class="dropdown"><a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Gioca<span class="caret"></span></a>'
	              	'<ul class="dropdown-menu" role="menu">'
	                  '<li><a href="PartitaSingle.html">Single player</a></li>'
	                  '<li class="disabled"><a href="#">Multiplayer</a></li>'
	                '</ul>'		
	              '<li><a href="istruzioni.html">Info</a></li>'
	              '<li><a href="Contatti.html">Contatti</a></li>'
	            '</ul>'
	           '</div><!--/.nav-collapse -->'
	      	'</nav>'
		'</div>')
}