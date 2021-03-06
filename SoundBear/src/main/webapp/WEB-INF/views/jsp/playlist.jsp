<%@ include file="header.jsp"%>
<title>SoundBear Playlist</title>

<c:set var="playlistId" value="${playlist.playlistId}" /> 

<script>

$(document).ready(
		function() {
			var playlistId = "${playlistId}";
			$("#table").hide();
			$("#sort").hide();
			
			$.ajax({
				type : 'GET',
				url : 'showSongs?pid='+playlistId,
				dataType : 'json',
				success : function(data) {
					
					if(Object.keys(data.songs).length > 0){
						$("#table").show();
						$("#sort").show();
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
							    "<td> <input type=\"button\" id="+song.songId+" class=\"btn-danger\" value=\"Delete\"/>"+
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
	var playlistId = "${playlistId}";
	
	$("#tbody").html("");
	
	$.ajax({
		type : 'GET',
		url : 'sortPlaylistSongs?pid='+playlistId+"&criteria="+criteria,
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
					    "<td> <input type=\"button\" id="+song.songId+" class=\"btn-danger\" value=\"Delete\"/>"+
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
	
	$(document).ready(function(){
 	    $("table").delegate("input", "click", function(){
 	        
 	    	var playlistId = "${playlistId}";
 	    	var songId = this.id;
 	    	
 	    	$("#tbody").html("");
 			
 			$.ajax({
 				type : 'GET',
 				url : 'deleteSongFromPlaylist?pid='+playlistId+"&sid="+songId,
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
							    "<td> <input type=\"button\" id="+song.songId+" class=\"btn-danger\" value=\"Delete\"/>"+
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
 	});
	


</script>

</head>

<body>
	<div class="col-md-9 col-md-push-1" id="playlist">
	
		<h3>Playlist: <c:out value="${playlist.playlistName}"/></h3>
		
		<div class="form-inline" id ="sort">
		
		<label>Sort by:</label> <select class="form-control" id="sortBy" name="sortBy" onchange="sortSongs()">

						<option value="artist">Artist</option>
						<option value="song">Song</option>
						<option value="genre">Genre</option>

					</select>
					
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