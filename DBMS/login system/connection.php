<?php
//assignment ng value, pwede tanggalin yung port since dafault nya ay 3306 nilagay ko lang akin kasi iba port ko
$dbhost = "localhost";
$dbuser = "root";
$dbpass = "";
$dbname = "login_db";
$dbport = "4306";

//pangcheck kung may connection sa db and also assignment na rin ng connection
if(!$con = mysqli_connect($dbhost,$dbuser,$dbpass,$dbname,$dbport)){

	die("failed to connect!" . mysqli_error($con));
}

?>
