<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<%@ page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->


<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/login.min.css" rel="stylesheet">

<script type="text/javascript" src="<c:url value="/script/jquery-3.1.1.min.js"/>"></script>


<script type="text/javascript" src="<c:url value="/script/login.js"/>"></script>

<script src="<c:url value = "/script/bootstrap.min.js"/>"></script>

<!-- START LOGIN JS FUNCTIONS -->
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
<!-- END OF LOGIN JS FUNCTIONS -->
<!-- START REGISTER JS FUNCTIONS -->
<script>
    function validate() {
        var usernameRegex = new RegExp("^[a-zA-Z]+[a-zA-Z0-9_.]*$");
        var emailRegex = new RegExp("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        var passwordRegex = new RegExp ("[a-zA-Z0-9_.!@#$%^&*()]*$");

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

        if (password1 == password2 && passwordRegex.test(password1) && password1.length > 4) {
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



