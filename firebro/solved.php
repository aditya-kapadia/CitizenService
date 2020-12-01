<html>
<head>
		<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="https://www.gstatic.com/firebasejs/4.9.1/firebase.js"></script>
		<link rel="stylesheet" type="text/css" href="styler.css">
<title>User Data</title>
</head>
<header>
<?php
include('navbar.php');
?>
</header>
<style type="text/css">
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
	button:disabled,
button[disabled]{
  border: 1px solid #999999 !important;
  background-color: #cccccc !important;
  color: #666666 !important;
}
</style>
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
		<th>Verified?</th>
		<th>FeedBack</th>
		</tr>
		</thead>
	<tbody id="table_body1"></tbody>
</table></strong><script type="text/javascript">
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
		<th>Verified?</th>
		<th>FeedBack</th>
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
		<th>Verified?</th>
		<th>FeedBack</th>
		</tr>
		</thead>
	<tbody id="table_body3"></tbody>
</table></strong><script type="text/javascript">
$('#resent-data').hide();</script>
</div>
<script type="text/javascript">
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
var testing=1;
var userkey = localStorage.getItem("userdata");
console.log(userkey);
var rootRef=firebase.database().ref().child("users").child(userkey).child("images").child("potholes");
rootRef.on("child_added",snap=>{
	var url=snap.child("imageUrl").val();
	var add=snap.child("imageLocationAddress").val();
	var cord=snap.child("imageLocationValues").val();
	var wd=snap.child("widthAndHeight").val();
	var category=snap.child("output").val();
	var date=snap.child("imageDate").val();
	var email = snap.child("emailId");
	var timee = snap.child("imageTime").val();
	var verify = snap.child("verified").val();
	var completed = snap.child("completed").val();
	var feedback = snap.child("feedBack").val();
	if(completed=="yes"){
		$("#pothole-data").show();
	$("#table_body1").append("<tr><td><a href="+url+" target = '_blank' </a>View Image</td> <td>"+add+"</td><td>"+cord+"</td><td>"+wd+"</td><td>"+category+"</td><td>"+date+"</td><td>"+timee+"</td><td><button class = "+testing+" data-id="+testing+" disabled=true>Data Sent</button></td><td>"+verify+"</td><td><button id="+testing+">View FeedBack</button></td></tr>");
}
else{
	}
/*		var completeref = rootRef.child(testing);
		completeref.on("child_added",snap=>{
  			console.log(completeref);
    		var completecheck = snap.child("completed").val();
    		if(completecheck=="yes"){
    			$("button[id]").prop('disabled', true);
    	}
    		else{
    			$("button[id]").prop('disabled', false);
    		}
    	});*/
	testing++;
});
/*$('#table_body1').on('click','button[data-id]',function(e){
	var datakey = $(this).attr('data-id');
	rootRef.child(datakey).update({completed:"yes"});
	rootRef.child(datakey).update({completedDate:today});
	var outputdata;
	var emailidea;
	var coord;
	rootRef.child(datakey).child('output').once('value',function(snapshot){
		outputdata = snapshot.val();
	});
	rootRef.child(datakey).child('imageLocationAddress').once('value',function(snapshot){
		coord = snapshot.val();
	})
	rootRef.child(datakey).child('user_id').once('value',function(snapshot){
		emailidea = snapshot.val();
		window.open("mailsend.php?email="+emailidea+"&output="+outputdata+"&coord="+coord,"mywindow","menubar=1,resizable=1,width=350,height=350");
	});
		window.location.reload();
	});*/

