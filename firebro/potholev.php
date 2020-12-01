<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
	<title>Pothole Data</title>
<!-- 
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Home</title>
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" >
		<link rel="stylesheet" type="text/css" href="css/demo.css" >
		<link rel="stylesheet" type="text/css" href="css/component.css" >
		<script src="js/modernizr.custom.js"></script>
 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"> -->
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- <link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed' rel='stylesheet' type='text/css'>
 -->
<!-- <link rel="stylesheet" type="text/css" href="style.css"> -->
<script src="https://www.gstatic.com/firebasejs/4.9.0/firebase.js"></script>
</head>
<header>
<?php
include('navbar.php');
?>
<script>
Logout.addEventListener('click', e=>{
firebase.auth().signOut();
alert("Logout Success");
window.location="index.html"
});
</script>
<!-- <script src="js/classie.js"></script>
		<script src="js/gnmenu.js"></script>
		<script>
			new gnMenu( document.getElementById( 'gn-menu' ) );
		</script> -->
		</ul>
		</div>
</header>
<body>
<div class="table-responsive">
<table class="table">
	<thead>
		<tr>
		<th>Image</th>
		<th>Address</th>
		<th>Coordinates</th>
		<th>Values</th>
		<!-- <th>Depth</th> -->
		<th>Category</th>
		<th>Date</th>
		<!-- <th>User</th> -->
		<th>Status</th>
		</tr>
	</thead>
<tbody id="table_body">
	<!-- <tr>
		<td id="url">Some data</td>
		<td id="add">Some data</td>
		<td id="cord">Some data</td>
		<td id="width">Some data</td>
		<td id="depth">Some data</td>
		<td id="category">Some data</td>
		<td id="date">Some data</td>
		<td id="user">Some data</td>
		<td id="status"><button id="complete" name="complete value="complete">Completed</button></td>
	</tr> -->
</tbody>
</table>
</div>
<script src="firebase.js"></script>
<script src="pothole.js"></script>
</body>