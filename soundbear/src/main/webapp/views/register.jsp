<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>SoundBear Register</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/login.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script>
	function validate() {
		var username = $("#username").val();
		var email = $("#email").val();
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();

		var validateUsername;
		var validateEmail;
		var validatePassword;

		$.ajax({
			url : "../Register?username=" + username + "&email=" + email,
			type : 'GET',
			dataType : 'json',
			async : false,
			success : function(data) {
				var json = $.parseJSON(JSON.stringify(data));

				if (username) {
					if (json.validUsername) {
						validateUsername = true;

						$(".username-success").show();
						$(".username-error").hide();
					} else {
						validateUsername = false;

						$(".username-error").show();
						$(".username-success").hide();
					}
				} else {
					validateUsername = true;

					$(".username-success").hide();
					$(".username-error").hide();
				}

				if (email) {
					if (json.validEmail) {
						validateEmail = true

						$(".email-success").show();
						$(".email-error").hide();
					} else {
						validateEmail = false;

						$(".email-error").show();
						$(".email-success").hide();
					}
				} else {
					validateEmail = true;

					$(".email-success").hide();
					$(".email-error").hide();
				}
			}
		});

		if (password1 == password2) {
			validatePassword = true;

			$(".password-success").show();
			$(".password-error").hide();
		} else {
			validatePassword = false;

			$(".password-error").show();
			$(".password-success").hide();
		}

		if (!password1 && !password2) {
			validatePassword = true;

			$(".password-success").hide();
			$(".password-error").hide();
		}

		if (validateUsername && validateEmail && validatePassword) {
			$("#signup").attr("disabled", false);
		} else {
			$("#signup").attr("disabled", true);
		}
	}

	function register() {
		var username = $("#username").val();
		var email = $("#email").val();
		var password = $("#password1").val();

		if (username && email && password) {
			var userJson = {
				"username" : username,
				"email" : email,
				"password" : password
			};

			$.ajax({
				url : '../Register',
				type : 'POST',
				dataType : 'json',
				data : userJson,

				success : window.location = './play.jsp'
			});
		}
	}
</script>


</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-12">

				<div class="wrap">
					<p class="form-title">Sign Up</p>
					<div class="login">

						<input type="text" id="username" placeholder="Username"
							required="required" onblur="validate()" /> <span
							class="username-error">Username taken. </span> <span
							class="username-success">Username available. </span> <input
							type="text" id="email" placeholder="Email" required="required"
							onblur="validate()" /> <span class="email-error">Email
							taken or invalid. </span> <span class="email-success">Email
							available. </span> <input type="password" id="password1"
							placeholder="Password" required="required" onblur="validate()" />
						<input type="password" id="password2"
							placeholder="Confirm Password" required="required"
							onblur="validate()" /> <span class="password-error">Passwords
							don't match. </span> <span class="password-success">Passwords
							match. </span>


						<div class="btn-group btn-group-justified">
							<div class="btn-group">

								<button id="signup" class="btn btn-primary btn-success btn-sm"
									onclick="register()">SIGN UP</button>

							</div>
						</div>

						<div class="remember-forgot"></div>

						<div class="fb-login-button" data-max-rows="1" data-size="xlarge"
							data-show-faces="false" data-auto-logout-link="false"></div>
						<div class="fb-login-button" data-max-rows="1" data-size="xlarge"
							data-show-faces="false" data-auto-logout-link="false"></div>
					</div>

					<div id="fb-root"></div>

				</div>

			</div>
		</div>
	</div>




	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
</body>

</html>