$('#table_body1').on('click','button[id]',function(e){
	var datakey = $(this).attr('id');
	var emailidea,feedback;
	rootRef.child(datakey).child('user_id').once('value',function(snapshot){
		emailidea = snapshot.val();});
	rootRef.child(datakey).child('feedBack').once('value',function(snapshot){
		feedback = snapshot.val();});
	window.open("feedback.php?email="+emailidea+"&feedback="+feedback,"mywindow");
});
/*$('#table_body1').on('click','button[id]',function(e){
	var emailkey = $(this).attr('id');
	rootRef.child(emailkey).child('emailId').once('value',function(snapshot){
		emailidea = snapshot.val();
		console.log(emailidea);
		localStorage.setItem("emailis",emailidea);
		window.open("mailsend.php?email="+emailidea,"mywindow","menubar=1,resizable=1,width=350,height=350");
	});
	});*/
	// window.location.reload();
var wastetest=1;
var rootRef2=firebase.database().ref().child("users").child(userkey).child("images").child("sanitation");
rootRef2.on("child_added",snap=>{
	var url=snap.child("imageUrl").val();
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
		$("#waste-data").show();
	$("#table_body2").append("<tr><td><a href="+url+" target = '_blank' </a>View Image</td> <td>"+add+"</td><td>"+cord+"</td><td>"+wd+"</td><td>"+category+"</td><td>"+date+"</td><td>"+timee+"</td><td><button data-id="+wastetest+" disabled=true>Data Sent</button></td><td>"+verify+"</td><td><button id="+wastetest+">View FeedBack</button></td></tr>");

	}
	else{
}
	wastetest++;
});
/*$('#table_body2').on('click','button[data-id]',function(e){
	var datakey = $(this).attr('data-id');
	rootRef2.child(datakey).update({completed:"yes"});
	rootRef2.child(datakey).update({completedDate:today});
var outputdata;
	var emailidea;
	var coord;
	rootRef2.child(datakey).child('output').once('value',function(snapshot){
		outputdata = snapshot.val();
	});
	rootRef2.child(datakey).child('imageLocationAddress').once('value',function(snapshot){
		coord = snapshot.val();
	})
	rootRef2.child(datakey).child('user_id').once('value',function(snapshot){
		emailidea = snapshot.val();
		window.open("mailsend.php?email="+emailidea+"&output="+outputdata+"&coord="+coord,"mywindow","menubar=1,resizable=1,width=350,height=350");
	});
		window.location.reload();
	});
*/
$('#table_body2').on('click','button[id]',function(e){
	var datakey = $(this).attr('id');
	var emailidea,feedback;
	rootRef2.child(datakey).child('user_id').once('value',function(snapshot){
		emailidea = snapshot.val();});
	rootRef2.child(datakey).child('feedBack').once('value',function(snapshot){
		feedback = snapshot.val();});
	window.open("feedback.php?email="+emailidea+"&feedback="+feedback,"mywindow");
});



var retest=1;
var rootRef3=firebase.database().ref().child("users").child(userkey).child("images").child("resendImages");
rootRef3.on("child_added",snap=>{
	var url=snap.child("imageUrl").val();
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
		$("#resent-data").show();
	$("#table_body3").append("<tr><td><a href="+url+" target = '_blank' </a>View Image</td> <td>"+add+"</td><td>"+cord+"</td><td>"+wd+"</td><td>"+category+"</td><td>"+date+"</td><td>"+timee+"</td><td><button data-id="+retest+" disabled=true>Data Sent</button></td><td>"+verify+"</td><td><button id="+retest+">View FeedBack</button></td></tr>");
	}
	else{
	}
	retest++;
});
/*$('#table_body3').on('click','button[data-id]',function(e){
	var datakey = $(this).attr('data-id');
	rootRef3.child(datakey).update({completed:"yes"});
	rootRef3.child(datakey).update({completedDate:today});
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
*/
$('#table_body3').on('click','button[id]',function(e){
	var datakey = $(this).attr('id');
	var emailidea,feedback;
	rootRef3.child(datakey).child('user_id').once('value',function(snapshot){
		emailidea = snapshot.val();});
	rootRef3.child(datakey).child('feedBack').once('value',function(snapshot){
		feedback = snapshot.val();});
	window.open("feedback.php?email="+emailidea+"&feedback="+feedback,"mywindow");
});
</script>