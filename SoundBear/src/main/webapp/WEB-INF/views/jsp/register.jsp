<jsp:include page="login_header.jsp"></jsp:include>

<!-- START REGISTER JS FUNCTIONS -->
<script>
    function validate() {
        var usernameRegex = new RegExp("^[a-zA-Z]+[a-zA-Z0-9_]*$");
        var emailRegex = new RegExp("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        var username = $("#username").val();
        var email = $("#email").val();
        var password1 = $("#password1").val();
        var password2 = $("#password2").val();

        var isValidUsername;
        var isValidEmail;
        var validatePassword;

        $.ajax({
            url: 'validateRegisterForm',
            type: 'POST',
            data: JSON.stringify({
                "username": username,
                "email": email
            }),
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            async: false,
            success: function(data) {

                if (username) {
                    if (data.isValidUsername && usernameRegex.test(username)) {
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
                    if (data.isValidEmail && emailRegex.test(email)) {
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
            error: function(data) {
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
                url: 'registerSubmit',
                type: 'POST',
                data: JSON.stringify({
                    "username": username,
                    "email": email,
                    "password1": password1,
                    "password2": password2
                }),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                success: function(data) {

                    if (data.status === 'OK') {
                        alert(data.msg);
                        window.location = 'index';
                    } else {
                        alert(data.msg);
                        window.location = 'register';
                    }
                },
                error: function(data) {
                    alert(data);
                }
            });
        } else {
            $(".fields-required").show();
        }
    }

</script>
<!-- START REGISTER JS FUNCTIONS -->
<title>SoundBear Register</title>
</head>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-12">

                <div class="wrap">
                    <p class="form-title">Sign Up</p>
                    <div class="login">

                        <input type="text" id="username" placeholder="Username" required="required" maxlength="45" onblur="validate()" /> <span class="username-error">Username taken or invalid. </span> <span class="username-success">Username available. </span> <input type="text" id="email" placeholder="Email" required="required" maxlength="45" onblur="validate()" /> <span class="email-error">Email
							taken or invalid. </span> <span class="email-success">Email
							available. </span> <input type="password" id="password1" placeholder="Password" required="required" maxlength="45" onblur="validate()" /> <input type="password" id="password2" placeholder="Confirm Password" required="required" maxlength="45" onblur="validate()" /> <span class="password-error">Passwords
							don't match. </span> <span class="password-success">Passwords
							match. </span>


                        <div class="btn-group btn-group-justified">
                            <div class="btn-group">
                                <button id="signup" class="btn btn-primary btn-warning btn-sm" onclick="register()">SIGN UP</button>
                            </div>
                        </div>

                        <span class="fields-required">Please fill out all fields.</span>

                        <div class="remember-forgot"></div>

                        <div class="fb-login-button" data-max-rows="1" data-size="xlarge" data-show-faces="false" data-auto-logout-link="false"></div>
                        <div class="fb-login-button" data-max-rows="1" data-size="xlarge" data-show-faces="false" data-auto-logout-link="false"></div>
                    </div>

                    <div id="fb-root"></div>

                </div>

            </div>
        </div>
    </div>

</body>
<jsp:include page="footer.jsp"></jsp:include>
