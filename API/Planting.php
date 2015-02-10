<?php

class Planting
{
	private $ID;
	private $CropType;
	private $CropVariety;
	private $CropDensity;
	private $Notes;
	
	public function __construct($PlantID, $Crop, $Variety, $Density, $NoteSection)
	{
		$this->ID = $PlantID;
		$this->CropType = $Crop;
		$this->CropVariety = $Variety;
		$this->CropDensity = $Density;
		$this->Notes = $NoteSection;
	}
	
	public function JSONize($Tabs)
	{
		echo $Tabs . "{\n";
		$TabsPlusOne = $Tabs . "\t";
		echo $TabsPlusOne . "\"ID\" : " . $this->ID . ",\n";
		echo $TabsPlusOne . "\"CropType\" : \"" . $this->CropType . "\",\n";
		echo $TabsPlusOne . "\"CropVariety\" : \"" . $this->CropVariety . "\",\n";
		echo $TabsPlusOne . "\"CropDensity\" : \"" . $this->CropDensity . "\",\n";
		echo $TabsPlusOne . "\"Notes\" : \"" . $this->Notes . "\"\n";
		echo $Tabs . "}";
	}
}

?>