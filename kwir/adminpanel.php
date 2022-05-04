<?php
session_start();

if(!isset($_SESSION['login'])) {
	header('Location: login.php');
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

?>

<!DOCTYPE html>
<html lang="pl">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
	
		<title>PANEL ADMINA</title>

		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/panelstyle.css" rel="stylesheet">
	</head>
	
	<body>
	
	<div class="container">
		<div class="row">
		  <div class="col-xs-12">
			<h1>Panel Administratora</h1><a href="logout.php" class="btn btn-lg btn-danger">Wyloguj się</a><hr/>
			<h2>Ogłoszenia</h2><hr/>
			<?php
			$sql = "select id, title, content, dateadd from news order by dateadd";
			if ($result=mysqli_query($con,$sql))
			  {
			  // Fetch one and one row
			  while ($row=mysqli_fetch_row($result))
				{
				//printf ("%s (%s) %s\n",$row[0],$row[1],$row[2]);
				echo '<h3>'.$row[1].'</h3>';
				echo '<p class="lead">Data dodania: '.$row[3].'</p>';
				echo '<p class="lead">'.$row[2].'</p>';
				echo '<a href="editnews.php?toedit='.$row[0].'" class="btn btn-lg btn-primary rebutton">Edytuj</a>';
				echo '<a href="deletenews.php?todelete='.$row[0].'" class="btn btn-lg btn-primary rebutton">Usuń</a>';
				}
			  // Free result set
			  mysqli_free_result($result);
			}
			?>
			<br/><a href="addnews.php" class="btn btn-lg btn-warning addbutton">Dodaj nowe ogłoszenie</a><hr/>
			<h2>Projekty</h2><hr/>
			
			<?php	
				$sql = "select id, title, descrip, conditions, producer, link from projects";
				if ($result=mysqli_query($con,$sql))
				  {
				  // Fetch one and one row
				  while ($row=mysqli_fetch_row($result))
					{
					echo '<h3>'.$row[1].'</h3>';
					echo '<p class="lead">'.$row[2].'</p>';
					echo '<p class="lead">Warunki: '.$row[3].'</p>';
					echo '<p class="lead">Realizator projektu: '.$row[4].'</p>';
					echo '<p class="lead">Link: '.$row[5].'</p>';
					echo '<a href="editproject.php?toedit='.$row[0].'" class="btn btn-lg btn-primary rebutton">Edytuj</a>';
					echo '<a href="deleteproject.php?todelete='.$row[0].'" class="btn btn-lg btn-primary rebutton">Usuń</a>';
					}
				  // Free result set
				  mysqli_free_result($result);
				}
			?>				
			<br/><a href="addproject.php" class="btn btn-lg btn-warning addbutton">Dodaj nowy projekt</a><hr/>
		  </div>
		</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="js/bootstrap.min.js"></script>
	
	</body>
</html>

<?php
	mysqli_close($con);
?>