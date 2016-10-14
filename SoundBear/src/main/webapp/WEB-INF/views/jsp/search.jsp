
<%@ include file="header.jsp"%>
<title>SoundBear Profile</title>


<script type="text/javascript">
	$(document).ready(function() {
		
		var parameter = window.location.search.replace("?", ""); // will return the GET parameter 
		var values = parameter.split("=");
		var search = values[1];
		$("#table").hide();
		$("#table-users").hide();
		$("#playlists").hide();
		$("#no-songs").hide();
		$("#no-users").hide();

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
					
					if(Object.keys(data.songs).length > 0){
						$("#table").show();
						listPlaylists();
						addToPlaylist()
						$("#playlists").show();
					}else{
						$("#no-songs").show();
					}

					$.each(data.songs, function(index, song) {

						$("#tbody").append("<tr>" +
							     " <th scope=\"row\">"+(index+1)+"</th>"+
							      "<td>"+song.artist+"</td>"+
							      "<td>"+song.songName+"</td>"+
							      "<td>"+song.genre+"</td>"+
							      "<td><audio controls>"+
							      "<source src="+song.path+" type=\"audio/mpeg\">"+
							    "</audio></td>"+
							    "<td><button class=\"btn-success\" id="+song.songId+">Add to</button></td>"+
							    "</tr>");

					});
					
					
					
					if(Object.keys(data.users).length > 0){
						$("#table-users").show();
	
					}else{
						$("#no-users").show();
					}
					
					$.each(data.users, function(index, user) {
						var a = document.createElement('a');
						$("#tbody-users").append(+ "<td>");
						a.href = 'viewProfile?id=' + user.userId;
						$("#tbody-users").append("<tr>"+ 
								" <th scope=\"row\">"+
								(index + 1)+
								"</th>"+ "<td>"+
								user.username+
								"</td>"+ "<td>"
								+ user.email
								+ "</td> <td><a href=\""+a+"\">View Profile</a></td>"
																			);

					});
					
					
					
					

				} else {

				}
			},
			error : function(data) {
				alert(data);
			}

		})
	}
	);
	
	
	
	
	function listPlaylists(){
		
		$.ajax({
			type : 'GET',
			url : 'listPlaylists',
			dataType : 'json',
			success : function(data) {

				$.each(data.playlists, function(index, playlist) {

					$("#playlists").append("<option value="+playlist.playlistId+">"+playlist.playlistName+"</option>");

				});

			},
			error : function(code, message) {
				$('#error').html(
						'Error Code: ' + code + ', Error Message: '
								+ message);
			}
		});
		
	}
	
	function addToPlaylist(){
		
		  $("table").delegate("button", "click", function(){
	 	        
	 	    	var songId = this.id;
	 	    	var playlistId = $("#playlists").val();
	 	    	
	 	    	if(playlistId == 'empty'){
	 	    		$('#select-playlist').fadeIn().delay(2000).fadeOut();
	 	    		return;
	 	    	}
	 	    	
	 	    	
	 			$.ajax({
	 				type : 'GET',
	 				url : 'addSongToPlaylist?pid='+playlistId+'&sid='+songId,
	 				dataType : 'json',
	 				success : function(data) {
						if(data.status == 'NO'){
							$('#error-msg').fadeIn().delay(2000).fadeOut();
						}
						else{
							$('#success').fadeIn().delay(2000).fadeOut();
						}
	 				},
	 				error : function(data) {

	 				}
	 			});
	 	    	
	 	    });
		
	}
	
	
	
</script>

<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>

	<div id="exTab3" class="container responsive">
		<ul class="nav nav-pills">
			<li class="active"><a href="#1b" data-toggle="tab">Songs</a></li>
			<li><a href="#2b" data-toggle="tab">Users</a></li>
		</ul>

		<div class="tab-content clearfix">
		
			<!-- SONGS -->
			
			<div class="tab-pane active" id="1b">
			
					
			<div class="col-md-9 col-md-push-1" id="songs">
			
				<div class="col-xs-2 col-md-push-10" style="position:fixed; top: 24%;left: 83%;">
				
						 
							<select class="form-control" id="playlists">
								<option value="empty"></option>	
							
							
							</select>
							<span id="error-msg" style="display:none;color:red">The song is already in the selected playlist.</span>
							<span id ="select-playlist" style="display:none;color:red">Please select a playlist first.</span>
							<span id="success" style="display:none;color:green">Song was added to the selected playlist.</span>
						</div>
						
						
						<table class="table table-hover" id="table">
							<thead>
								<tr>
								 	<th>#</th>
									<th>Artist</th>
									<th>Song</th>
									<th>Genre</th>
									<th>Play</th>
								</tr>
							</thead>
			
			<tbody id="tbody">
			
			
			
			</tbody>

		</table>
		
		</div>
		<br>
		<h2><label id="no-songs">No Songs Found. Sorry :( </label></h2>
			</div>





			<!--USERS  -->
			<div class="tab-pane" id="2b">
						<table class="table table-hover" id="table-users">
			<thead>
				<tr>
					<th>#</th>
					<th>Username</th>
					<th>Email</th>
					<th></th>
				</tr>
			</thead>

			<tbody id="tbody-users">



			</tbody>

		</table>
		<br>
		<h2><label id="no-users">No Users Found. Sorry :( </label></h2>
			</div>

		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>
