<html>
<?php
$lastemail = $_GET["email"];
$otp = $_GET["feedback"];
$out = $_GET["out"];
$to = $lastemail;
$body = $otp;
send_mail($to, $body);


function send_mail($to, $body)
{
	date_default_timezone_set('Etc/UTC');
	require 'PHPMailer/class.phpmailer.php';
	require 'PHPMailer/PHPMailerAutoload.php';

	
	$mail = new PHPMailer(true);
	
	$mail->isSMTP();
	$mail->Host = 'smtp.live.com';
	$mail->SMTPAuth = true;
	$mail->Username = 'deepbluecsp3@hotmail.com';
	$mail->Password = 'Deepbluecsp@3';
	$mail->SMTPSecure = 'tls';
	$mail->Port = 587;
	$mail->Subject = "Your FeedBack Reply";
	
	$mail->From = 'deepbluecsp3@hotmail.com';
	$mail->FromName = 'CSP 3';
	$mail->addAddress($to);
	$mail->addReplyTo("deepbluecsp3@hotmail.com", 'Reply');
	
	$mail->isHTML(true); 
	
	$mail->Body    = $body;
	$mail->AltBody = 'Your issue which you had reported to us is now solved. Thank You, Team Mastek';
	
	if(!$mail->send())
	{
		echo 'Email could not be sent.';
	} 
	else 
	{
		echo"Email has been sent!!";
		?>
		<script type="text/javascript">
			window.close();
		</script>
		<?php
	}
}

?>