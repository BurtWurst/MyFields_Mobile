<?php

class Planting implements JsonSerializable
{
	private $ID;
	private $CropType;
	private $CropVariety;
	private $CropDensity;
	private $Notes;
	private $Date;
	
	public function __construct($PlantID, $Crop, $Variety, $Density, $NoteSection, $DateOfPlant)
	{
		$this->ID = $PlantID;
		$this->CropType = $Crop;
		$this->CropVariety = $Variety;
		$this->CropDensity = $Density;
		$this->Notes = $NoteSection;
		$this->Date = $DateOfPlant;
	}
	
	public function jsonSerialize()
	{
		
		return [
					'ID' 	   	  => $this->ID,
					'CropType' 	  => $this->CropType,
					'CropVariety' => $this->CropVariety,
					'CropDensity' => $this->CropDensity,
					'Notes' 	  => $this->Notes,
					'Date' 		  => $this->Date
				];
	}
}

?>