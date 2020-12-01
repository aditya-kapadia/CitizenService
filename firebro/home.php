<html>
<head>
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://www.gstatic.com/firebasejs/4.9.1/firebase.js"></script>
		<link rel="stylesheet" type="text/css" href="circlestyler.css">
		<link rel="stylesheet" type="text/css" href="styler.css">
<title>User Page</title>
<style>
button{
    background-color: white !important;
    color: black !important;
    border: 2px solid #D3D3D3 !important; 
    width: 265px !important;
    display: block!important;
    white-space: normal !important;
    word-wrap: break-word !important;
}
button:hover{
	background-color: #A9A9A9 !important;
	color: white;
}
.table{
    width: 9% !important;
    margin-left: auto !important;
    margin-right: auto !important;
}
</style>
</head>
<header>
<?php
include('navbar.php');
?>
</header>
<body>
<script src="firebase.js"></script>
<div class="chart" id="graph" data-percent="1" style="width: 100; height: 100;"></div>
<div class="chart" id="graph1" data-percent="1" style="width: 100; height: 100;"></div><br>
<div class="table-responsive">
<table class="table" style="width: 100%";>
	<thead>
		<tr class="space">
		<th>User ID</th>
		<th></th>
		<th></th>
		</tr>
		</thead>
	<tbody id="table_body"></tbody><br>
</table>
</div>
<script src = "dataload.js"></script>
<script src = "circle.js"></script>
<!-- <script type="text/javascript">
var ref=firebase.database().ref().child("users");
var userid;
/*ref.once('value',function(snapshot){
	snapshot.forEach(function(childSnapshot)
	{
		userid = childSnapshot.key;
		$("#table_body").append("<button data-id="+userid+">"+userid+"</button><br>");
	

});
*/

ref.on("child_added",snap=>{
	userid = snap.key;
		$("#table_body").append("<button data-id="+userid+">"+userid+"</button><br>");
});
	$('#table_body').on('click','button[data-id]',function(e){
	var userkey = $(this).attr('data-id');
	console.log(userkey);
	alert(userkey);
	localStorage.setItem("userdata",userkey);
	window.location.href = "testingdata.php";
	
});
var potholecount,wastecount;
var upvotesRef = firebase.database().ref().child("ReportedPotholeIssues");
var upvotesRef1 = firebase.database().ref().child("ReportedSanitationIssues");
upvotesRef.once('value',function(snapshot){
		var potholecount = snapshot.numChildren();
		console.log(potholecount);
});
upvotesRef1.once('value',function(snapshot){
		var wastecount = snapshot.numChildren();
});

var el = document.getElementById('graph'); // get canvas

var options = {
    percent:  el.getAttribute('data-percent') || 25,
    size: el.getAttribute('data-size') || 220,
    lineWidth: el.getAttribute('data-line') || 15,
    rotate: el.getAttribute('data-rotate') || 0
}

var canvas = document.createElement('canvas');
var span = document.createElement('span');
span.textContent = potholecount;
    
if (typeof(G_vmlCanvasManager) !== 'undefined') {
    G_vmlCanvasManager.initElement(canvas);
}

var ctx = canvas.getContext('2d');
canvas.width = canvas.height = options.size;

el.appendChild(span);
el.appendChild(canvas);

ctx.translate(options.size / 2, options.size / 2); // change center
ctx.rotate((-1 / 2 + options.rotate / 180) * Math.PI); // rotate -90 deg

//imd = ctx.getImageData(0, 0, 240, 240);
var radius = (options.size - options.lineWidth) / 2;

var drawCircle = function(color, lineWidth, percent) {
		percent = Math.min(Math.max(0, percent || 1), 1);
		ctx.beginPath();
		ctx.arc(0, 0, radius, 0, Math.PI * 2 * percent, false);
		ctx.strokeStyle = color;
        ctx.lineCap = 'round'; // butt, round or square
		ctx.lineWidth = lineWidth
		ctx.stroke();
};

drawCircle('#efefef', options.lineWidth, 100 / 100);
drawCircle('#555555', options.lineWidth, options.percent / 100);
var handle = setInterval(function() {
	options.percent = potholecount;
var digit = potholecount.toString()[0];
   if(options.percent > 100){
    options.percent = potholecount-(digit*100);
   }
 /* if(options.percent > 100) {
     options.percent = testme-(digit*100); 
  }*/
  drawCircle('#555555', options.lineWidth, options.percent / 100);
  span.textContent = 'Potholes: '+potholecount;
}, 10000);
</script> -->