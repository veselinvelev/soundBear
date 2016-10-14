

<link href="css/navbar.css" rel="stylesheet">
<script type="text/javascript"
	src="<c:url value="/script/jquery-3.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/navbar.js"/>"></script>
<script type="text/javascript">
	function searching() {
		var search = $("#search-text").val();
		alert(search);

		$.ajax({
			url : 'search',
			type : 'POST',
			data : JSON.stringify({
				"search" : search,
			}),
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {

				if (data.status === 'OK') {
					
				} else {
			
				}
			},
			error : function(data) {
				alert(data);
			}

		});
	}
</script>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);

	if (session.getAttribute("loggedUser") == null)
		response.sendRedirect("login");
%>



<!-- Fixed navbar -->
<nav id="header" class="navbar navbar-fixed-top">
	<div id="header-container" class="container navbar-container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a id="brand" class="navbar-brand  	glyphicon glyphicon-music"
				href="play"> SoundBear</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">

				<li class="active"><a class="username glyphicon glyphicon-user"
					href="profile"> Profile </a></li>

				<li><a class="upload glyphicon glyphicon-upload" href="upload">
						Upload</a></li>

				<li><a class="playlists glyphicon glyphicon-play"
					href="playlists"> Playlists </a></li>

				<li><a class="mysongs glyphicon glyphicon-cd" href="mySongs">
						MySongs </a></li>
				<li><a class="mysongs glyphicon glyphicon-cd" href="mySongs">
						Logout </a></li>


				<li>
					<div class="search col-md-pull-2">
						<input type="text" id="search-text" class="form-control input-sm " maxlength="64"
							placeholder="Search" />
						<button type="submit"
							class="searching btn btn-warning btn-sm glyphicon glyphicon-search "
							onclick="searching()"></button>
					</div>
				</li>

				<li>
					<div class="col-md-2"></div>
				</li>
				<li><a class="mysongs glyphicon  glyphicon glyphicon-off"
					href="logout"> Logout </a></li>

			</ul>
		</div>
		<!-- /.nav-collapse -->
	</div>
	<!-- /.container -->
</nav>
<!-- /.navbar -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- 	Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value = "/script/bootstrap.min.js"/>"></script>

<script src="<c:url value = "/script/navbar.js"/>"></script>

<script type="text/javascript"
	src="<c:url value="/script/jquery-3.1.1.min.js"/>"></script>

