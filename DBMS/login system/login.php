<?php 

session_start();

	include("connection.php");
	include("functions.php");

	//pag may nilagay sa post/textbox
	if($_SERVER['REQUEST_METHOD'] === 'POST'){

		$user_name = $_POST['user_name'];
		$password = $_POST['password'];

		if(!empty($user_name) && !empty($password) && !is_numeric($user_name)){

			//pangread sa database, nagkakaroon ng pop up si google kapag may @gmail.com sa username
			$query = "select * from users where user_name = '$user_name' limit 1";
			$result = mysqli_query($con, $query);

			if($result){
				if($result && mysqli_num_rows($result) > 0){

					$user_data = mysqli_fetch_assoc($result);
					
					if($user_data['password'] === $password && $user_data['user_name'] === $user_name){

						$_SESSION['user_id'] = $user_data['user_id'];
						header("Location: index.php");
						die;

					}else{
						echo "wrong username or password!";
						
					}
				}
			}if(!$result){
				echo "Account not found!";
			}
		}
	}

?>



<!DOCTYPE html>
<html>
<head>
	<title>Login</title>
	<style>
		body {
			background-color: #f2f2f2;
			font-family: Arial, sans-serif;
		}

		#login-box {
			background-color: #fff;
			border-radius: 5px;
			box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
			margin: 50px auto;
			padding: 40px;
			max-width: 400px;
			text-align: center;
		}

		#login-box h1 {
			color: #333;
			font-size: 2rem;
			margin-bottom: 30px;
		}

		#login-box input[type="text"],
		#login-box input[type="password"] {
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

		#login-box input[type="submit"] {
			background-color: #4CAF50;
			border: none;
			border-radius: 5px;
			color: #fff;
			font-size: 1.2rem;
			padding: 10px 20px;
			margin-top: 20px;
			cursor: pointer;
		}

		#login-box input[type="submit"]:hover {
			background-color: #45a049;
		}

		#signup-link {
			color: #4CAF50;
			font-size: 1.2rem;
			margin-top: 20px;
			text-decoration: none;
			display: block;
		}

		#signup-link:hover {
			text-decoration: underline;
		}
	</style>
</head>
<body>
	<div id="login-box">
		<h1>Login</h1>
		<form method="post">
			<input type="text" name="user_name" placeholder="Enter username here" required><br><br>
			<input type="password" name="password" placeholder="Enter password here" required><br><br>
			<input type="submit" value="Login">
		</form>
		<a id="signup-link" href="signin.php">Click to Signup</a>
	</div>
</body>
</html>