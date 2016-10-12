<%@ include file="header.jsp"%>
<title>SoundBear My Followers</title>
<script>
	
	$(document).ready(function() {
		
		var parameter = window.location.search.replace( "?", "" ); // will return the GET parameter 

		var values = parameter.split("=");

		
		$.ajax({
			type : 'GET',
			url : 'listFollowers?item=' + values[1],
			dataType : 'json',
			success : function(data) {

				$.each(data.followers, function(index, follower) {
					var a = document.createElement('a');
					$("#tbody").append(+ "<td>");
					a.href = 'viewProfile?id=' + follower.userId;
					$("#tbody").append("<tr>"+ 
							" <th scope=\"row\">"+
							(index + 1)+
							"</th>"+ "<td>"+
							follower.username+
							"</td>"+ "<td>"
							+ follower.email
							+ "</td> <td><a href=\""+a+"\">View Profile</a></td>"
																		);

				});

			},
			error : function(code, message) {
				//
			}
		});

	});
</script>

</head>


<body>
	<div class="col-md-6 col-md-push-1" id="mySongs">


		<h2><c:out value="${loggedUser.username}" /> <c:out value="${item}" /></h2>
		<table class="table table-hover" id="table">
			<thead>
				<tr>
					<th>#</th>
					<th>Username</th>
					<th>Email</th>
					<th></th>
				</tr>
			</thead>

			<tbody id="tbody">



			</tbody>

		</table>

	</div>

	<div id="error"></div>
</body>

<%@ include file="footer.jsp"%>