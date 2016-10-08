<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="css/navbar.css" rel="stylesheet">
<script type="text/javascript"
	src="<c:url value="/script/jquery-3.1.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/navbar.js"/>"></script>

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
				<a id="brand" class="navbar-brand  	glyphicon glyphicon-play-circle" href="play"> SoundBear</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">

					<li class="active"> <a class="username glyphicon glyphicon-user" href="#"> Profile </a> </li>

					<li> <a class = "upload glyphicon glyphicon-upload" href="upload"> Upload</a> </li>

					<li> <a  class = "playlists glyphicon glyphicon-play" href="#"> Playlists </a> </li>
					
					<li> <a  class = "mysongs glyphicon glyphicon-cd" href="#"> MySongs </a> </li>

					<li>
						<div class="search">
							<input type="text" class="form-control input-sm " maxlength="64"
								placeholder="Search" />
							<button type="submit" class="btn btn-primary btn-sm glyphicon glyphicon-search"></button>
						</div>
					</li>

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
	<script src="js/bootstrap.min.js"></script>
	<script src="js/navbar.js"></script>

</body>
</html>