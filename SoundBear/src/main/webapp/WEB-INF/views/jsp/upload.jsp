<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script>
	function validate(file) {
		var ext = file.split(".");
		ext = ext[ext.length - 1].toLowerCase();
		var arrayExtensions = [ "mp3" ];

		if (arrayExtensions.lastIndexOf(ext) == -1) {
			alert("Only mp3 files are allowed.");
			$("#my-file-selector").val("");
		}

		$('#upload-file-info').html($("#my-file-selector").val());
	}

	function uploadSong() {
		var artist = $("#artist").val();
		var name = $("#name").val();
		var genre = $("#genre").val();
		var songName = $('#my-file-selector').val();

		if (artist && name && genre != "empty") {

		} else {
			$(".fields-required").show();
		}

	}
</script>


</head>

<jsp:include page="navbar.jsp" />

<body>

	<div class="container">

		<br />

		<div class="row">
			<h2 class="text-primary col-md-4 col-md-push-4">Upload a song</h2>
		</div>

		<br />

		<div class="row">
			<div class="form-group col-xs-3 col-md-push-4">

				<label class="text-primary"> Artist: </label> <input type="text"
					class="form-control" id="artist" maxlength="45">

			</div>
		</div>

		<div class="row">
			<div class="form-group col-xs-3 col-md-push-4">

				<label class="text-primary"> Song name: </label> <input type="text"
					class="form-control" id="name" maxlength="45">

			</div>
		</div>

		<div class="row">
			<div class="form-group col-xs-3 col-md-push-4">
				<label class="text-primary"> Genre: </label> <select
					class="form-control" id="genre">

					<option value="empty"></option>
					<option value="pop">Pop</option>
					<option value="rock">Rock</option>
					<option value="rap">Rap</option>
					<option value="jazz">Jazz</option>
					<option value="house">House</option>
					<option value="classical">Classical</option>
					<option value="karaoke">Karaoke</option>

				</select>

			</div>
		</div>

		<div class="row">

			<div class="form-group col-md-6">
				<label class="btn btn-primary" for="my-file-selector"> <input
					id="my-file-selector" type="file" accept="audio/*"
					style="display: none;" onchange="validate(this.value)">
					Choose file
				</label>
			</div>

		</div>

		<br /> <br />
		<div class="col-md-9 col-md-push-4">
			<span class='label label-primary' id="upload-file-info"></span>
		</div>

		<br />

		<div class="row">

			<div class="form-group col-md-6">
				<button id="submit" type="submit" class="btn btn-primary"
					onclick="uploadSong()">Upload</button>
			</div>

		</div>

		<br /> <br /> <span class="fields-required col-md-3 col-md-push-4"
			style="display: none; color: red;">Please fill out all fields.</span>

	</div>
</body>


</html>