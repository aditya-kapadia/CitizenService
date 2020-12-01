<head>
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://www.gstatic.com/firebasejs/4.9.1/firebase.js"></script>
		<link rel="stylesheet" type="text/css" href="circlestyler.css">
		<link rel="stylesheet" type="text/css" href="styler.css">
</head>
<header>
<?php
include('navbar.php');
?>
</header>
<style type="text/css">
	button:disabled,
button[disabled]{
  border: 1px solid #999999;
  background-color: #cccccc;
  color: #666666;
}
h2{
	font-size: 17px;
}
button{
    background-color: white !important;
    color: black !important;
    border: 2px solid #D3D3D3 !important; 
    display: block!important;
    white-space: normal !important;
    word-wrap: break-word !important;
}
button:hover{
	background-color: #A9A9A9 !important;
	color: white;
}
</style>
<body>
<body>
<script src="firebase.js"></script>
<div class="table-responsive" id="pothole-data">
<h2><strong>Pothole Data</h2>
<table class="table fixed" id="table_id">
	<thead>
		<tr>
		<th>Url</th>
		<th>Address</th>
		<th>Coordinates</th>
		<th>Values</th>
		<th>Output</th>
		<th>Date Added</th>
		<th>Time Added</th>
		<th>Status</th>
		</tr>
		</thead>
	<tbody id="table_body1"></tbody>
</table></strong>
<script type="text/javascript">
$('#pothole-data').hide();
</script>
</div>
<div class="table-responsive" id="waste-data">
<h2><strong>Sanitation Data</h2>
<table class="table">
	<thead>
		<tr>
		<th>Url</th>
		<th>Address</th>
		<th>Coordinates</th>
		<th>Values</th>
		<th>Output</th>
		<th>Date Added</th>
		<th>Time Added</th>
		<th>Status</th>
		</tr>
		</thead>
	<tbody id="table_body2"></tbody>
</table></strong><script type="text/javascript">
$('#waste-data').hide();
</script>
</div>
<div class="table-responsive" id="resent-data">
<h2><strong>Resent Data</h2>
<table class="table">
	<thead>
		<tr>
		<th>Url</th>
		<th>Address</th>
		<th>Coordinates</th>
		<th>Values</th>
		<th>Output</th>
		<th>Date Added</th>
		<th>Time Added</th>
		<th>Status</th>
		</tr>
		</thead>
	<tbody id="table_body3"></tbody>
</table></strong><script type="text/javascript">

$('#resent-data').hide();</script>
</div>
<script>
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!

var yyyy = today.getFullYear();
if(dd<10){
    dd='0'+dd;
} 
if(mm<10){
    mm='0'+mm;
} 
var today = dd+'/'+mm+'/'+yyyy;
var timeis = new Date().toLocaleTimeString();
var userkey=[];
var peruser = firebase.database().ref().child("users")
peruser.once('value',function(snapshot){
/*	snapshot.forEach(function(childSnapshot){*/
	var userkey = Object.keys(snapshot.val());
	console.log(userkey[0]);
	var i=0;
	for(i=0;i<=userkey.length;i++){
var retest=1;
var rootRef3=peruser.child(userkey[i]).child("images").child("resendImages");
rootRef3.on("child_added",snap=>{
	var url=snap.child("imageUrl").val();
	console.log(url);
	var add=snap.child("imageLocationAddress").val();
	var cord=snap.child("imageLocationValues").val();
	var wd=snap.child("widthAndHeight").val();
	var category=snap.child("output").val();
	var date=snap.child("imageDate").val();
	var timee = snap.child("imageTime").val();
	var verify = snap.child("verified").val();
	var completed = snap.child("completed").val();
	var feedback = snap.child("feedBack").val();
	if(completed=="yes"){
/*	$("#table_body3").append("<tr><td><a href="+url+" target = '_blank' </a>View Image</td> <td>"+add+"</td><td>"+cord+"</td><td>"+wd+"</td><td>"+category+"</td><td>"+date+"</td><td>"+timee+"</td><td><button data-id="+retest+" disabled=true>Data Sent</button></td><td>"+verify+"</td><td><button id="+retest+">View FeedBack</button></td></tr>");*/
	}
	else{
		$("#resent-data").show();
	$("#table_body3").append("<tr><td><a href="+url+" target = '_blank' </a>View Image</td> <td>"+add+"</td><td>"+cord+"</td><td>"+wd+"</td><td>"+category+"</td><td>"+date+"</td><td>"+timee+"</td><td><button data-id="+retest+">Issue Solved</button></td></tr>");	
	}

	retest++;
});
}
$('#table_body3').on('click','button[data-id]',function(e){
	var datakey = $(this).attr('data-id');
	rootRef3.child(datakey).update({completed:"yes"});
	rootRef3.child(datakey).update({completedDate:today});
	rootRef3.child(datakey).update({completedTime:timeis});
	var outputdata;
	var emailidea;
	var coord;
	rootRef3.child(datakey).child('output').once('value',function(snapshot){
		outputdata = snapshot.val();
	});
	rootRef3.child(datakey).child('imageLocationAddress').once('value',function(snapshot){
		coord = snapshot.val();
	})
	rootRef3.child(datakey).child('user_id').once('value',function(snapshot){
		emailidea = snapshot.val();
		window.open("mailsend.php?email="+emailidea+"&output="+outputdata+"&coord="+coord,"mywindow","menubar=1,resizable=1,width=350,height=350");
	});
		window.location.reload();
	});
});
/*$('#table_body3').on('click','button[id]',function(e){
	var datakey = $(this).attr('id');
	var emailidea,feedback;
	rootRef3.child(datakey).child('user_id').once('value',function(snapshot){
		emailidea = snapshot.val();});
	rootRef3.child(datakey).child('feedBack').once('value',function(snapshot){
		feedback = snapshot.val();});
	window.open("feedback.php?email="+emailidea+"&feedback="+feedback,"mywindow");
});*/
</script>