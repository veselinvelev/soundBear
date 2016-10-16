<%@ include file="login_header.jsp" %>

<title>SoundBear Login</title>
</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="pr-wrap">

                    <div class="pass-reset">

                        <label> Enter the email you signed up with</label> <input id="email" type="email" placeholder="Email" onkeydown = "if (event.keyCode == 13)
                        document.getElementById('restore-pass').click()" />
                        <div class="btn-group btn-group-justified">
                            <div class="btn-group">
                                <span id="email-not-found" class="email-not-found">Please
									enter valid email.</span>
                                <button class="btn btn-primary btn-warning btn-sm" id="restore-pass" onclick="restorePassword()">SUBMIT</button>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="wrap">
                    <p class="form-title">Sign In</p>
                    <div class="login">
                        <input id="username" name="username" type="text" placeholder="Username" required="required" onkeydown = "if (event.keyCode == 13)
                        document.getElementById('loginButton').click()"  /> <input id="password" name="password"
							type="password" placeholder="Password" required="required"
							onkeydown="if (event.keyCode == 13)
                        document.getElementById('loginButton').click()" />
						<div class="btn-group btn-group-justified">
                            <div class="btn-group">
                                <span id="login-error" class="login-error">Inalid
									username or password</span>
                                <button class="btn btn-primary btn-warning btn-sm" id="loginButton"onclick="login()">SIGN IN</button>
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
    <!-- Include all compiled plugins (below), or include individual files as needed 
	<script src="<c:url value = "/script/bootstrap.min.js"/>"></script>

-->
</body>

<%@ include file="footer.jsp" %>
