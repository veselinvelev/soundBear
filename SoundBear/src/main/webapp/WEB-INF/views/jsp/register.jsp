<%@ include file="login_header.jsp"%>


<title>SoundBear Register</title>
</head>

<body>

	<div class="container">
		<div class="row">
			<div class="col-md-12">

				<div class="wrap">
					<p class="form-title">Sign Up</p>
					<div class="login">

						<input type="text" id="username" placeholder="Username"
							required="required" maxlength="45" onblur="validate()"
							required="required"
							onkeydown="if (event.keyCode == 13)
		                        document.getElementById('signup').click()" />
						<span class="username-error">Username taken or invalid. </span> <span
							class="username-success">Username available. </span> <input
							type="text" id="email" placeholder="Email" required="required"
							maxlength="45" onblur="validate()" onkeydown = "if (event.keyCode == 13)
		                        document.getElementById('signup').click()"  /> <span class="email-error">Email
							taken or invalid. </span> <span class="email-success">Email
							available. </span> <input type="password" id="password1"
							placeholder="Password" required="required" maxlength="45"
							onblur="validate()"
							onkeydown="if (event.keyCode == 13)
		                        document.getElementById('signup').click()" />
						<input type="password" id="password2"
							placeholder="Confirm Password" required="required" maxlength="45"
							onblur="validate()" onkeydown = "if (event.keyCode == 13)
		                        document.getElementById('signup').click()" /> <span class="password-error">Passwords
							don't match. </span> <span class="password-success">Passwords
							match. </span>


						<div class="btn-group btn-group-justified">
							<div class="btn-group">
								<button id="signup" class="btn btn-primary btn-warning btn-sm"
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

</body>
<%@ include file="footer.jsp"%>
