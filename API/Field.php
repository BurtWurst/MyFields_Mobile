<?php

include_once "PestSample.php";
include_once "GPSLocation.php";
include_once "Planting.php";

class Field 
{	
	protected $ID;
	protected $FieldName;
	protected $Location;
	protected $Size;
	protected $TypeOfSoil;
	protected $TillageSystem;
	protected $IrrigationSystem;
	
	protected $PlantingList;
	protected $PestSamples;
	
	public function __construct($FieldID, $Name, $Latitude, $Longitude, $Acres, 
								$SoilType, $MethodOfTill, $Irrigation, $Plantings,
								$PestReports)
	{
		$this->ID = $FieldID;
		$this->FieldName = $Name;
		$this->Location = new GPSLocation($Latitude, $Longitude);
		$this->Size = $Acres;
		$this->TypeOfSoil = $SoilType;
		$this->TillageSystem = $MethodOfTill;
		$this->IrrigationSystem = $Irrigation;
		
		$this->PlantingList = $Plantings;
		$this->PestSamples = $PestReports;
		
	}
	
	public function JSONize($Tabs)
	{
		echo $Tabs . "{\n";
		$TabsPlusOne = $Tabs . "\t";
		echo $TabsPlusOne . "\"ID\" : " . $this->ID . ",\n";
		echo $TabsPlusOne . "\"FieldName\" : \"" . $this->FieldName . "\",\n";
		echo $TabsPlusOne . "\"Location\" : \n";
		echo $this->Location->JSONize($TabsPlusOne);
		echo ",\n";
		echo $TabsPlusOne . "\"Size\" : " . $this->Size . ",\n";
		echo $TabsPlusOne . "\"TypeOfSoil\" : \"" . $this->TypeOfSoil . "\",\n";
		echo $TabsPlusOne . "\"TillageSystem\" : \"" . $this->TillageSystem . "\",\n";
		echo $TabsPlusOne . "\"IrrigationSystem\" : \"" . $this->IrrigationSystem . "\",\n";
		
		echo $TabsPlusOne . "\"PlantingList\" : [\n";
		
		for($i = 0; $i < count($this->PlantingList); $i++)
		{
			echo $this->PlantingList[$i]->JSONize($TabsPlusOne);
			
			if($i < count($this->PlantingList) - 1)
			{
				echo ",\n";
			}
			else
			{
				echo "\n";
			}
		}
		
		echo $TabsPlusOne . "],\n";
		
		echo $TabsPlusOne . "\"PestSamples\" : [\n";
		
		for($i = 0; $i < count($this->PestSamples); $i++)
		{
			echo $this->PestSamples[$i]->JSONize($TabsPlusOne);
			
			if($i < count($this->PestSamples) - 1)
			{
				echo ",\n";
			}
			else
			{
				echo "\n";
			}
		}
		
		echo $TabsPlusOne . "],\n";
		
		echo "}";
	}
}

?>