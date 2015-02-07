<?php
	
	$dbHost = "mysql.cis.ksu.edu";
	$dbName = "proj_myfields";
	
	$userName = "proj_myfields";
	$password = "----";
	
	$db = new PDO('mysql:host=' . $dbHost . ';dbname=' . $dbName . ';charset=utf8', 
				   $userName, 
				   $password);
					
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
	
	
	try
	{
		$field;
		
		foreach($db->query("select * from FIELDS where Owner = '" . $user->uid . "'" as $row)
		{
			$field = new Field('stuff');
			
			foreach($db->query("select * from PESTSAMPLES where FieldId = '" . $field->getID() . "'" as $sample)
			{
				$field->addSample(new PestSample('stuff'));
			}
			
			echo $field->JSONize();
		}
	}
	catch(PDOException $problem)
	{
		echo 'Problem connecting to database: ' . $problem;
	}
?>