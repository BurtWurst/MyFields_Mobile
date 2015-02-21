<?php

include_once "GPSLocation.php";

class PestSample implements JsonSerializable
{
	
	protected $Id;			
	
	protected $Location;
	protected $Field;
	protected $ControlCost;
	protected $CropValue;	
	
	protected $Notes;
	protected $OtherPests;
	
	
	public function __construct($Identifier, $Latitude, $Longitude, $Site, $TreatmentCost, $CropCost, $NoteSection, $Pests)
	{
		$this->Id = $Identifier;
		$this->Location = new GPSLocation($Latitude, $Longitude);
		$this->Field = $Site;
		$this->ControlCost = $TreatmentCost;
		$this->CropValue = $CropCost;
		
		$this->Notes = $NoteSection;
		$this->OtherPests = $Pests;
		
	}
	
	public function jsonSerialize()
	{
		throw new Exception("Not Implemented!");
	}
}

class GreenbugSample extends PestSample
{
	protected $TreatmentRecommendation;
	protected $AphidCount;
	protected $MummyCount;
	
	public function __construct($Identifier, $Latitude, $Longitude, $Site, $TreatmentCost, $CropCost, $NoteSection, $Pests, $Aphids, $Mummys, $Treatment)
	{
		parent::__construct($Identifier, $Latitude, $Longitude, $Site, $TreatmentCost, $CropCost, $NoteSection, $Pests);
		
		$this->TreatmentRecommendation = $Treatment;
		$this->AphidCount = $Aphids;
		$this->MummyCount = $Mummys;
	}
	
	public function jsonSerialize()
	{
		
		return [
					'ID' 						=> $this->Id,
					'Location' 					=> $this->Location->jsonSerialize(),
					'Field' 					=> $this->Field,
					'ControlCost' 				=> $this->ControlCost,
					'CropValue' 				=> $this->CropValue,
					'TreatmentRecommendation' 	=> $this->TreatmentRecommendation,
					'AphidCount' 				=> $this->AphidCount,
					'MummyCount' 				=> $this->MummyCount,
					'Notes' 					=> $this->Notes,
					'OtherPests' 				=> $this->OtherPests
				];
	}
}

?>