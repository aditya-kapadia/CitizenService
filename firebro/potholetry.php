<html>
<head>
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://www.gstatic.com/firebasejs/4.9.0/firebase.js"></script>
<title>Pothole</title>
</head>
<header>
	<?php
	include('navbar.php');
	?>
</header>
<body>
<script src="firebase.js"></script>
<div class="table-responsive">
<table class="table">
	<thead>
		<tr>
		<th>User ID</th>
		</tr>
		</thead>
	<tbody id="table_body"></tbody>
</table>
</div>
<script>
var ref=firebase.database().ref().child("users");
ref.once('value',function(snapshot){
	var childKey;
	var keys=[];
	snapshot.forEach(function(childSnapshot)
	{
		childKey = childSnapshot.val();
		keys.push(childKey);
		/*childKey.push(childSnapshot.key);*/
		/*$("#table_body").append("<tr><td><a href='fulldata.php&uid="+childKey+"' target = '_blank' </a>"+childSnapshot.key+"</td></tr>");*/
	});

	for(i=0;i<keys.length;i++){
		console.log(keys.length);
		$("#table_body").append("<button id="+keys[i]+">Complete</button>");
	}

	$(keys).click(function(){
		console.log(childKey);
	});	
});
</script>	












</html>