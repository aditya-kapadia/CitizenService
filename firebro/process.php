  <?php

$string = 'abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
$string_shuffled = str_shuffle($string);
$otp = substr($string_shuffled, 1, 7);


$to = $lastemail;

$body = $otp;

send_mail($to, $body);

function send_mail($to, $body)
{
	require 'PHPMailer/PHPMailerAutoload.php';
	
	$mail = new PHPMailer;
	
	$mail->isSMTP();
	$mail->Host = 'smtp.gmail.com';
	$mail->SMTPAuth = true;
	$mail->Username = 'djplaceme@gmail.com';
	$mail->Password = 'jiggybaby';
	$mail->SMTPSecure = 'ssl';
	$mail->Port = 465;
	
	$mail->From = 'djplaceme@gmail.com';
	$mail->FromName = 'Placement Portal';
	$mail->addAddress($to);
	$mail->addReplyTo("aditya.kapadia5@gmail.com", 'Reply');
	
	$mail->isHTML(true); 
	
	$mail->Body    = $body;
	$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';
	
	if(!$mail->send())
	{
		echo 'Email could not be sent.';
	} 
	else 
	{
		echo "<script>
		alert('Email has been sent');
		window.location.href = 'otpage';
		</script>";
		//redirect('index.html');
	}
}

?>