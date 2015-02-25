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
			// MySQL update syntax is TERRIBLE
			$Updates_Fields[0] = "Name = CASE Index ";
			$Updates_Fields[1] = "Latitude = CASE Index ";
			$Updates_Fields[2] = "Longitude = CASE Index ";
			$Updates_Fields[3] = "Size = CASE Index ";
			$Updates_Fields[4] = "Soil = CASE Index ";
			$Updates_Fields[5] = "Tillage = CASE Index ";
			$Updates_Fields[6] = "Irrigation = CASE Index ";
			$Updates_Fields[7] = "(";
			$News_Fields = array(); //God this is easier than an update.
			
			$Updates_Plantings = array(6);
			$Updates_Plantings[0] = "Crop = CASE Id ";
			$Updates_Plantings[1] = "Variety = CASE Id ";
			$Updates_Plantings[2] = "Density = CASE Id ";
			$Updates_Plantings[3] = "Notes = CASE Id ";
			$Updates_Plantings[4] = "Date = CASE Id ";
			$Updates_Plantings[5] = "(";
			$News_Plantings = array();
			
			$Updates_Samples = array(7);
			$Updates_Samples[0] = "Latitude = CASE Id ";
			$Updates_Samples[1] = "Longitude = CASE Id ";
			$Updates_Samples[2] = "ControlCost = CASE Id ";
			$Updates_Samples[3] = "CropValue = CASE Id ";
			$Updates_Samples[4] = "Notes = CASE Id ";
			$Updates_Samples[5] = "OtherPests = CASE Id ";
			$Updates_Samples[6] = "(";
			$News_Samples = array();
			
			$Updates_Samples_Greenbug = array(4);
			$Updates_Samples_Greenbug[0] = "TreatmentRecommendation = Case Id ";
			$Updates_Samples_Greenbug[1] = "AphidCount = Case Id ";
			$Updates_Samples_Greenbug[2] = "MummyCount = Case Id ";
			$Updates_Samples_Greenbug[3] = "(";
			$News_Samples_Greenbug = array();
			
			$data = json_decode($_POST('data'));

			$rowCount;
			
			
			
			foreach($data as $field)
			{
				
				$rowCount = $db->query("select count(*) as x from Fields where Index = '" . $field['ID'] . "'");
				
				if($rowCount['x'] > 0)
				{
					$Updates_Fields[0] .= "WHEN " . $field['ID'] . " THEN " . $field['FieldName'] . " ";
					$Updates_Fields[1] .= "WHEN " . $field['ID'] . " THEN " . $field['Location']['Latitude'] . " ";
					$Updates_Fields[2] .= "WHEN " . $field['ID'] . " THEN " . $field['Location']['Longitude'] . " ";
					$Updates_Fields[3] .= "WHEN " . $field['ID'] . " THEN " . $field['Size'] . " ";
					$Updates_Fields[4] .= "WHEN " . $field['ID'] . " THEN " . $field['TypeOfSoil'] . " ";
					$Updates_Fields[5] .= "WHEN " . $field['ID'] . " THEN " . $field['TillageSystem'] . " ";
					$Updates_Fields[6] .= "WHEN " . $field['ID'] . " THEN " . $field['IrrigationSystem'] . " ";
					$Updates_Fields[7] .= $field['ID'] . ",";
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
						$Updates_Plantings[0] .= "WHEN " . $planting['ID'] . " THEN " . $planting['CropType'] . " ";
						$Updates_Plantings[1] .= "WHEN " . $planting['ID'] . " THEN " . $planting['CropVariety'] . " ";
						$Updates_Plantings[2] .= "WHEN " . $planting['ID'] . " THEN " . $planting['CropDensity'] . " ";
						$Updates_Plantings[3] .= "WHEN " . $planting['ID'] . " THEN " . $planting['Notes'] . " ";
						$Updates_Plantings[4] .= "WHEN " . $planting['ID'] . " THEN " . date('Y-m-d H:i:s', strtotime($planting['Date'])) . " ";
						$Updates_Plantings[5] .= $planting['ID'] . ",";
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
						$Updates_Samples[0] .= "WHEN " . $sample['GenericSample']['ID'] . " THEN " . $sample['GenericSample']['Location']['Latitude'] . " ";
						$Updates_Samples[1] .= "WHEN " . $sample['GenericSample']['ID'] . " THEN " . $sample['GenericSample']['Location']['Longitude'] . " ";
						$Updates_Samples[2] .= "WHEN " . $sample['GenericSample']['ID'] . " THEN " . $sample['GenericSample']['ControlCost'] . " ";
						$Updates_Samples[3] .= "WHEN " . $sample['GenericSample']['ID'] . " THEN " . $sample['GenericSample']['CropValue'] . " ";
						$Updates_Samples[4] .= "WHEN " . $sample['GenericSample']['ID'] . " THEN " . $sample['GenericSample']['Notes'] . " ";
						$Updates_Samples[5] .= "WHEN " . $sample['GenericSample']['ID'] . " THEN " . implode(", ", $sample['GenericSample']['OtherPests']) . " ";
						$Updates_Samples[6] .= $sample['GenericSample']['ID'] . ",";
					}
					else
					{
						array_push($News_Samples, "(" . $sample['GenericSample']['ID'] . ", " . $sample['GenericSample']['Location']['Latitude'] . ", " . 
														$sample['GenericSample']['Location']['Longitude'] . ", " . $field['ID'] . ", " . 
														$sample['GenericSample']['ControlCost'] . ", " . $sample['GenericSample']['CropValue'] . ", " . 
														$sample['GenericSample']['Notes'] . ", " . implode(", ", $sample['GenericSample']['OtherPests']) . ", " . 
														$field['ID'] . ")");
					}
					
					// Need an if-elseif structure for each type of sample, checking for a unique piece of data to that type.
					if(isset($sample['MummyCount']))
					{
						$rowCount = $db->query("select count(*) as x from GreenbugSamples where Id = '" . $sample['SpecificID'] . "'");
						
						if($rowCount['x'] > 0)
						{
							$Updates_Samples_Greenbug[0] .= "WHEN " . $sample['SpecificID'] . " THEN " . $sample['TreatmentRecommendation'] . " ";
							$Updates_Samples_Greenbug[1] .= "WHEN " . $sample['SpecificID'] . " THEN " . $sample['AphidCount'] . " ";
							$Updates_Samples_Greenbug[2] .= "WHEN " . $sample['SpecificID'] . " THEN " . $sample['MummyCount'] . " ";
							$Updates_Samples_Greenbug[3] .= $sample['SpecificID'] . ",";
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
			
			$Updates_Samples_Greenbug[0] .= "END, ";
			$Updates_Samples_Greenbug[1] .= "END, ";
			$Updates_Samples_Greenbug[2] .= "END ";
			$Updates_Samples_Greenbug[3] = rtrim($Updates_Samples_Greenbug[3], ",") . ")";
			
			$Updates_Samples[0] .= "END, ";
			$Updates_Samples[1] .= "END, ";
			$Updates_Samples[2] .= "END, ";
			$Updates_Samples[3] .= "END, ";
			$Updates_Samples[4] .= "END, ";
			$Updates_Samples[5] .= "END ";
			$Updates_Samples[6] = rtrim($Updates_Samples[6], ",") . ")";
			
			$Updates_Plantings[0] .= "END, ";
			$Updates_Plantings[1] .= "END, ";
			$Updates_Plantings[2] .= "END, ";
			$Updates_Plantings[3] .= "END, ";
			$Updates_Plantings[4] .= "END ";
			$Updates_Plantings[5] = rtrim($Updates_Plantings[5], ",") . ")";
			
			$Updates_Fields[0] .= "END, ";
			$Updates_Fields[1] .= "END, ";
			$Updates_Fields[2] .= "END, ";
			$Updates_Fields[3] .= "END, ";
			$Updates_Fields[4] .= "END, ";
			$Updates_Fields[5] .= "END, ";
			$Updates_Fields[6] .= "END ";
			$Updates_Fields[7] = rtrim($Updates_Fields[7], ",") . ")";
			try {
			
				$db->beginTransaction();
				
				if(strlen($Updates_Fields[0]) > 18)
				{
					$FieldsUpdate = $db->prepare("UPDATE Fields SET " . $Updates_Fields[0] . $Updates_Fields[1] . $Updates_Fields[2] . $Updates_Fields[3] . $Updates_Fields[4] . 
												$Updates_Fields[5] . $Updates_Fields[6] . " WHERE Index IN " . $Updates_Fields[7]);
					$FieldsUpdate->execute();
				}
				if(count($News_Fields) > 0)
				{
					$FieldsInsert = $db->prepare("INSERT INTO Fields (Index, Name, Latitude, Longitude, Size, Soil, Tillage, Irrigation, Owner) VALUES " . 
													implode(", ", News_Fields));
					$FieldsInsert->execute();
				}
				
				
				if(strlen($Updates_Plantings[0]) > 15)
				{
					$PlantingsUpdate = $db->query("UPDATE Plantings SET " . $Updates_Plantings[0] . $Updates_Plantings[1] . $Updates_Plantings[2] . $Updates_Plantings[3] . $Updates_Plantings[4] . 
													" WHERE Id IN " . $Updates_Plantings[5]);
					$PlantingsUpdate->execute();
				}
				if(count($News_Plantings) > 0)
				{
					$PlantingsInsert = $db->prepare("INSERT INTO Plantings (Id, Crop, Variety, Density, Notes, Date, Field) VALUES " . 
														implode(", ", News_Plantings));
					$PlantingsInsert->execute();
				}
				
				
				if(strlen($Updates_Samples[0]) > 19)
				{
					$SamplesUpdate = $db->query("UPDATE PestSamples SET " . $Updates_Samples[0] . $Updates_Samples[1] . $Updates_Samples[2] . $Updates_Samples[3] . $Updates_Samples[4] . 
												$Updates_Samples[5] . " WHERE Id IN " . $Updates_Samples[6]);
					$SamplesUpdate->execute();
				}
				if(count($News_Samples) > 0)
				{
					$SamplesInsert = $db->prepare("INSERT INTO PestSamples (Id, Latitude, Longitude, FieldID, ControlCost, CropValue, Notes, OtherPests) VALUES " . 
													implode(", ", News_Samples));
					$SamplesInsert->execute();
				}
				
				
				
				if(strlen($Updates_Samples_Greenbug[0]) > 34)
				{
					$GreenbugSamplesUpdate = $db->query("UPDATE GreenbugSamples SET " . $Updates_Samples_Greenbug[0] . $Updates_Samples_Greenbug[1] . $Updates_Samples_Greenbug[2] . 
												" WHERE Id IN " . $Updates_Samples_Greenbug[3]);
					$GreenbugSamplesUpdate->execute();
				}
				if(count($News_Samples_Greenbug) > 0)
				{
					$GreenbugSamplesInsert = $db->prepare("INSERT INTO GreenbugSamples (Id, GenericSampleID, TreatmentRecommendation, AphidCount, MummyCount) VALUES " . 
															implode(", ", News_Samples_Greenbug));
					$GreenbugSamplesInsert->execute();
				}
				
				$db->commit();
			}
			catch(PDOException $e)
			{
				$db->rollback();
				echo "Problem with POSTing data: " . $e;
				http_response_code(500);
			}
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