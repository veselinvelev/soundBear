<%@ include file="header.jsp"%>
<title>SoundBear Player</title>

<style>
a.song-a {
	color: darkorange;
	display: block;
	padding: 5px;
	font-size: 15px;
}

#playlist {
	background: #404040;
	width: 400px;
	padding: 20px;
	list-style-type: none;
}

audio {
	background: DimGrey;
	width: 400px;
	height:110px;
	padding: 20px;
}

li.active a.song-a {
	color: white;
}

li a.song-a {
	background: #404040;
	padding: 5px;
	display: block;
	text-decoration: none;
}
</style>


</head>

<script type="text/javascript">
	$(document).ready(function() {
		var audio;
		var playlist;
		var tracks;
		var current;

		init();
		function init() {
			current = 0;
			audio = $('audio');
			playlist = $('#playlist');
			tracks = playlist.find('li a.song-a');
			len = tracks.length;
			audio[0].volume = .10;
			audio[0].play();
			playlist.find('a.song-a').click(function(e) {
				e.preventDefault();
				link = $(this);
				current = link.parent().index();
				run(link, audio[0]);
			});
			audio[0].addEventListener('ended', function(e) {
				current++;
				if (current == len) {
					current = 0;
					link = playlist.find('a.song-a')[0];
				} else {
					link = playlist.find('a.song-a')[current];
				}
				run($(link), audio[0]);
			});
		}
		function run(link, player) {
			player.src = link.attr('href');
			par = link.parent();
			par.addClass('active').siblings().removeClass('active');
			audio[0].load();
			audio[0].play();
		}
		
			$.ajax({
				type : 'GET',
				url : 'listPlaylists',
				dataType : 'json',
				success : function(data) {

				/* 	if(Object.keys(data.playlists).length == 0){
						$(".audioPlayer").hide();
						return;
					}
					
					$(".audioPlayer").show(); */
					
					$.each(data.playlists, function(index, playlist) {

						$("#playlists").append(
								"<option value="+playlist.playlistId+">"
										+ playlist.playlistName
										+ "</option>");

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
 	    $("ul").delegate("a.song-a", "click", function(){
 	   		
 	    	
 	    	var audio;
 	   		var playlist;
 	   		var tracks;
 	   		var current;

 	   		init();
 	   		function init() {
 	   			current = 0;
 	   			audio = $('audio');
 	   			playlist = $('#playlist');
 	   			tracks = playlist.find('li a.song-a');
 	   			len = tracks.length;
 	   			audio[0].volume = .10;
 	   			audio[0].play();
 	   			playlist.find('a.song-a').click(function(e) {
 	   				e.preventDefault();
 	   				link = $(this);
 	   				current = link.parent().index();
 	   				run(link, audio[0]);
 	   			});
 	   			audio[0].addEventListener('ended', function(e) {
 	   				current++;
 	   				if (current == len) {
 	   					current = 0;
 	   					link = playlist.find('a.song-a')[0];
 	   				} else {
 	   					link = playlist.find('a.song-a')[current];
 	   				}
 	   				run($(link), audio[0]);
 	   			});
 	   		}
 	   		function run(link, player) {
 	   			player.src = link.attr('href');
 	   			par = link.parent();
 	   			par.addClass('active').siblings().removeClass('active');
 	   			audio[0].load();
 	   			audio[0].play();
 	   		}
 	    	
 	    	
 	        return false; 	    	
 	    });
 	});

	function loadPlaylist(){
	
		var playlistId = $("#playlists").val();
		document.getElementById("audio").removeAttribute("src");
		document.getElementById("audio").load();
		document.getElementById("audio").play();
		
		$("#audio").html("");
		$("#playlist").html("");
		if (playlistId == 'empty') {
			return;
		}
		$.ajax({
			type : 'GET',
			url : 'showSongs?pid='+playlistId,
			dataType : 'json',
			success : function(data) {
				
				if(Object.keys(data.songs).length > 0){
				
				}

				$.each(data.songs, function(index, song) {

					if(index == 0){
						
						$("#audio").append("<source type=\"audio/mp3\" src=\" "     + song.path +  "\">");
						
						
						$("#playlist").append("<li class=\"active\"><a class=\"song-a\" href=\" " + song.path +"\">" +song.artist + " - " + song.songName +"</a></li>");
						
					}
					else{
						$("#playlist").append("<li><a class=\"song-a\" href=\" " + song.path +"\">" +song.artist + " - " + song.songName +"</a></li>");
						
					}
					

				});

			},
			error : function(code, message) {
				$('#error').html(
						'Error Code: ' + code + ', Error Message: '
								+ message);
			}
		});
		
	}
	
	
	function loadSongsInfo(){
		
		var playlistId = $("#playlists").val();
		
		$("#1b").html("");
		
		//$("#audio").html("");
		//$("#playlist").html("");
		if (playlistId == 'empty') {
			return;
		}
		$.ajax({
			type : 'GET',
			url : 'showSongInfo?pid='+playlistId,
			dataType : 'json',
			success : function(data) {
			
				$.each(data.songsInfo, function(index, songInfo){
					
					var sName = document.createElement("p");
					var sImage = document.createElement("img");
					var sWiki = document.createElement("p");
					var sNameDiv = document.createElement("div");
					var sImageDiv = document.createElement("div");
					var sWikiDiv = document.createElement("div");
					
					sName.setAttribute("style","font-size:22px");
					sName.setAttribute("class","glyphicon glyphicon-headphones");
					sImage.setAttribute("class","class=\"img-rounded img-responsive\"");
					
					sName.innerHTML = songInfo.track.name;
					sImage.setAttribute("src",songInfo.track.album.image[2]['#text']);
					sWiki.innerHTML = songInfo.track.wiki.summary;
					
					sNameDiv.appendChild(sName);
					sImageDiv.appendChild(sImage);
					sWikiDiv.appendChild(sWiki);
				
					
					$("#1b").append(sNameDiv);
					$("#1b").append(sImageDiv);
					$("#1b").append(sWikiDiv);
					$("#1b").append("<hr/>")
				});
			
			},
			error : function(code, message) {
				$('#error').html(
						'Error Code: ' + code + ', Error Message: '
								+ message);
			}
		});
		
	}
	
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
							'<img class=\"img-rounded img-responsive\" style=\"float:left\;padding:10px;" src="' + data.artist.image[2]['#text'] + '" />');
					$('#success #artistBio').html(data.artist.bio.content);
					
					
				},
				error : function(code, message) {
					$('#error').html(
							'Error Code: ' + code + ', Error Message: ' + message);
				}
			});
		}
		else{
			alert("Please enter a name.");
		}

	}
	

