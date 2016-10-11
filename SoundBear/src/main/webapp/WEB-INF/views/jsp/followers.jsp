<%@ include file="header.jsp"%>
<title>SoundBear My Followers</title>
<script>
	/*	$(document).ready(
	 function() {
	 $.ajax({
	 type : 'GET',
	 url : 'listMySongs',
	 dataType : 'json',
	 success : function(data) {

	 $.each(data.mySongs, function(index, song) {

	 $("#tbody").append("<tr>" +
	 " <th scope=\"row\">"+(index+1)+"</th>"+
	 "<td>"+song.artist+"</td>"+
	 "<td>"+song.songName+"</td>"+
	 "<td>"+song.genre+"</td>"+
	 "<td><audio controls>"+
	 "<source src="+song.path+" type=\"audio/mpeg\">"+
	 "</audio></td>"+
	 "</tr>");

	 });

	 },
	 error : function(code, message) {
	 $('#error').html(
	 'Error Code: ' + code + ', Error Message: '
	 + message);
	 }
	 });

	 });
	 */

	/* 	function sortSongs(){
	 var criteria = $("#sortBy").val();
	
	 $.ajax({
	 type : 'GET',
	 url : 'sortMySongs/'+criteria,
	 dataType : 'json',
	 success : function(data) {

	 $.each(data.mySongs, function(index, song) {

	 $("#tbody").append("<tr>" +
	 " <th scope=\"row\">"+(index+1)+"</th>"+
	 "<td>"+song.artist+"</td>"+
	 "<td>"+song.songName+"</td>"+
	 "<td>"+song.genre+"</td>"+
	 "<td><audio controls>"+
	 "<source src="+song.path+" type=\"audio/mpeg\">"+
	 "</audio></td>"+
	 "</tr>");

	 });

	 },
	 error : function(code, message) {
	 $('#error').html(
	 'Error Code: ' + code + ', Error Message: '
	 + message);
	 }
	 });
	
	
	 } */

	$(document)
			.ready(
					function() {
						$
								.ajax({
									type : 'GET',
									url : 'listFollowers',
									dataType : 'json',
									success : function(data) {

										$
												.each(
														data.followers,
														function(index,
																follower) {

															$("#tbody")
																	.append(
																			"<tr>"
																					+ " <th scope=\"row\">"
																					+ (index + 1)
																					+ "</th>"
																					+ "<td>"
																					+ follower.username
																					+ "</td>"
																					+ "<td>"
																					+ follower.email
																					+ "</td>"
																					+ "<td>"
																					+ "<a href=\"viewProfile?id=3\">View Profile</a>"
																					+ "</td>"
																		+ "</tr>");

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

		
		<h2>Following Me</h2>
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