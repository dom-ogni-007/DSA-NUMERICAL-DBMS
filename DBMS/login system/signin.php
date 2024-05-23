<?php 
session_start();

	include("connection.php");
	include("functions.php");

    if (!$con) {
        die("Database connection failed: " . mysqli_connect_error());
    }
	//not sure kung may point ba yung htmlspecialchars dito pero nilagay ko na rin haha
	if(htmlspecialchars($_SERVER['REQUEST_METHOD'] == "POST")){
		//pangkuha uli value sa textbox
		$user_name = $_POST['user_name'];
		$password = $_POST['password'];

		if(!empty($user_name) && !empty($password) && !is_numeric($user_name)){

			//save sa database and panggenerate ng user_id, baguhin nalang yung limit kung gusto
			$user_id = random_num(20);
			$query = "insert into users (user_id,user_name,password) values ('$user_id','$user_name','$password')";

			if (mysqli_query($con, $query)) {
                header("Location: login.php");
                die;
            } else {
                echo "Error executing query: " . mysqli_error($con);
            }
		}else{
			echo "Please enter some valid information!";
		}
	}
?>


<!DOCTYPE html>
<html>
<head>
	<title>Sign In</title>
	<style>
		body {
			background-color: #f2f2f2;
			font-family: Arial, sans-serif;
		}

		#signin-box {
			background-color: #fff;
			border-radius: 5px;
			box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
			margin: 50px auto;
			padding: 40px;
			max-width: 400px;
			text-align: center;
		}

		#signin-box h2 {
			color: #333;
			font-size: 2rem;
			margin-bottom: 30px;
		}

		#signin-box input[type="text"],
		#signin-box input[type="password"] {
			background-color: #f2f2f2;
			border: none;
			border-radius: 5px;
			box-shadow: 0px 0px 5px rgba(0,0,0,0.1);
			display: block;
			font-size: 1.2rem;
			margin: 10px auto;
			padding: 10px;
			width: 80%;
		}

		#signin-box input[type="submit"] {
			background-color: #4CAF50;
			border: none;
			border-radius: 5px;
			color: #fff;
			font-size: 1.2rem;
			padding: 10px 20px;
			margin-top: 20px;
			cursor: pointer;
		}

		#signin-box input[type="submit"]:hover {
			background-color: #45a049;
		}

		#login-link {
			color: #4CAF50;
			font-size: 1.2rem;
			margin-top: 20px;
			text-decoration: none;
			display: block;
		}

		#login-link:hover {
			text-decoration: underline;
		}
	</style>
</head>
<body>
	<div id="signin-box">
		<h2>Sign Up</h2>
		<form method="post">
			<input type="text" name="user_name" placeholder="Enter username here" required><br><br>
			<input type="password" name="password" placeholder="Enter password here" required><br><br>
			<input type="submit" value="Sign Up"><br><br>
		</form>
		<a id="login-link" href="login.php">Click to Login</a>
	</div>
</body>
</html>

