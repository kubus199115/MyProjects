<?php
session_start();

if(!isset($_SESSION['login'])) {
	header('Location: login.php');
	exit();
}

if(!isset($_GET['toedit'])) {
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

$toedit = $_GET['toedit'];
$toedit = htmlentities($toedit, ENT_QUOTES, "UTF-8");
$toedit = mysqli_real_escape_string($con, $toedit);

$idtoedit;
$titletoedit;
$descriptoedit;
$conditionstoedit;
$producertoedit;
$linktoedit;

$sql = "select * from projects where id=".$toedit;

if ($result=mysqli_query($con, $sql)) {
		if(mysqli_num_rows($result) > 0) {
			while ($row=mysqli_fetch_row($result))
			{
				$idtoedit = $row[0];
				$titletoedit = $row[1];
				$descriptoedit = $row[2];
				$conditionstoedit = $row[3];
				$producertoedit = $row[4];
				$linktoedit = $row[5];
			}
			mysqli_free_result($result);
		}
		else {
			mysqli_free_result($result);
			mysqli_close($con);
			header('Location: adminpanel.php');
			exit();
		}
}

mysqli_close($con);

?>
<!DOCTYPE html>
<html lang="pl">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
		<title>PANEL ADMINA - EDYCJA PROJEKTU</title>

		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/panelstyle.css" rel="stylesheet">
	</head>
	
	<body>
	
	<div class="container">
		<div class="row">
		  <div class="col-xs-12">
			<h1>Panel Administratora</h1><hr/>
			<h2>Edycja Projektu</h2><hr/>
			<form action="checkeditproject.php" method="post">
				<input type="hidden" value="<?php echo $idtoedit ?>" name="id" />
				Tytuł: <br /><input type="text" size="50" maxlength="512" value="<?php echo $titletoedit ?>" name="title" /> <br />
				Opis: <br /><textarea cols="52" rows="10" maxlength="512" name="descrip"><?php echo $descriptoedit ?></textarea> <br />
				Warunki przystąpienia: <br /><textarea cols="52" rows="10" maxlength="1024" name="conditions"><?php echo $conditionstoedit ?></textarea> <br />
				Realizator: <br /><input type="text" size="50" maxlength="255" value="<?php echo $producertoedit ?>" name="producer" /> <br />
				Link: <br /><input type="text" size="50" maxlength="256" value="<?php echo $linktoedit ?>" name="link" /> <br />
				<input class="btn btn-lg btn-primary addbutton" type="submit" value="Zatwierdź zmiany" />
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