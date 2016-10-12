<%@ include file="header.jsp"%>
<title>SoundBear Playlists</title>

<script>
	$(document).ready(
			function() {
				$.ajax({
					type : 'GET',
					url : 'listPlaylists',
					dataType : 'json',
					success : function(data) {

						$.each(data.playlists, function(index, playlist) {

							$("#tbody").append("<tr>" +
								     " <th scope=\"row\">"+(index+1)+"</th>"+
								      "<td>"+playlist.playlistName+"</td>"+
								      "<td><a href=\"#\">View Playlist</a></td>"+
								      "<td> <input type=\"button\" id="+playlist.playlistId+" class=\"btn-danger\" value=\"Delete\"/>"+
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
	
	
 	function addPlaylist(playlistId){
 		var playlistName = $("#name").val();
 		
 		if(!playlistName){
 			alert("Please enter a playlist name.");
 			return;
 		}
 		
		$("#tbody").html("");
		
		$.ajax({
			type : 'GET',
			url : 'addPlaylist/'+playlistName,
			dataType : 'json',
			success : function(data) {

				$.each(data.playlists, function(index, playlist) {

					$("#tbody").append("<tr>" +
						     " <th scope=\"row\">"+(index+1)+"</th>"+
						      "<td>"+playlist.playlistName+"</td>"+
						      "<td><a href=\"#\">View Playlist</a></td>"+
						      "<td> <input type=\"button\" id="+playlist.playlistId+" class=\"btn-danger\" value=\"Delete\" />"+
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
 	        
 	    	var playlistId = this.id;
 	    	
 	    	$("#tbody").html("");
 			
 			$.ajax({
 				type : 'GET',
 				url : 'deletePlaylist/'+playlistId,
 				dataType : 'json',
 				success : function(data) {

 					$.each(data.playlists, function(index, playlist) {

 						$("#tbody").append("<tr>" +
 							     " <th scope=\"row\">"+(index+1)+"</th>"+
 							      "<td>"+playlist.playlistName+"</td>"+
 							      "<td><a href=\"#\">View Playlist</a></td>"+
 							      "<td> <input type=\"button\" id="+playlist.playlistId+" class=\"btn-danger\" value=\"Delete\"/>"+
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
<div class="col-md-6 col-md-push-1" id="Playlists">
		<div class="form-inline">
		<label>Playlist name:</label> 
		<input type="text" id = "name" class = "form-control" maxlength = "45"/>
		<input type ="button" style="width:60px;height:30px;" class = "btn-success" id = "add" value = "Add" onclick="addPlaylist()"/>
					
		</div>
		
		<table class="table table-hover" id="table">
			<thead>
				<tr>
				 	<th>#</th>
					<th>Playlist</th>
					<th>View</th>
				</tr>
			</thead>
			
			<tbody id="tbody">
			
			
			
			</tbody>

		</table>

	</div>

	<div id="error"></div>

</body>
</html>