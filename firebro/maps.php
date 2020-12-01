<!DOCTYPE html>
<html>
<head>
<script src="https://www.gstatic.com/firebasejs/4.9.1/firebase.js"></script>
<link rel="stylesheet" type="text/css" href="styler.css">	
	<title>Maps</title>
	<header>
	<?php
	include('navbar.php');
	?>
	<style>
		* {
			margin: 20;
			padding: 0;
		}
		#map {
			height: 600px;
			width: 100%;
		}
	</style>
	<script type="text/javascript" src="firebase.js"></script>
</head>
<body>
<!-- <div id="imgDiv">
<img id="imagename"></img></div> -->
<div id="map"></div>

<script>
	function initMap() {
		var pinImagePot = new google.maps.MarkerImage("http://www.googlemapsmarkers.com/v1/0000ff/");
		var pinImageSan = new google.maps.MarkerImage("http://www.googlemapsmarkers.com/v1/ff0000/");
		var test = {lat: 19.0760, lng: 72.8777};
		var img;
		var map = new google.maps.Map(document.getElementById("map"), {
			zoom: 10,
			center: test
		});
		var rootRef=firebase.database().ref().child("ReportedPotholeIssues");
		rootRef.on("child_added",snap=>{
			var name = snap.child("userName").val();
			var lat = snap.child("latitude").val();
			var long = snap.child("longitude").val();
			var img = snap.child("image").val();
		var location = {lat: lat, lng: long};
		console.log(lat);
		var marker = new google.maps.Marker({
			icon: pinImagePot,
			position: location,
			map: map,
			title: name
		});
/*document.getElementById('imgDiv').innerHTML='<img src=\''+img+'\'>'*/
		var infowindow = new google.maps.InfoWindow({
    content: "<a href = http://maps.google.com/maps?q="+lat+","+long+" target = '_blank'>"+name+"</a>"
});
		google.maps.event.addListener(marker, 'click', function() {
  		infowindow.open(map,marker);
	});
	});
		var rootRef1 =firebase.database().ref().child("ReportedSanitationIssues"); 
			rootRef1.on("child_added",snap=>{
			var name = snap.child("userName").val();
			var lat = snap.child("latitude").val();
			var long = snap.child("longitude").val();
		var location = {lat: lat, lng: long};
		console.log(lat);
		var marker = new google.maps.Marker({
			icon: pinImageSan,
			position: location,
			map: map,
			title: name
		});
		var infowindow = new google.maps.InfoWindow({
    content: "<a href = http://maps.google.com/maps?q="+lat+","+long+" target = '_blank'>"+name+"</a>"
});
		google.maps.event.addListener(marker, 'click', function() {
  		infowindow.open(map,marker);
	});
	});
	/*	google.maps.event.addListener(marker, 'click', function() {
  		infowindow.open(map,marker);
});*/
	};
</script>
<!-- <script type="text/javascript" src="firebase.js"></script> -->
<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCEfHmVBhwD39ni0oWCvfBDtYh3Lv8Y7MU&callback=initMap"></script>

</body>
</html>