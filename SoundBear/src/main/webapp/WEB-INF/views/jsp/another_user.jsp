
<%@ include file="header.jsp"%>
<title>SoundBear Profile</title>
<style>
img {
	max-width: 100%;
	height: auto;
}

.item {
	width: 200px;
	min-height: 200px;
	max-height: auto;
	float: left;
	margin: 3px;
	padding: 3px;
}

.btn-file {
	position: relative;
	overflow: hidden;
}

.btn-file input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	min-width: 100%;
	min-height: 100%;
	font-size: 100px;
	text-align: right;
	filter: alpha(opacity = 0);
	opacity: 0;
	outline: none;
	background: white;
	cursor: inherit;
	display: block;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {

		var parameter = window.location.search.replace("?", ""); // will return the GET parameter 

		var values = parameter.split("=");

	//	alert(values[1]);

		$.ajax({
			type : 'GET',
			url : 'checkFollowStatus?id=' + values[1],
			dataType : 'json',
			success : function(data) {

				if (data.status == 'OK') {
					$("#follow").hide();
					$("#unfollow").show();

				} else {

					$("#follow").show();
					$("#unfollow").hide();

				}

			},
			error : function(code, message) {
			}
		});

	});
	
	
	function follow() {
		
		var parameter = window.location.search.replace("?", ""); // will return the GET parameter 

		var values = parameter.split("=");
		
			$.ajax({
				url : 'updateFollow?id=' + values[1] + '&action=follow',
				type : 'GET',
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					
					window.location = 'viewProfile?id=' + values[1];
			
				},
				error : function(data) {
					window.location = 'error';
				}

			});
	}
	
function unfollow() {
		
		var parameter = window.location.search.replace("?", ""); // will return the GET parameter 

		var values = parameter.split("=");
		
			$.ajax({
				url : 'updateFollow?id=' + values[1] + '&action=unfollow',
				type : 'GET',
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					window.location = 'viewProfile?id=' + values[1];
			
				},
				error : function(data) {
				
					window.location = 'error';
				}

			});
	}
	
	
</script>

<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">

		<br> <br>
		<div class="container-fluid well span6">
			<div class="row">
				<div class="item span2 col-sm-2 col-md-2">
					<img src="${anotherUser.photo}" alt=""
						class="img-rounded img-responsive" />
				</div>
				<div class="span2 col-md-4 col-sm-4">
					<h3>
						<c:out value="${anotherUser.username}" />
					</h3>
					<h6>
						Email:
						<c:out value="${anotherUser.email}" />
					</h6>

					<div class="col-md-4  row btn-group ">
						<button class="btn btn-primary btn-info btn-md" id="follow"
							style="display: none" onclick="follow()">Follow</button>
						<button class="btn btn-primary btn-info btn-md" id="unfollow"
							style="display: none" onclick="unfollow()">Unfollow</button>
					</div>
				</div>










			</div>






			<!--
				<button class="btn btn-success btn-block">
					<span class="fa fa-plus-circle"></span> Follow
				</button>
			</div>
			<div class="col-xs-12 col-sm-4 emphasis">
				<br>
				<h2>
					<strong>245</strong>
				</h2>
				<p>
					<small>Following</small>
				</p>
				<button class="btn btn-info btn-block">
					<span class="fa fa-user"></span> View Profile
				</button>
			</div>

		</div>
		-->
		</div>
	</div>
</body>
<%@ include file="footer.jsp"%>
