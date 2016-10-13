<%@ include file="header.jsp"%>
<title>SoundBear My Songs</title>
<script>
	$(document).ready(
			function() {
				
				$("#table").hide();
				$("#sort").hide();
				
				$.ajax({
					type : 'GET',
					url : 'listMySongs',
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
	
	
</script>

</head>


<body>
	<div class="col-md-9 col-md-push-1" id="mySongs">
		<div class="form-inline">
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