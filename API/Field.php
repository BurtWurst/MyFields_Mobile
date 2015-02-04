<?php

class Field 
{	
	private $ID;
	private $FieldName;
	private $Location;
	private $Size;
	private $TypeOfSoil;
	private $TillageSystem;
	private $IrrigationSystem;
	
	private $PlantingList;
	private $PestSamples;
	
	public function __construct($FieldID, $Name, $Latitude, $Longitude, $Acres, 
								$SoilType, $MethodOfTill, $Irrigation, $Plantings,
								$PestReports)
	{
		$ID = $FieldID;
		$FieldName = $Name;
		$Location =  = new GPSLocation($Latitude, $Longitude);
		$Size = $Acres;
		$TypeOfSoil = $SoilType;
		$TillageSystem = $MethodOfTill;
		$IrrigationSystem = $Irrigation;
		
		$PlantingList = $Plantings;
		$PestSamples = $PestReports;
		
	}
	
	public function JSONize()
	{
		echo '{';
		echo '\t"ID" : ' . $ID . ',\n';
		echo '\t"FieldName" : "' . $FieldName . '",\n';
		echo '\t"Location" : \n' . $Location->JSONize() . ',\n';
		echo '\t"Size" : ' . $Size . ',\n';
		echo '\t"TypeOfSoil" : "' . $TypeOfSoil . '",\n';
		echo '\t"TillageSystem" : "' . $TillageSystem . '",\n';
		echo '\t"IrrigationSystem" : "' . $IrrigationSystem . '",\n';
		
		echo '\t"PlantingList" : [\n';
		
		for($i = 0; $i < count($PlantingList); $i++)
		{
			echo $PlantingList[i]->JSONize();
			
			if($i < count($PlantingList) - 1)
			{
				echo ',\n';
			}
			else
			{
				echo '\n';
			}
		}
		
		echo '\t],\n';
		
		echo '\t"PestSamples" : [\n';
		
		for($i = 0; $i < count($PestSamples); $i++)
		{
			echo $PestSamples[i]->JSONize();
			
			if($i < count($PestSamples) - 1)
			{
				echo ',\n';
			}
			else
			{
				echo '\n';
			}
		}
		
		echo '\t],\n';
		
		echo '}';
	}
}

class GPSLocation
{
	private $Latitude;
	private $Longitude;

	public function __construct($Lat = 39.240867, $Lon = -96.536004)
	{
		$Latitude =  $Lat;
		$Longitude =  $Lon;
	}
	
	public function JSONize()
	{
		echo '\t{';
		echo '\t\t"Latitude" : ' . $Latitude . ',\n';
		echo '\t\t"Longitude" : ' . $Longitude . '\n';
		echo '\t}';
	}
}

class Planting
{
	private $ID;
	private $CropType;
	private $CropVariety;
	private $CropDensity;
	private $Notes;
	
	public function __construct($PlantID, $Crop, $Variety, $Density, $NoteSection)
	{
		$ID = $PlantID;
		$CropType = $Crop;
		$CropVariety = $Variety;
		$CropDensity = $Density;
		$Notes = $NoteSection;
	}
	
	public function JSONize()
	{
		echo '\t{';
		echo '\t\t"ID" : ' . $ID . ',\n';
		echo '\t\t"CropType" : "' . $CropType . '",\n';
		echo '\t\t"CropVariety" : "' . $CropVariety . '",\n';
		echo '\t\t"CropDensity" : "' . $CropDensity . '",\n';
		echo '\t\t"Notes" : "' . $Notes . '"\n';
		echo '\t}';
	}
}

?>


