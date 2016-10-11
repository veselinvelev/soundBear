
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
		$("#photo").val("");
		$("#upload-file-info").html("");
		$(".choose-file").hide();
		if ($('.profile-pic').css('display') == 'none') {
			$(".profile-pic").show();
		} else {
			$(".profile-pic").hide();
		}

	}

	function showPasswdUpdate() {
		$(".choose-file").hide();
		$("#photo").val("");
		$("#upload-file-info").html("");
		if ($('.change-password').css('display') == 'none') {
			$(".change-password").show();
		} else {
			$(".change-password").hide();
		}

	}

	function validatePhoto(photo) {
		$(".choose-file").hide();
		var ext = photo.split(".");
		ext = ext[ext.length - 1].toLowerCase();
		var arrayExtensions = [ "jpeg", "png", "jpg" ];

		if (typeof FileReader !== "undefined") {
			var size = document.getElementById('photo').files[0].size;
			if (size > 5242880) { //5MB
				alert("The file is too big. Max 20MB allowed");
				$("#photo").val("");
				return;
			}
		}

		if (arrayExtensions.lastIndexOf(ext) == -1) {
			alert("Only jpg, jpeg and png files are allowed.");
			$("#photo").val("");
		}

		$('#upload-file-info').html($("#photo").val());
	}

	$(document).ready(function() {
		$("form").submit(function(e) {
			var fileName = $('#photo').val();

			if (fileName.length) {
				return true;
			} else {
				$(".choose-file").show();
				return false;
			}
		});
	});

	function validate() {

		var password1 = $("#password1").val();
		var password2 = $("#password2").val();

		var validatePassword;

		if (password1 == password2) {
			isValidPassword = true;

		} else {
			isValidPassword = false;

			$(".password-error").show();
			$(".password-success").hide();
		}

		//check if the fields are empty
		if (!password1 && !password2) {
			isValidPassword = true;

			$(".password-success").hide();
			$(".password-error").hide();
		}

		if (isValidPassword) {
			$("#change-password").attr("disabled", false);
		} else {
			$("#change-password").attr("disabled", true);
		}
	}

	function changePassword() {
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();

		if (password1 && password2) {
			$.ajax({
				url : 'changePassword',
				type : 'POST',
				data : JSON.stringify({
					"password1" : password1,
					"password2" : password2
				}),
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {

					if (data.status === 'OK') {
						window.location = 'profile';
					} else {
						$(".login-error").show();
					}
				},
				error : function(data) {
					alert(data);
				}

			});
		}
	}


	//);
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


				</div>

				<div class="col-md-2  row  ">
					<div class=" col-sm-2 inline">
						<p>
						<h2>
							<strong> <c:out value="${loggedUser.followers}" /></strong>
						</h2>

						<strong><a href="followers">Followers</a></strong>
						</p>

					</div>
					<div class=" col-sm-4 col-sm-push-8">

						<h2>
							<strong><c:out value="${loggedUser.following}" /></strong>
						</h2>
						<strong><a href="followers">Following</a></strong>

					</div>
				</div>





		

			</div>






			<!--
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
	</div>
</body>
<%@ include file="footer.jsp"%>
