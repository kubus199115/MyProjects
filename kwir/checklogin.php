<?php

session_start();

if((!isset($_POST['login'])) || (!isset($_POST['haslo']))) {
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

$login = $_POST['login'];
$haslo = $_POST['haslo'];

$login = htmlentities($login, ENT_QUOTES, "UTF-8");
$haslo = htmlentities($haslo, ENT_QUOTES, "UTF-8");

//$sql = "select * from users where login='$login' and pswd='$haslo'";
if ($result=mysqli_query($con, sprintf("select * from users where login='%s' and pswd='%s'",
	mysqli_real_escape_string($con, $login),
	mysqli_real_escape_string($con, $haslo)))) 
	{
		if(mysqli_num_rows($result) > 0) {
			$_SESSION['login'] = true;
			unset($_SESSION['loginError']);
			mysqli_free_result($result);
			header('Location: adminpanel.php');
		}
		else {
			$_SESSION['loginError'] = '<span style="color:red">Nieprawidłowe dane logowania</span>';
			header('Location: login.php');
		}
	}

mysqli_close($con);

?>