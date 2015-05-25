
		<div class="container">
			<nav class="navbar navbar-default">
				<div class="navbar-header">
			    	<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
			      		<span class="sr-only">Toggle navigation</span>
			      		<span class="icon-bar"></span>
			      		<span class="icon-bar"></span>
			      		<span class="icon-bar"></span>
			    	</button>
			    	<a class="navbar-brand" href="MyMemo.html">MyMemo</a>
				</div>
			  	<div class="navbar-collapse collapse">
			    	<ul class="nav navbar-nav">
			      		<li class="dropdown">
			      			<a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Gioca<span class="caret"></span></a>
			      			<ul class="dropdown-menu" role="menu">
			          			<li><a href="PartitaSingle.html">Single player</a></li>
			          			<li><a href="PartitaMulti.html">Multiplayer</a></li>
			        		</ul>		
			      		<li><a href="Istruzioni.html">Info</a></li>
			      		<li><a href="Contatti.html">Contatti</a></li>
			      		<li class="dropdown" style="float: right;">
							 <a href="" class="dropdown-toggle" data-toggle="dropdown">Login <b class="caret"></b></a>
							 <ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
							 	<li>
							 		<div class="row">
							 			<div class="col-md-12">
											<form class="form" role="form" id="login-nav" onsubmit="event.preventDefault(); return false">
							 					<div class="form-group">
							 						<label class="sr-only" for="id_user">Utente</label>
							 						<input type="text" class="form-control" name="Utente" id="id_user" placeholder="Utente">
							 					</div>
							 					<div class="form-group">
							 						<label class="sr-only" for="id_password">Password</label>
							 						<input type="password" class="form-control" name="Password" id="id_password" placeholder="Password">
							 					</div>
							 					
							 					<div class="form-group">
							 						<button class="btn btn-success btn-block" onclick="submitLogin(this)" name="Do" value="login">Login</button>
							 					</div>
							 					<div class="form-group">
							 						<button class="btn btn-success btn-block" onclick="submitLogin(this)" name="Do" value="logout">Logout</button>
							 					</div>
							 				</form>
							 			</div>
							 		</div>
							 	</li>
							 </ul>
						</li>
			    	</ul>
			   	</div>
			</nav>
		</div>