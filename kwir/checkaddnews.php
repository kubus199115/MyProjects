<?php
	session_start();
	
	if(!isset($_SESSION['login'])) {
		header('Location: login.php');
		exit();
	}
	
	if((!isset($_POST['title'])) || (!isset($_POST['content']))) {
		header('Location: adminpanel.php');
		exit();
	}
	
	require_once('dbdata.php');

	$con = mysqli_connect($host, $dbuser, $dbpassword, $dbname);

	if (!$con) {
		echo "Error: Unable to connect to MySQL." . PHP_EOL;
		echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
		exit;
	}

	if (!mysqli_set_charset($con, "utf8")) {
		printf("Error loading character set utf8: %s\n", mysqli_error($link));
		exit();
	}
	
	$title = $_POST['title'];
	$title = htmlentities($title, ENT_QUOTES, "UTF-8");
	$title = mysqli_real_escape_string($con, $title);
	
	$content = $_POST['content'];
	$content = htmlentities($content, ENT_QUOTES, "UTF-8");
	$content = mysqli_real_escape_string($con, $content);
	
	$info = '';
	
	$date = date('Y-m-d'); 
	$sql = "insert into news (title, content, dateadd) values ('".$title."', '".$content."', '".$date."')";
	if (mysqli_query($con, $sql)) 
	{
		$info = "Udało się dodać ogłoszenie.";
	}
	else 
	{
		$info = "Nie udało się dodać ogłoszenia. Coś poszło nie tak:(";
	}

	mysqli_close($con);
	
?>
<!DOCTYPE html>
<html lang="pl">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
		<title>PANEL ADMINA - DODANIE OGŁOSZENIA</title>

		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/panelstyle.css" rel="stylesheet">
	</head>
	
	<body>
	
	<div class="container">
		<div class="row">
		  <div class="col-xs-12">
			<h1>Panel Administratora</h1><hr/>
			<h2>Dodanie Ogłoszenia</h2><hr/>
			<p class="lead"><?php echo $info; ?></p>
			<a href="adminpanel.php" class="btn btn-lg btn-primary addbutton">Powrót do panelu administratora</a>
		  </div>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="js/bootstrap.min.js"></script>
	
	</body>
</html>