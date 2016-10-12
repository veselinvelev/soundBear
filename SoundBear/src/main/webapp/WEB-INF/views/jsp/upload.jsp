<%@ include file="header.jsp" %>
    <title>SoundBear Upload</title>
    <script>
        function validate(song) {
            var ext = song.split(".");
            ext = ext[ext.length - 1].toLowerCase();
            var arrayExtensions = ["mp3"];

            if (typeof FileReader !== "undefined") {
    			var size = document.getElementById('my-file-selector').files[0].size;
    			if (size > 20971520) { //20MB
    				alert("The file size exceeds the 20MB limit.");
    				$("#my-file-selector").val("");
    				return;
    			}
    		}
            
            if (arrayExtensions.lastIndexOf(ext) == -1) {
                alert("Only mp3 files are allowed.");
                $("#my-file-selector").val("");
                return;
            }

           
            
            $('#upload-file-info').html($("#my-file-selector").val());
        }


        $(document).ready(function() {
            $("form").submit(function(e) {
                var artist = $("#artist").val();
                var songName = $("#name").val();
                var genre = $("#genre").val();
                var fileName = $('#my-file-selector').val();
                
                var validationRegex = new RegExp("^[a-zA-Z0-9 ]+[a-zA-Z ]+[a-zA-z0-9 ]$");
                
                $(".invalid-artist").hide();
                $(".invalid-song").hide();
                
                if (artist && songName && genre != "empty" && fileName.length) {
                	$(".choose-file").hide();
                    $(".fields-required").hide();
                	
                	if(!validationRegex.test(artist)){
                		$(".invalid-artist").show();
                		return false;
                	}
                	
                	if(!validationRegex.test(songName)){
                		$(".invalid-song").show();
                		return false;
                	}
                	
                    return true;
                } else if (!fileName.length) {
                    $(".choose-file").show();
                    $(".fields-required").hide();
                    return false;
                } else {
                    $(".choose-file").hide();
                    $(".fields-required").show();
                    return false;
                }
            });
        });
        
    </script>


    </head>



    <body>
        <div class="container">

            <br />
            <form name="form" enctype="multipart/form-data" method="post" action="songUpload">
                <div class="row">
                    <h2 class="text-warning col-md-6 col-md-push-4">Upload a song</h2>
                </div>

                <br />

                <div class="row">
                    <div class="form-group col-xs-3 col-md-push-4">

                        <label class="text-warning"> Artist: </label> <input type="text" class="form-control" id="artist" name="artist" maxlength="45" />

                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-xs-3 col-md-push-4">

                        <label class="text-warning"> Song name: </label> <input type="text" class="form-control" id="name" name="name" maxlength="45"/>

                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-xs-3 col-md-push-4">
                        <label class="text-warning"> Genre: </label>
                        <select class="form-control" id="genre" name="genre">

						<option value="empty"></option>
						<option value="pop">Pop</option>
						<option value="rock">Rock</option>
						<option value="rap">Rap</option>
						<option value="jazz">Jazz</option>
						<option value="house">House</option>
						<option value="classical">Classical</option>
						<option value="karaoke">Karaoke</option>

					</select>

                    </div>
                </div>
                

                <div class="row">
                    <div class="btn-group col-xs-3 col-xs-push-4">
                        <label class=" btn  btn-warning btn-sm" for="my-file-selector"> <input
						id="my-file-selector" type="file" name ="song" accept="audio/*"
						style="display: none;" onchange="validate(this.value)">
						Choose file
					</label>

                        <button id="submit" type="submit" class="btn btn-warning btn-sm col-xs-push-5" onclick="uploadSong()">Upload</button>

                    </div>

                    <!--           </div>-->


                    <!--     <div class="row">
                <!-- 
                <div class="btn-group col-md-6">
                    <button id="submit" type="submit" class="btn btn-warning btn-warning btn-sm" onclick="uploadSong()">Upload</button>
                </div>
-->

                    <!--   <button id="submit" type="submit" class="btn btn-warning btn-warning btn-sm" onclick="uploadSong()">Upload</button>
-->

                </div>


                <div class="row">
                    <br /> <br />
                    <div class="col-md-9 col-md-push-4">
                        <span class='label label-warning' id="upload-file-info"></span>
                    </div>

                    <br />
                </div>

                <br /> <span class="fields-required col-md-2 col-md-push-4" style="display: none; color: red;">Please fill out all
				fields.</span>

                <span class="choose-file col-md-2 col-md-push-4" style="display: none; color: red;">Please choose a file.</span>
                
                <span class="invalid-song col-md-2 col-md-push-4" style="display: none; color: red;">Invalid song name.</span>
                
                <span class="invalid-artist col-md-2 col-md-push-4" style="display: none; color: red;">Invalid artist.</span>
                
                

            </form>

        </div>
    </body>

    <%@ include file="footer.jsp" %>
