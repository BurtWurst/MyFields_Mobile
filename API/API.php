<?php
	
	include_once "Field.php";
	include_once "PestSample.php";
	include_once "GPSLocation.php";
	include_once "Planting.php";
	
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
		$uid = 1;
		$count = 0;
		
		$statement = $db->query("select * from Fields where Owner = '" . $uid . "'");
		
		foreach($statement as $row)
		{
			$field = new Field($row["Index"], $row["Name"], $row["Latitude"], $row["Longitude"],
								$row["Size"], $row["Soil"], $row["Tillage"], $row["Irrigation"]);
			
			// Add each planting
			foreach($db->query("select * from Plantings where Field = '" . $field->getID() . "'") as $planting)
			{
				$field->addPlanting(new Planting($planting["Id"], $planting["Crop"], $planting["Variety"],
												$planting["Density"], $planting["Notes"]));
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
				
			echo $field->JSONize("");
			
			if($count < ($statement->rowCount() - 1))
			{
				echo ",\n";
			}
			else
			{
				echo "\n";
			}
			
			$count++;
		}
	}
	catch(PDOException $problem)
	{
		echo 'Problem connecting to database: ' . $problem;
	}
?>