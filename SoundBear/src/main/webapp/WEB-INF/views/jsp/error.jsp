<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SoundBear Error</title>
</head>

<style>

body{
	font-size:18px;
}



.error {
  margin: 0 auto;
  text-align: center;
  font-size:18px;
}

.error-code {
  bottom: 60%;
  color: #2d353c;
  font-size: 96px;
  line-height: 100px;
  font-size:18px;
}

.error-desc {
  font-size: 12px;
  color: #647788;
  font-size:18px;
}

.m-b-10 {
  margin-bottom: 10px!important;
  font-size:18px;
}

.m-b-20 {
  margin-bottom: 20px!important;
  font-size:18px;
}

.m-t-20 {
  margin-top: 20px!important;
  font-size:18px;
}

</style>

<body>
<div class="error">
        <img src="<c:url value = "/img/error_pic.jpg"/>" width="400" height="400" >
        <h3 class="font-bold">We couldn't find the page..</h3>

        <div class="error-desc">
            Sorry, but the page you are looking for was either not found or does not exist. <br/>
            Try refreshing the page or click the button below to go back to the Homepage.
            <div>
                <a class=" login-detail-panel-button btn" href="play">
                        <i class="fa fa-arrow-left"></i>
                        Go back to Homepage                        
                    </a>
            </div>
        </div>
    </div>
</body>
</html>