</script>


<body>

	<div id="exTab3" class="container">


		<div class="audioPlayer row row-centered">

			<div class="row ">

				<div class="col-xs-4">

					<audio id="audio" preload="auto" tabindex="0" controls>
					 	<!-- <source type="audio/mp3" src="https://soundbear.s3-us-west-2.amazonaws.com/XGonGiveItToYa3420161010231801">  -->
					</audio>



				</div>
				<div class=" col-xs-4" style="overflow-y: scroll; height:110px; width:432; background-color:#404040">

					<ul id="playlist">
					
					
<!--
						 <li class="active"><a class="song-a" href="https://soundbear.s3-us-west-2.amazonaws.com/XGonGiveItToYa3420161010231801">Ravel Bolero</a></li>
						 <li class="active"><a class="song-a" href=" https://soundbear.s3-us-west-2.amazonaws.com/XGonGiveItToYa3420161010231801">Ravel Bolero</a></li>

					 	<li><a class="song-a"
							href="http://www.archive.org/download/MoonlightSonata_755/Beethoven-MoonlightSonata.mp3">Moonlight
								Sonata - Beethoven</a></li>

						<li><a class="song-a"
							href="http://www.archive.org/download/CanonInD_261/CanoninD.mp3">Canon
								in D Pachabel</a></li> -->


					</ul>
				</div>

				<div class="col-xs-2 col-xs-push-1">

					<select class="form-control" id="playlists" onchange="loadPlaylist(); loadSongsInfo();">
						<option value="empty"></option>


					</select>
				</div>

			</div>

		</div>


		<hr />

		<ul class="nav nav-pills">
			<li class="active"><a href="#1b" data-toggle="tab">Songs</a></li>
			<li><a href="#2b" data-toggle="tab">Find an artist</a></li>
			<!-- <li><a href="#3b" data-toggle="tab">Applying clearfix</a></li>
			<li><a href="#4a" data-toggle="tab">Background color</a></li> -->
		</ul>

		<div class="tab-content clearfix">
			<br> <br>
			
			<div class="tab-pane active " style="overflow-y: scroll; height:330px; width:1130px;" id="1b">
			
			
			</div>
			
			<div class="tab-pane" style="overflow-y: scroll; height:330px; width:1130px;" id="2b">
			
			<label>Artist:</label>	
			<div class = "form-group form-inline">
				 <input type="text" class="form-control col-xs-3" id="artist" maxlength="45"  onkeydown = "if (event.keyCode == 13)
                        document.getElementById('apiSearch').click()"/> 
				<button id="apiSearch" type="submit" class="btn-sm btn-primary glyphicon glyphicon-search"  onclick="searchArtist()" ></button>				
			</div>
			
			<div id="success">
			<div id="artistName"></div>
			<div id="artistImage"></div>
			<div id="artistBio"></div>
			<div id="categories"></div>
			</div>
			<div id="error"></div>

		<!-- 	</div>
			<div class="tab-pane" id="3b">
				<h3>We applied clearfix to the tab-content to rid of the gap
					between the tab and the content</h3>
			</div>
			<div class="tab-pane" id="4b">
				<h3>We use css to change the background color of the content to
					be equal to the tab</h3>
			</div> -->
		</div>


	</div>



</body>

<%@ include file="footer.jsp"%>