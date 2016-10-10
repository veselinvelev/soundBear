<%@ include file="header.jsp"%>
<title>SoundBear My Songs</title>
<script>
	$(document).ready(
			function() {
				$.ajax({
					type : 'GET',
					url : 'listMySongs',
					dataType : 'json',
					success : function(data) {

						$.each(data.mySongs, function(index, song) {

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
	
	
/* 	function sortSongs(){
		var criteria = $("#sortBy").val();
		
		$.ajax({
			type : 'GET',
			url : 'sortMySongs/'+criteria,
			dataType : 'json',
			success : function(data) {

				$.each(data.mySongs, function(index, song) {

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
		
		
	} */
	
	
</script>

</head>


<body>
	<div class="col-md-9 col-md-push-1" id="mySongs">

		<select class="form-control" id="sortBy" name="sortBy" >

						<option value="artist">Artist</option>
						<option value="song">Song</option>
						<option value="genre">Genre</option>

					</select>
		
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