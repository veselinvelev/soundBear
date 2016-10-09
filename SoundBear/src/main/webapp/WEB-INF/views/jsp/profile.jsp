
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
	function showUpload() {
		$("#file").val("");
		$("#upload-file-info").html("");
		if ($('.profile-pic').css('display') == 'none') {
			$(".profile-pic").show();
		} else {
			$(".profile-pic").hide();
		}

	}

	function validate(file) {
		var ext = file.split(".");
		ext = ext[ext.length - 1].toLowerCase();
		var arrayExtensions = [ "jpeg", "png", "jpg" ];

		if (arrayExtensions.lastIndexOf(ext) == -1) {
			alert("Only jpg, jpeg and png files are allowed.");
			$("#file").val("");
		}

		$('#upload-file-info').html($("#file").val());
	}

	$(document).ready(function() {
		$("form").submit(function(e) {
			var fileName = $('#file').val();

			if (fileName.length) {
				return true;
			} else {
				$(".choose-file").show();
				return false;
			}
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
					<img
						src="https://pbs.twimg.com/profile_images/2573540313/image.jpg"
						alt="" class="img-rounded img-responsive" />
				</div>



				<div class="span2 col-md-4 col-sm-4">



					<h3>
						<c:out value="${loggedUser.username}" />
					</h3>



					<h6>
						Email:
						<c:out value="${loggedUser.email}" />
					</h6>
					<h6>Ubication: Colombia</h6>
					<h6>Old: 1 Year</h6>


					<h6>
						<a href="#">More... </a>
					</h6>
				</div>

				<div class="span2 col-sm-6 col-md-6">
					<div class="btn-group">
						<button class="btn btn-primary btn-info btn-sm"
							onclick="showUpload()">Update Photo</button>


					</div>
				</div>

			</div>

			<div class="span2 col-md-4 profile-pic" style="display: none">
				<form name="form" method="POST" enctype="multipart/form-data"
					onsubmit="Validatebodypanelbumper()">
					<div class="btn-group">
						<span class="btn btn-sm btn-info btn-file col-md-pull-2">Browse
							<input type="file" id="file" name="file" accept="image/*"
							onchange="validate(this.value)" />
						</span> <input class="btn  btn-sm btn-info " type="submit" value="Upload" />
					</div>
				</form>

				<div class="row">
					<div class="col-md-9 ">
					<br>
						<span class='label label-info' id="upload-file-info"></span>
					</div>
				</div>
				 <span class="choose-file" style="display: none; color: red;">Please choose a file.</span>
			</div>

			<!--  	<div class="col-xs-8 divider text-center">
			<div class="col-xs-12 col-sm-4 emphasis">
			<br>
				<h2>
					<strong> 20,7K </strong>
				</h2>
				<p>
					<small>Followers</small>
				</p>
				<button class="btn btn-success btn-block">
					<span class="fa fa-plus-circle"></span> Follow
				</button>
			</div>
			<div class="col-xs-12 col-sm-4 emphasis">
				<br>
				<h2>
					<strong>245</strong>
				</h2>
				<p>
					<small>Following</small>
				</p>
				<button class="btn btn-info btn-block">
					<span class="fa fa-user"></span> View Profile
				</button>
			</div>

		</div>
		-->
		</div>
</body>
<%@ include file="footer.jsp"%>
