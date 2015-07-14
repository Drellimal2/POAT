<?php
    $dbhost = "localhost";
	$dbuser = "root";
    $dbname = "poat";
	$connection = mysql_connect($dbhost, $dbuser);
	if (!$connection) {
		echo "no connection";
		die("Connection failed");
		
	}
	else{
		$database = mysql_select_db($dbname);
		$username = (isset($_POST['username']))? $_POST['username']:"";
		$email = (isset($_POST['email']))? $_POST['email']:"";
		$password = (isset($_POST['password']))? $_POST['password']:"";
		$confirmpassword = (isset($_POST['confirmPassword']))? $_POST['confirmPassword']:"";
		$username = mysql_real_escape_string($username);
		$email = mysql_real_escape_string($email);
		$password = mysql_real_escape_string($password);
		$confirmpassword = mysql_real_escape_string($confirmpassword);
		$matchedpassword = '';
		if ($password === $confirmpassword){
			$matchedpassword = $password;
		}
		else{
			echo "Incorrect data";
		}
		if (($username && $matchedpassword) != ""){
			$sql_insert = "INSERT INTO users(Username, Email, Password, ConfirmPassword) VALUES('$username','$email', '$matchedpassword','$matchedpassword')";
			$insert = mysql_query($sql_insert);
			if ($insert){
				$redirect = 'index.html';
				header("Location: $redirect");
				die();
			}
			else{
				echo "Error in creating user";
			}
		}
	}
?>