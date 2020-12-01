<html>
<head>
<link rel="stylesheet" type="text/css" href="styler.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://www.gstatic.com/firebasejs/4.9.1/firebase.js"></script>
<script type="text/javascript" src="firebase.js"></script>
<?php
include 'navbar.php';
$lastemail = $_GET["email"];
$out = $_GET["feedback"];
?>
<title>FeedBack Page</title>
</head>
<body>
<?php 
if($out=="No feedback")
{
	echo("No feedback given at User End");
}
else if($out==null){
	echo("No feedback given at User End");
}
else if($out=="null"){
	echo("No feedback given at User End");
}
else{
?>
	<div id="feedback" style="width: 100%">
		<div align ="center" class="login", id="login" style="word-wrap: break-word; width: 100%; height: 400px;">
		<h2>FeedBack</h2>
		<h3 align="left">Email ID:<?php echo($lastemail); ?></h3>
		<br><br><br>
	<p><?php
	echo($out);
	?></p></div></div>
<div id="mailer" style="width: 100%">
			<div align ="center" class="login", id="login" style="width: 90%; height: 400px;">
			<h2>Reply</h2>
			<textarea style="width: 90%; height: 200px;" id="text">
			</textarea>
			<button align="right" style="width: 120px;" id="send">Send</button>
<script>
	$('#send').on('click',function(e){
		var reply = document.getElementById("text").value;
		var lastemail = "<?php echo $lastemail ?>";
		var out = "<?php echo $out ?>";
		window.open("replier.php?email="+lastemail+"&feedback="+reply+"&out="+out,"mywindow","menubar=1,resizable=1,width=350,height=350");
	});
</script>
<?php 
}
?>
</body>