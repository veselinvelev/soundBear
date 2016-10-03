<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<script type="text/javascript"
	src="<c:url value="/script/jquery-3.1.1.min.js"/>"></script>
<script>
	function validate() {
		var username = $("#username").val();
		var email = $("#email").val();
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();

		var isValidUsername;
		var isValidEmail;
		var validatePassword;

		$.ajax({
			url : 'validateRegisterForm',
			type : 'POST',
			data : JSON.stringify({
				"username" : username,
				"email" : email
			}),
			contentType: "application/json; charset=utf-8",
			dataType : 'json',
			async : false,
			success : function(data) {
				
				if (username) {
					if (data.isValidUsername) {
						isValidUsername = true;

						$(".username-success").show();
						$(".username-error").hide();
					} else {
						isValidUsername = false;

						$(".username-error").show();
						$(".username-success").hide();
					}
				} else {
					isValidUsername = true;

					$(".username-success").hide();
					$(".username-error").hide();
				}

				if (email) {
					if (data.isValidEmail) {
						isValidEmail = true

						$(".email-success").show();
						$(".email-error").hide();
					} else {
						isValidEmail = false;

						$(".email-error").show();
						$(".email-success").hide();
					}
				} else {
					isValidEmail = true;

					$(".email-success").hide();
					$(".email-error").hide();
				}
			},
			error : function(data) {
				alert(data);
			}
		});

		if (password1 == password2) {
			isValidPassword = true;

			$(".password-success").show();
			$(".password-error").hide();
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

		if (isValidUsername && isValidEmail && isValidPassword) {
			$("#signup").attr("disabled", false);
		} else {
			$("#signup").attr("disabled", true);
		}
	}

	function register() {
		var username = $("#username").val();
		var email = $("#email").val();
		var password1 = $("#password1").val();
		var password2 = $("#password2").val();

		if (username && email && password1 && password2) {
			$.ajax({
				url : 'registerSubmit',
				type : 'POST',
				data : JSON.stringify({
					"username" : username,
					"email" : email,
					"password1":password1,
					"password2":password2
				}),
				contentType: "application/json; charset=utf-8",
				dataType : 'json',
				success : function(data) {

					if (data.status === 'OK') {
						alert(data.msg);
						window.location = 'index';
					} else {
						alert(data.msg);
						window.location = 'register';
					}
				},
				error : function(data) {
					alert(data);
				}
					

			});
		} else {
			$(".fields-required").show();
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
							required="required" maxlength="45" onblur="validate()" /> <span
							class="username-error">Username taken. </span> <span
							class="username-success">Username available. </span> <input
							type="text" id="email" placeholder="Email" required="required"
							maxlength="45" onblur="validate()" /> <span class="email-error">Email
							taken or invalid. </span> <span class="email-success">Email
							available. </span> <input type="password" id="password1"
							placeholder="Password" required="required" maxlength="45"
							onblur="validate()" /> <input type="password" id="password2"
							placeholder="Confirm Password" required="required" maxlength="45"
							onblur="validate()" /> <span class="password-error">Passwords
							don't match. </span> <span class="password-success">Passwords
							match. </span>


						<div class="btn-group btn-group-justified">
							<div class="btn-group">
								<button id="signup" class="btn btn-primary btn-success btn-sm"
									onclick="register()">SIGN UP</button>
							</div>
						</div>

						<span class="fields-required">Please fill out all fields.</span>

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
	<script src="<c:url value = "/script/bootstrap.min.js"/>"></script>
</body>

</html>
