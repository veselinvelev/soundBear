<%@ include file="header.jsp"%>
<title>SoundBear My Songs</title>
<script>
	$(document).ready(
			function() {
				
				$("#table").hide();
				$("#sort").hide();
				$("#playlists").hide();
				$("#sorting").hide();
				
				$.ajax({
					type : 'GET',
					url : 'listMySongs',
					dataType : 'json',
					success : function(data) {
						
						if(Object.keys(data.songs).length > 0){
							$("#table").show();
							$("#sort").show();
							$("#playlists").show();
							$("#sorting").show();
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
								    "<td><input type=\"button\" id="+song.songId+" class=\"btn-danger\" value=\"Delete\"/>"+
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

			});
	
	
 	function sortSongs(){
		var criteria = $("#sortBy").val();
		
		$("#tbody").html("");
		
		$.ajax({
			type : 'GET',
			url : 'sortMySongs/'+criteria,
			dataType : 'json',
			success : function(data) {

				$.each(data.songs, function(index, song) {

					$("#tbody").append("<tr>" +
						     " <th scope=\"row\">"+(index+1)+"</th>"+
						      "<td>"+song.artist+"</td>"+
						      "<td>"+song.songName+"</td>"+
						      "<td>"+song.genre+"</td>"+
						      "<td><audio controls>"+
						      "<source src="+song.path+" type=\"audio/mpeg\">"+
						    "</audio></td>"+
						    "<td><input type=\"button\" id="+song.songId+" class=\"btn-danger\" value=\"Delete\"/>"+
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
		
		
	} 
 	
 	$(document).ready(
			function() {
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
 	
 	$(document).ready(function(){
 	    $("table").delegate("input", "click", function(){
 	        
 	    	$("#tbody").html("");
 	    	var songId = this.id;
 	    	
 			$.ajax({
 				type : 'GET',
 				url : 'deleteSong?sid='+songId,
 				dataType : 'json',
 				success : function(data) {
 					
 					$.each(data.songs, function(index, song) {

						$("#tbody").append("<tr>" +
							     " <th scope=\"row\">"+(index+1)+"</th>"+
							      "<td>"+song.artist+"</td>"+
							      "<td>"+song.songName+"</td>"+
							      "<td>"+song.genre+"</td>"+
							      "<td><audio controls>"+
							      "<source src="+song.path+" type=\"audio/mpeg\">"+
							    "</audio></td>"+
							    "<td><input type=\"button\" id="+song.songId+" class=\"btn-danger\" value=\"Delete\"/>"+
							    "<td><button class=\"btn-success\" id="+song.songId+">Add to</button></td>"+
							    "</tr>");

					});
 					
 				},
 				error : function(data) {

 				}
 			});
 	    	
 	    });
 	});
	
</script>

</head>


<body>
	<div class="col-md-9 col-md-push-1" id="mySongs">
		<div class="form-inline" id = "sorting">
		<label>Sort by:</label> <select class="form-control" id="sortBy" name="sortBy" onchange="sortSongs()">

						<option value="artist">Artist</option>
						<option value="song">Song</option>
						<option value="genre">Genre</option>

					</select>
					
		</div>
		
		 <div class="col-xs-2 col-md-push-10" style="position:fixed; top: 28%;left: 83%;">
		 
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

	<div id="error"></div>
</body>

<%@ include file="footer.jsp"%>