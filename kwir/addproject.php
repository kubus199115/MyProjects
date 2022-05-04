<?php
session_start();

if(!isset($_SESSION['login'])) {
	header('Location: login.php');
	exit();
}
?>
<!DOCTYPE html>
<html lang="pl">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
		<title>PANEL ADMINA - DODANIE PROJEKTU</title>

		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/panelstyle.css" rel="stylesheet">
	</head>
	
	<body>
	
	<div class="container">
		<div class="row">
		  <div class="col-xs-12">
			<h1>Panel Administratora</h1><hr/>
			<h2>Dodanie Projektu</h2><hr/>
			<form action="checkaddproject.php" method="post">
				Tytuł: <br /><input type="text" size="50" maxlength="512" name="title" /> <br />
				Opis: <br /><textarea cols="52" rows="10" maxlength="512" name="descrip"></textarea> <br />
				Warunki przystąpienia: <br /><textarea cols="52" rows="10" maxlength="1024" name="conditions"></textarea> <br />
				Realizator: <br /><input type="text" size="50" maxlength="255" name="producer" /> <br />
				Link: <br /><input type="text" size="50" maxlength="256" name="link" /> <br />
				<input class="btn btn-lg btn-primary addbutton" type="submit" value="Dodaj" />
			</form>
		  </div>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="js/bootstrap.min.js"></script>
	
	</body>
</html>