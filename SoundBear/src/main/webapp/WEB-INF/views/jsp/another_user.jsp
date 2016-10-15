
<%@ include file="header.jsp"%>
<title>SoundBear Profile</title>
<style>
img {
	max-width: 100%;
	height: auto;
}

.item {
	width: 200px;
	min-height: 200px;
	max-height: auto;
	float: left;
	margin: 3px;
	padding: 3px;
}

.btn-file {
	position: relative;
	overflow: hidden;
}

.btn-file input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	min-width: 100%;
	min-height: 100%;
	font-size: 100px;
	text-align: right;
	filter: alpha(opacity = 0);
	opacity: 0;
	outline: none;
	background: white;
	cursor: inherit;
	display: block;
}
</style>

<script type="text/javascript">
	var showSongs = false;

	$(document).ready(function() {

		var parameter = window.location.search.replace("?", ""); // will return the GET parameter 
		$(".followed-user-songs").hide();
		$("#playlists").hide();
		var values = parameter.split("=");

		//	alert(values[1]);

		$.ajax({
			type : 'GET',
			url : 'checkFollowStatus?id=' + values[1],
			dataType : 'json',
			async : false,
			success : function(data) {

				if (data.status == 'OK') {
					$("#follow").hide();
					$("#unfollow").show();
					showSongs = true;

				} else {

					$("#follow").show();
					$("#unfollow").hide();
					showSongs = false;

				}

			},
			error : function(code, message) {
			}
			
		
			
		});
		
		
		$.ajax({
			type : 'GET',
			url : 'listMySongs?id=' + values[1],
			dataType : 'json',
			success : function(data) {
				
				if(Object.keys(data.songs).length > 0 && showSongs){
					$(".followed-user-songs").show();
					$("#playlists").show();
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

			},
			error : function(code, message) {
				$('#error').html(
						'Error Code: ' + code + ', Error Message: '
								+ message);
			}
		});
		
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
		
		

	});

	
	
	
	function follow() {

		var parameter = window.location.search.replace("?", ""); // will return the GET parameter 

		var values = parameter.split("=");

		$.ajax({
			url : 'updateFollow?id=' + values[1] + '&action=follow',
			type : 'GET',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {

				window.location = 'viewProfile?id=' + values[1];

			},
			error : function(data) {
				window.location = 'error';
			}

		});
	}

	function unfollow() {

		var parameter = window.location.search.replace("?", ""); // will return the GET parameter 

		var values = parameter.split("=");

		$.ajax({
			url : 'updateFollow?id=' + values[1] + '&action=unfollow',
			type : 'GET',
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				window.location = 'viewProfile?id=' + values[1];

			},
			error : function(data) {

				window.location = 'error';
			}

		});
	}
	
	$(document).ready(function(){
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
 	});
	
</script>

<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">

		<br> <br>
		<div class="container-fluid well span6">
			<div class="row">
				<div class="item span2 col-sm-2 col-md-2">
					<img src="${anotherUser.photo}" alt=""
						class="img-rounded img-responsive" />
				</div>
				<div class="span2 col-md-4 col-sm-4">
					<h3>
						<c:out value="${anotherUser.username}" />
					</h3>
					<h6>
						Email:
						<c:out value="${anotherUser.email}" />
					</h6>

					<div class="col-md-4  row btn-group ">
						<button class="btn btn-primary btn-info btn-md" id="follow"
							style="display: none" onclick="follow()">Follow</button>
						<button class="btn btn-primary btn-info btn-md" id="unfollow"
							style="display: none" onclick="unfollow()">Unfollow</button>
					</div>
				</div>
			</div>

		</div>

		

		<div style="display: none" class="followed-user-songs col-md-11">
		
		<div class="col-xs-2 col-md-push-10" style="position:fixed; top: 70%;left: 83%;">
				
						 
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
	</div>
</body>
<%@ include file="footer.jsp"%>
