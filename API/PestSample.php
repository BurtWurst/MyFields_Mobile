<?php

include_once "GPSLocation.php";

class PestSample 
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
	
	public function JSONize()
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
	
	public function JSONize($Tabs)
	{
		echo $Tabs . "{\n";
		$TabsPlusOne = $Tabs . "\t";
		echo $TabsPlusOne . "\"ID\" : " . $this->Id . ",\n";
		echo $TabsPlusOne . "\"Location\" : \n";
		echo $this->Location->JSONize($TabsPlusOne);
		echo ",\n";
		echo $TabsPlusOne . "\"Field\" : " . $this->Field . ",\n";
		echo $TabsPlusOne . "\"ControlCost\" : " . $this->ControlCost . ",\n";
		echo $TabsPlusOne . "\"CropValue\" : " . $this->CropValue . ",\n";
		$TreatmentRecommendationString = $this->TreatmentRecommendation ? "True" : "False";
		echo $TabsPlusOne . "\"TreatmentRecommendation\" : " . $TreatmentRecommendationString . ",\n";
		echo $TabsPlusOne . "\"AphidCount\" : " . $this->AphidCount . ",\n";
		echo $TabsPlusOne . "\"MummyCount\" : " . $this->MummyCount . ",\n";
		echo $TabsPlusOne . "\"Notes\" : \"" . $this->Notes . "\",\n";
		echo $TabsPlusOne . "\"OtherPests\" : [\n";
		for($i = 0; $i < count($this->OtherPests); $i++)
		{
			echo $TabsPlusOne . "\t\"" . $this->OtherPests[$i] . "\"";
			
			if($i < count($this->OtherPests) - 1)
			{
				echo ",\n";
			}
			else
			{
				echo "\n";
			}
		}
		
		echo $TabsPlusOne . "]\n";
		echo $Tabs . "}";
	}
}

?>