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
		
		if($userQuery->rowCount() === 0)
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
	
		if ($_SERVER['REQUEST_METHOD'] === 'GET')
		{
				$fields = array();
				$uid;
				$count = 0;
				
				// Get Fields associated with a user's ID
				$statement = $db->query("select * from Fields where Owner = '" . $uid . "'");
				
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
							$field->addPestSample(new GreenbugSample($specificSample["Id"], $specificSample['GenericSampleID'], $sample["Latitude"], $sample["Longitude"], 
																$sample["FieldID"], $sample["ControlCost"], $sample["CropValue"],
																$sample["Notes"], explode(",", $sample["OtherPests"]),
																$specificSample["AphidCount"], $specificSample["MummyCount"], $specificSample["TreatmentRecommendation"]));
																
						}
					}
					
					array_push($fields, $field);
				}
				echo json_encode($fields, JSON_PRETTY_PRINT);
		}
		else if($_SERVER['REQUEST_METHOD'] === 'POST')
		{
			// POSTING is incomplete.
			$Updates_Fields = array(8); // Number of columns to update
			$News_Fields = array(); //God this is easier than an update.
			
			$Updates_Plantings = array();
			$News_Plantings = array();
			
			$Updates_Samples = array();
			$News_Samples = array();
			
			$Updates_Samples_Greenbug = array();
			$News_Samples_Greenbug = array();
			
			$data = json_decode($_POST('data'));

			$rowCount;
			
			$Updates_Fields[0] = "SET Name = CASE Id ";
			$Updates_Fields[1] = "SET Latitude = CASE Id ";
			$Updates_Fields[2] = "SET Longitude = CASE Id ";
			$Updates_Fields[3] = "SET Size = CASE Id ";
			$Updates_Fields[4] = "SET Soil = CASE Id ";
			$Updates_Fields[5] = "SET Tillage = CASE Id ";
			$Updates_Fields[6] = "SET Irrigation = CASE Id ";
			$Updates_Fields[7] = "SET Owner = CASE Id ";
			
			foreach($data as $field)
			{
				
				$rowCount = $db->query("select count(*) as x from Fields where Index = '" . $field['ID'] . "'");
				
				if($rowCount['x'] > 0)
				{
					
					array_push($Updates_Fields, "(" . $field['ID'] . ", " . $field['FieldName'] . ", " . $field['Location']['Latitude'] . ", " . 
											$field['Location']['Longitude'] . ", " . $field['Size'] . ", " . $field['TypeOfSoil'] . ", " . 
											$field['TillageSystem'] . ", " . $field['IrrigationSystem'] . ")");
				}
				else
				{
					array_push($News_Fields, "(" . $field['ID'] . ", " . $field['FieldName'] . ", " . $field['Location']['Latitude'] . ", " . 
											$field['Location']['Longitude'] . ", " . $field['Size'] . ", " . $field['TypeOfSoil'] . ", " . 
											$field['TillageSystem'] . ", " . $field['IrrigationSystem'] . ", " . $uid . ")");
				}
				
				foreach($field['PlantingList'] as $planting)
				{
					$rowCount = $db->query("select count(*) as x from Plantings where Id = '" . $planting['ID'] . "'");
					
					if ($rowCount['x'] > 0)
					{
						array_push($Updates_Plantings, "(" . $planting['ID'] . ", " . $planting['CropType'] . ", " . $planting['CropVariety'] . ", " .
															$planting['CropDensity'] . ", " . $planting['Notes'] . ", " . 
															date('Y-m-d H:i:s', strtotime($planting['Date'])) . ")");
					}
					else
					{
						array_push($News_Plantings, "(" . $planting['ID'] . ", " . $planting['CropType'] . ", " . $planting['CropVariety'] . ", " .
															$planting['CropDensity'] . ", " . $planting['Notes'] . ", " . 
															date('Y-m-d H:i:s', strtotime($planting['Date'])) . ", " . 
															$field['ID'] . ")");
					}
				}
				
				foreach($field['PestSamples'] as $sample)
				{
					$rowCount = $db->query("select count(*) as x from PestSamples where Id = '" . $sample['GenericSample']['ID'] . "'");
					
					if ($rowCount['x'] > 0)
					{
						array_push($Updates_Samples, "(" . $sample['GenericSample']['ID'] . ", " . $sample['GenericSample']['Location']['Latitude'] . ", " . 
														$sample['GenericSample']['Location']['Longitude'] . ", " . $sample['GenericSample']['ControlCost'] . ", " . 
														$sample['GenericSample']['CropValue'] . ", " . $sample['GenericSample']['Notes'] . ", " . 
														implode(", ", $sample['GenericSample']['OtherPests']) . ")");
					}
					else
					{
						array_push($News_Samples, "(" . $sample['GenericSample']['ID'] . ", " . $sample['GenericSample']['Location']['Latitude'] . ", " . 
														$sample['GenericSample']['Location']['Longitude'] . ", " . $sample['GenericSample']['ControlCost'] . ", " . 
														$sample['GenericSample']['CropValue'] . ", " . $sample['GenericSample']['Notes'] . ", " . 
														implode(", ", $sample['GenericSample']['OtherPests']) . ", " . $field['ID'] . ")");
					}
					
					// Need an if-elseif structure for each type of sample, checking for a unique piece of data to that type.
					if(isset($sample['MummyCount']))
					{
						$rowCount = $db->query("select count(*) as x from GreenbugSamples where Id = '" . $sample['SpecificID'] . "'");
						
						if($rowCount['x'] > 0)
						{
							array_push($Updates_Samples_Greenbug, "(" . $sample['SpecificID'] . ", " . $sample['GenericSample']['ID'] . ", " .
																$sample['TreatmentRecommendation'] . ", " . $sample['AphidCount'] . ", " . 
																$sample['MummyCount'] . ")");
						}
						else
						{
							array_push($News_Samples_Greenbug, "(" . $sample['SpecificID'] . ", " . $sample['GenericSample']['ID'] . ", " .
																$sample['TreatmentRecommendation'] . ", " . $sample['AphidCount'] . ", " . 
																$sample['MummyCount'] . ")");
						}
						
					}
				}
			}
			
			// INSERT/UPDATE QUERIES HERE
			
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