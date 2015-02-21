<?php
	
	include_once "Field.php";
	include_once "PestSample.php";
	include_once "GPSLocation.php";
	include_once "Planting.php";
	
	// Database access information
	$dbHost = "mysql.cis.ksu.edu";
	$dbName = "proj_myfields";	
	$userName = "proj_myfields";
	$password = "insecurepassword";
	
	// Username provided by requestor to access fields within DB
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
		
		if($userQuery->rowCount() == 0)
		{
			http_response_code(401);
			echo "Username or password is incorrect!";
			return;
		}
		else
		{
			// get the userId to associate to the Fields
			$uid = $userQuery->fetch()["uid"];
		}
	
		if ($_SERVER['REQUEST_METHOD'] == 'GET')
		{
				$fields = array();
				$uid;
				$count = 0;
				
				// Get Fields associated with a user's ID
				$statement = $db->query("select * from Fields where Owner = '" . $uid . "'");
				
				//echo "{\n[\n";
				
				foreach($statement as $row)
				{
					$field = new Field($row["Index"], $row["Name"], $row["Latitude"], $row["Longitude"],
										$row["Size"], $row["Soil"], $row["Tillage"], $row["Irrigation"]);
					
					// Add each planting
					foreach($db->query("select * from Plantings where Field = '" . $field->getID() . "'") as $planting)
					{
						$field->addPlanting(new Planting($planting["Id"], $planting["Crop"], $planting["Variety"],
														$planting["Density"], $planting["Notes"], date('Y-m-d', strtotime($planting["Date"]))));
					}
					
					// Add each pest sample
					foreach($db->query("select * from PestSamples where FieldId = '" . $field->getID() . "'") as $sample)
					{
						// This should be a 1-to-1 relationship
						foreach($db->query("select * from GreenbugSamples where GenericSampleId = '" . $sample["Id"] . "'") as $specificSample)
						{
							$field->addPestSample(new GreenbugSample($specificSample["Id"], $sample["Latitude"], $sample["Longitude"], 
																$sample["FieldID"], $sample["ControlCost"], $sample["CropValue"],
																$sample["Notes"], explode(",", $sample["OtherPests"]),
																$specificSample["AphidCount"], $specificSample["MummyCount"], $specificSample["TreatmentRecommendation"]));
																
						}
					}
					
					array_push($fields, $field);
					
					
					
					/*echo $field->JSONize("\t");
					
					if($count < ($statement->rowCount() - 1))
					{
						echo ",\n";
					}
					
					$count++;*/
				}
				echo json_encode($fields, JSON_PRETTY_PRINT);
				//echo "\n]\n}";
		}
		else if($_SERVER['REQUEST_METHOD'] == 'POST')
		{
			
		}
		else
		{
			http_response_code(405);
			echo "Unrecognized REQUEST type!";
			return;
		}
			
	}	
	catch(PDOException $problem)
	{
		echo 'Problem connecting to database: ' . $problem;
	}
?>