<?php

class Field 
{
	
	private static $FieldNameNumber = 1;
	
	$ValidCropTypes = { "Wheat", "Corn", "Soybean" };
	$ValidSoilTypes = { "Clay", "Clay Loam", "Loam", "Silty Clay", "Silty Clay Loam", "Silt Loam",
						"Silt", "Sandy Clay", "Sandy Clay Loam", "Sandy Loam", "Loamy Sand", "Sand"};
	$ValidTillageTypes = { "Conventional Tillage", "Reduced Tillage", "No-till", "Strip-till", "Mulch-Till", 
						"Rotational Tillage", "Ridge-Till"};
						
	$ValidIrrigationTypes = { "Dry Land (no irrigation)", "Surface", "Localized", "Drip", "Sprinkler",
								"Center Pivot", "Lateral Move", "Sub-Irrigation"};					
	
	private $FieldName;
	private $TypeOfCrop;
	private $Location;
	private $Size;
	private $TypeOfSoil;
	private $TillageSystem;
	private $IrrigationSystem;
	
	public function __construct()
	{
		$FieldName = "Field" . $FieldNameNumber;
		$FieldNameNumber = $FieldNameNumber + 1;
		$TypeOfCrop = $ValidCropTypes[rand(0, sizeof($ValidCropTypes))];
		$Location =  = new GPSLocation();
		$Size = rand(1, 20);
		$TypeOfSoil = $ValidSoilTypes[rand(0, sizeof($ValidSoilTypes))];
		$TillageSystem = $ValidTillageTypes[rand(0, sizeof($ValidTillageTypes))];
		$IrrigationSystem = $ValidIrrigationTypes[rand(0, sizeof($ValidIrrigationTypes))];
		
	}
	
	public function JSONize()
	{
		return json_encode(get_object_vars($this));
	}
}

class GPSLocation
{
	private $Latitude;
	private $Longitude;
	
	
	
	public function __construct()
	{
		$Latitude = 39.240867 + GPSLocation::randomFloat();
		$Longitude = -96.536004 + GPSLocation::randomFloat();
	}
	
	public static function randomFloat($min = -1, $max = 1) 
	{
		return $min + mt_rand() / mt_getrandmax() * ($max - $min);
	}
}

?>


