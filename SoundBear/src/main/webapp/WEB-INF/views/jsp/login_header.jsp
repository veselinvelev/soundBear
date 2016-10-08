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
<!-- END OF LOGIN JS FUNCTIONS -->




