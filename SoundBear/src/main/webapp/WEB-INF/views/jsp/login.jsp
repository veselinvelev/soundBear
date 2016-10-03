<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<%@ page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>SoundBear Login</title>

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
<script type="text/javascript" src="<c:url value="/script/login.js"/>"></script>

<script type="text/javascript">
	function login() {
		var username = $("#username").val();
		var password = $("#password").val();

		if (username && password) {
			$.ajax({
				url : 'login',
				type : 'POST',
				data : JSON.stringify({
					"username" : username,
					"password1" : password
				}),
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {

					if (data.status === 'OK') {
						window.location = 'play';
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

	function restorePassword() {

		var email = $("#email").val();

		if (email) {
			$.ajax({
				url : 'resetPassword',
				type : 'POST',
				data : JSON.stringify({
					"email" : email,
				}),
				contentType : "application/json; charset=utf-8",
				dataType : 'json',
				success : function(data) {
					alert(data.status);

					if (data.status == 'OK') {
						alert("A new password has been sent to your email")
						window.location = 'index';
					} else {
						$(".email-not-found").show();
					}
				},
				error : function(data) {
					alert(data);
				}

			});
		}

	}
</script>



</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="pr-wrap">

					<div class="pass-reset">

						<label> Enter the email you signed up with</label> <input
							id="email" type="email" placeholder="Email" />
						<div class="btn-group btn-group-justified">
							<div class="btn-group">
								<span id="email-not-found" class="email-not-found">Please
									enter valid email.</span>
								<button class="btn btn-primary btn-success btn-sm"
									onclick="restorePassword()">SUBMIT</button>
							</div>
						</div>

					</div>
				</div>
				<div class="wrap">
					<p class="form-title">Sign In</p>
					<div class="login">
						<input id="username" name="username" type="text"
							placeholder="Username" required="required" /> 
							<input
							id="password" name="password" type="password"
							placeholder="Password" required="required" />
						<div class="btn-group btn-group-justified">
							<div class="btn-group">
								<span id="login-error" class="login-error">Inalid
									username or password</span>
								<button class="btn btn-primary btn-success btn-sm"
									onclick="login()">SIGN IN</button>
							</div>
						</div>





						<div class="signup-forgot">

							<div class="row">
								<div class="col-md-7  forgot-pass-content">
									<a href="#" class="forgot-pass">Forgot Password</a>
								</div>
								<div class="col-md-4  col-md-push-2 forgot-pass-content">
									<a href="register">Sign Up</a>
								</div>
							</div>

						</div>

					</div>


				</div>

			</div>
		</div>
	</div>



	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) 
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>-->
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="<c:url value = "/script/bootstrap.min.js"/>"></script>






</body>

</html>
