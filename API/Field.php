<?php

include_once "PestSample.php";
include_once "GPSLocation.php";
include_once "Planting.php";

class Field implements JsonSerializable
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
								$SoilType, $MethodOfTill, $Irrigation)
								//$Plantings, $PestReports)
	{
		$this->ID = $FieldID;
		$this->FieldName = $Name;
		$this->Location = new GPSLocation($Latitude, $Longitude);
		$this->Size = $Acres;
		$this->TypeOfSoil = $SoilType;
		$this->TillageSystem = $MethodOfTill;
		$this->IrrigationSystem = $Irrigation;
		
		$this->PlantingList = array();
		$this->PestSamples = array();
		
	}
	
	public function addPestSample($Sample)
	{
		array_push($this->PestSamples, $Sample);
		//$this->PestSamples[$Sample->Id => $Sample];
	}
	
	public function addPlanting($Planting)
	{
		array_push($this->PlantingList, $Planting);
		//$this->PestSamples[$Planting->Id => $Planting];
	}
	
	public function getId()
	{
		return $this->ID;
	}
	
	public function jsonSerialize()
	{
	
		$PLList = array();
		$PSList = array();
		
		foreach($this->PlantingList as $Planting)
		{
			array_push($PLList, $Planting->jsonSerialize());
		}
		
		foreach($this->PestSamples as $Sample)
		{
			array_push($PSList, $Sample->jsonSerialize());
		}
		
		return [
					'ID' => $this->ID,
					'FieldName' => $this->FieldName,
					'Location' => $this->Location->jsonSerialize(),
					'Size' => $this->Size,
					'TypeOfSoil' => $this->TypeOfSoil,
					'TillageSystem' => $this->TillageSystem,
					'IrrigationSystem' => $this->IrrigationSystem,
					'PlantingList' => $PLList,
					'PestSamples' => $PSList
				];
	}
}

?>