<?php
	
	// Database access information
	$dbHost = "mysql.cis.ksu.edu";
	$dbName = "proj_myfields";	
	$userName = "proj_myfields";
	$password = "insecurepassword";
	
	// Username/pass provided by requestor
	$FieldUser;
	$FieldPassword;
	
	if(isset($_REQUEST['user']) && isset($_REQUEST['pass']))
	{
		$FieldUser = $_GET['user'];
		$FieldPassword = $_GET['pass'];
	}
	else
	{
		http_response_code(401);
		echo "Username or Password not provided!";
		return;
	}
	
	try
	{
	
		$db = new PDO('mysql:host=' . $dbHost . ';dbname=' . $dbName . ';charset=utf8', 
					   $userName, 
					   $password);
						
		$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
		
		// Requestor should provide username and password
		$userQuery = $db->query("select * from users 
												where name = '" . $FieldUser . 
												"' and pass = '" . $FieldPassword . "'");
		
		if($userQuery->rowCount() === 0)
		{
			http_response_code(401);
			echo "Username or password is incorrect!";
			
		}
		else
		{
			http_response_code(200);
		}
	}	
	catch(PDOException $problem)
	{
		echo 'Problem connecting to database: ' . $problem;
	}
?>