<%@ include file="header.jsp" %>
<title>SoundBear Player</title>

<script>
	function searchArtist() {
		var artistName = $("#artist").val().trim().replace(/ /g,"+");

		if(artistName){
			$.ajax({
				type : 'POST',
				url : 'http://ws.audioscrobbler.com/2.0/',
				data : 'method=artist.getinfo&' + 'artist='+artistName+'&'
						+ 'api_key=57ee3318536b23ee81d6b27e36997cde&' + 'format=json',
				dataType : 'jsonp',
				success : function(data) {
					$('#success #artistName').html(data.artist.name);
					$('#success #artistImage').html(
							'<img src="' + data.artist.image[2]['#text'] + '" />');
					$('#success #artistBio').html(data.artist.bio.content);
					
					
				},
				error : function(code, message) {
					$('#error').html(
							'Error Code: ' + code + ', Error Message: ' + message);
				}
			});
		}
		else{
			alert("Provide artist name.");
		}

	}
	
</script>

</head>


<body>

	<jsp:include page="navbar.jsp" />

	<div class="container">

		Artist: <input type="text" id="artist"  onkeydown = "if (event.keyCode == 13)
                        document.getElementById('searchButton').click()"/> 
		<input id="searchButton" type="submit"
			value="Search" onclick="searchArtist()" />



		<div id="success">
			<div id="artistName"></div>
			<div id="artistImage"></div>
			<div id="artistBio"></div>
			<div id="categories"></div>
		</div>
		<div id="error"></div>

	</div>

</body>

<%@ include file="footer.jsp" %>