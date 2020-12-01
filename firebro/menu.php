<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
	<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">

		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<title>Home</title>
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<script src="js/modernizr.custom.js"></script>
	</head>
	<body>
		<div class="container">
			<ul id="gn-menu" class="gn-menu-main">
				<li class="gn-trigger">
					<a class="gn-icon gn-icon-menu"><span>Menu</span></a>
					<nav class="gn-menu-wrapper">
						<div class="gn-scroller">
							<ul class="gn-menu">
								<li><a class="gn-icon gn-icon-home" href="data.php">Home</a></li>
								<!-- <li class="no-cursor">
									<a class="gn-icon gn-icon-issues no-cursor">Issues</a></li> -->
									<ul class="gn-submen">
										<li><a class="gn-icon gn-icon-potholes">1. Potholes</a></li>
										<li><a class="gn-icon gn-icon-waste" href="wastev.php">2. Waste</a></li>
									</ul>
								</li>
							<!-- 	<li>
<a class="gn-icon gn-icon-reoccurence" href="repeated.html">Repeated</a></li> -->
								<li class="no-bullet"><button id="Logout"><a class="gn-icon gn-icon-logout">Logout</a></button></li>
								<!-- <script>
	Logout.addEventListener('click', e=>{
  firebase.auth().signOut();
  alert("Logout Success");
  window.location="index.html"
});
</script> -->
							</ul>
						</div><!-- /gn-scroller -->
					</nav>
				</li>
		<script src="js/classie.js"></script>
		<script src="js/gnmenu.js"></script>
		<script>
			new gnMenu( document.getElementById( 'gn-menu' ) );
		</script>
	</body>
</html>