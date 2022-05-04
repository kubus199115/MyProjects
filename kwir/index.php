<?php

require_once('dbdata.php');

$con = mysqli_connect($host, $dbuser, $dbpassword, $dbname);

if (!$con) {
    echo "Error: Unable to connect to MySQL." . PHP_EOL;
    echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
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
	
		<title>KWALIFIKACJE - INTELIGENTNY ROZWÓJ</title>

		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/mystyle.css" rel="stylesheet">
	</head>
	
	<body>
	
	<!--ogłoszenia-->
	<div class="news" id="newslink">
		<button type="button" class="btn btn-sm closebutton" id="closebuttonid">X</button>
		<h2 class="main">Tablica ogłoszeń</h2>
		<!-- Wyświetlamy ogłoszenia z bazy danych -->
		<?php
			$sql = "select title, content, dateadd from news order by dateadd desc";
			if ($result=mysqli_query($con,$sql))
			  {
			  // Fetch one and one row
			  while ($row=mysqli_fetch_row($result))
				{
				//printf ("%s (%s) %s\n",$row[0],$row[1],$row[2]);
				echo '<h3>'.$row[0].'</h3>';
				echo '<p class="lead date">Data dodania: '.$row[2].'</p>';
				echo '<p class="lead content">'.$row[1].'</p><hr/>';
				}
			  // Free result set
			  mysqli_free_result($result);
			}
		?>
	</div>
	
	<!-- menu ruchome we własnym stylu -->
	<nav class="navbar navbar-expand-md navbar-custom fixed-top">
		<div class="container">
		  <a class="navbar-brand" href="index.php">
			<img src="img/logoarek_male.png" />
		  </a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample03" aria-controls="navbarsExample03" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		  </button>

		  <div class="collapse navbar-collapse" id="navbarsExample03">
			<ul class="navbar-nav ml-auto">
			  <li class="nav-item">
				<a class="nav-link scroll_to" href="#aboutmelink">O MNIE</a>
			  </li>
			  <li class="nav-item">
				<a class="nav-link scroll_to" href="#schoollink">SZKOLENIA</a>
			  </li>
			  <li class="nav-item">
				<a class="nav-link scroll_to" href="#projectslink">PROJEKTY</a>
			  </li>
			  <li class="nav-item">
				<a class="nav-link scroll_to" href="#contactlink">KONTAKT</a>
			  </li>
			</ul>
		  </div>
		</div>
	</nav>
	
	<!-- główne zdjecie -->
	<div class="container-fluid main-photo">
		<div class="row">
			<div class="col-lg-6 caption">
				<button class="btn btn-lg btn-primary infobutton" id="infobuttonid">Pokaż ogłoszenia</button>
				<h1 class="littletown">Kutno &middot; Gostynin &middot; Łęczyca</h1>
				<h1>KWALIFIKACJE - INTELIGENTNY ROZWÓJ</h1><hr/>
				<h1 class="little">szkolenia i doradztwo</h1>
				<h1 class="ab">ARKADIUSZ BAJDA</h1>
				<img class="pass" src="img/pass.png" /><br />
				<button class="btn btn-primary facebutton"><a href="#" class="facebooklink"><img src="img/facebook.png" /></a></button>
			</div>
		</div>				
	</div>
	
	<!-- 3 kolumny - główne cechy -->
	<div class="container-fluid marketing">
        <div class="row">
			<div class="container">
				<div class="row">
				  <div class="col-lg-4">
					<img src="img/organizacjaikona.png" alt="Generic placeholder image">
					<h2>Organizacja projektów</h2>
					<p class="lead">Zajmuję się organizowaniem projektów na terenie Kutna i okolic. Współpracuje z zaufanymi firmami, które gwarantują realizację na najwyższym poziomie. Dzęki temu, uzyskują Państwo dostęp do projektów skierowanym do różnych grup wiekowych oraz społecznych.</p>
				  </div><!-- /.col-lg-4 -->
				  <div class="col-lg-4">
					<img src="img/szkoleniaikona.png" alt="Generic placeholder image">
					<h2>Prowadzenie szkoleń</h2>
					<p class="lead">Prowadzę zajęcia z przedmiotów związanych z administracją biurową. Są to wykłady m. in. z ergonomii pracy, obsługi urządzeń biurowych, prowadzenie dokumentacji. Moje wykształcenie i doświadczenie zapewnią Państwu pozyskanie potrzebnej wiedzy w miłej atmosferze.</p>
				  </div><!-- /.col-lg-4 -->
				  <div class="col-lg-4">
					<img src="img/doradztwoikona.png" alt="Generic placeholder image">
					<h2>Doradztwo zawodowe</h2>
					<p class="lead">Pomagam uczestnikom projektów w uzyskaniu zatrudnienia. Staram się wyselekcjonować dla Państwa oferty najlepiej dopasowane do doświadczenia i przebytej kariery zawodowej. Oferuje również pomoc w przygotowaniu profesjonalnie wyglądającego CV. </p>
				  </div><!-- /.col-lg-4 -->
				</div>
			</div>
		</div><!-- /.row -->
	</div>
	
	<!-- O mnie -->
	<div class="container-fluid aboutme" id="aboutmelink">
        <div class="row">
			<div class="container">
				<div class="row">
				  <div class="col-xs-12">
					<h1>O mnie</h1>
					<img class="rounded-circle img-fluid" src="img/boss.png" alt="Generic placeholder image" width="256" height="256">
					<p class="lead">Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh.</p>
				  </div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- projekty -->
	<div class="container-fluid projects" id="projectslink">
		<div class="row">
			<div class="container">
				<div class="row title">
					<div class="col-xs-12">
						<h1>Projekty</h1>
						<h2 class="lead desc">Aktualnie organizowane projekty</h2>
						<img class="projimg img-fluid" src="img/kola.png" alt="Generic placeholder image">
						
						<div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner">
							
						  
						
						<!-- Wyświetlamy projekty z bazy danych -->
						<?php	
							$sql = "select title, descrip, conditions, producer, link from projects";
							if ($result=mysqli_query($con,$sql))
							  {
								$i = 1;
							  // Fetch one and one row
							  while ($row=mysqli_fetch_row($result))
								{
									if($i==1)
										echo '<div class="carousel-item active">';
									else
										echo '<div class="carousel-item">';
								echo '<div class="proj">';
								echo '<h2>'.$row[0].'</h2>';
								echo '<p class="lead">'.$row[1].'</p>';
								echo '<h4>Warunki uczestnictwa</h4>';
								echo '<p class="lead">'.$row[2].'</p>';
								echo '<h3>Realizator projektu: '.$row[3].'</h3>';
								echo '<a href="'.$row[4].'" class="projlink">Zobacz szczegóły</a>';
								echo '</div>';
								echo '</div>';
								$i++;
								}
							  // Free result set
							  mysqli_free_result($result);
							  }
						?>

							</div>
							<a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						    </a>
						    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						    </a>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- szkolenia -->
	<div class="container-fluid school2" id="schoollink">
		<div class="row">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 schooldesc">
						<h1>Szkolenia</h1>
						<p class="lead">Organizowane przeze mnnie projekty umożliwiają odbycie szkoleń o róznorodnej tematyce. 
						Każdy z nich jest starannie przygotowywany pod kątem materiału, tak aby jak najlepiej pzygotowywał do podjęcia pracy. 
						Również prowadzący zajęcia to osoby, które w swoich dziedzinach pochwalić się mogą dużą wiedzą i doświadczeniem.</p>
						<img class="womanimg img-fluid" src="img/woman.png" />
						<h3>Profesjonalni wykładowcy</h3>
						<p class="lead">Szkolenia prowadzą odpowiedni specjaliści, np. księgowe, przedsiębiorcy, eksperci prawa pracy</p>
						<img class="practiceimg img-fluid" src="img/praktyka.jpg" />
						<h3>Zajęcia praktyczne</h3>
						<p class="lead">Dużo praktycznej wiedzy, np. wypełnianie dokumentów, praca z ksero czy kasą fiskalną</p>
						<h1 class="popschool">Popularne szkolenia</h1>
					</div>
				</div>
				<div class="row schooltwocol justify-content-center">
					<div class="col-lg-5 admbiu">
						<h2 class="titleproj">Pracownik administracyjno-biurowy</h2>
						<h3>Nauczysz się m.in.</h3>
						<ul>
						<li class="lead">Organizacja pracy w biurze</li>
						<li class="lead">Informacja w pracy biurowej</li>
						<li class="lead">Profesjonalna obsługa klienta</li>
						</ul>
					</div>
					<div class="col-lg-5 offset-lg-1 admgos">
						<h2 class="titleproj">Pracownik administracyjno-gospodarczy</h2>
						<h3>Nauczysz się m.in.</h3>
						<ul>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						</ul>
					</div>
				</div>
				<div class="row schooltwocol justify-content-center">
					<div class="col-lg-5 specadm">
						<h2 class="titleproj">Pracownik ds. administracji i sprzedaży</h2>
						<h3>Nauczysz się m.in.</h3>
						<ul>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						</ul>
					</div>
					<div class="col-lg-5 offset-lg-1 szk">
						<h2 class="titleproj">Szkolenie</h2>
						<h3>Nauczysz się m.in.</h3>
						<ul>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						<li class="lead">Jeszcze nie wiem czego się nauczysz</li>
						</ul>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 schoolbuttoncol">
						<a href="szkolenia.php" class="btn btn-lg btn-primary schoolbutton">Zobacz szczegóły szkoleń</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- kontakt -->
	<div class="container-fluid contact" id="contactlink">
        <div class="row">
			<div class="container">
				<div class="row">
				  <div class="col-xs-12">
					<h1>Kontakt</h1><hr/>
					<h2 class="lead">KWALIFIKACJE - INTELIGENTNY ROZWÓJ<br />
					szkolenia i doradztwo<br/> ARKADIUSZ BAJDA</h2>
					<h2 class="lead"><img src="img/location.png" />ul. Władysława Jagiełły 1 lok. 15, 99-300 Kutno</h2>
					<h2 class="lead"><img src="img/phone.png" />607 788 948</h2>
					<h2 class="lead"><img src="img/email.png" />kwalifikacje-ir@wp.pl</h2>
				  </div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- stopka -->
	<div class="container-fluid footer">
        <div class="row">
			<div class="container">
				<div class="row">
				  <div class="col-lg-6">
					<ul>
						<a href="index.php" class="contactli"><li class="lead">Strona główna</li></a>
						<a href="index.php#aboutmelink" class="contactli"><li class="lead">O mnie</li></a>
						<a href="index.php#projectslink" class="contactli"><li class="lead">Projekty</li></a>
						<a href="index.php#schoollink" class="contactli"><li class="lead">Szkolenia</li></a>
						<a href="index.php#contactlink" class="contactli"><li class="lead">Kontakt</li></a>
					</ul>					
				  </div>
				  <div class="col-lg-6">
					<img src="img/logofoot.png" />
					<h2 class="lead">KWALIFIKACJE - INTELIGENTNY ROZWÓJ<br />
					szkolenia i doradztwo<br/> ARKADIUSZ BAJDA &copy; 2019</h2>
				  </div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="js/bootstrap.min.js"></script>
	<script src="js/mainscripts.js"></script>
	
	</body>
</html>

<?php
	mysqli_close($con);
?>