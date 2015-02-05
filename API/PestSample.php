<?php

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
		$Id = $Identifier;
		$Location = new GPSLocation($Latitude, $Longitude);
		$Field = $Site;
		$ControlCost = $TreatmentCost;
		$CropValue = $CropCost;
		
		$Notes = $NoteSection;
		$OtherPests = $Pests;
		
	}
	
	public function JSONize()
	{
		throw new Exception("Not Implemented!");
	}
}

class GreenbugSample extends PestSample
{
	protected $AphidCount;
	protected $MummyCount;
	
	public function __construct($Identifier, $Latitude, $Longitude, $Site, $TreatmentCost, $CropCost, $NoteSection, $Pests, $Aphids, $Mummys)
	{
		parent::__construct($Identifier, $Latitude, $Longitude, $Site, $TreatmentCost, $CropCost, $NoteSection, $Pests);
		
		$AphidCount = $Aphids;
		$MummyCount = $Mummys;
	}
	
	public function JSONize()
	{
		echo '\t{';
		echo '\t\t"ID" : ' . $ID . ',\n';
		echo '\t\t"Location" : \n' . $Location->JSONize() . ',\n';
		echo '\t\t"Field" : ' . $Field->ID . ',\n';
		echo '\t\t"ControlCost" : ' . $ControlCost . ',\n';
		echo '\t\t"CropValue" : ' . $CropValue . ',\n';
		echo '\t\t"AphidCount" : ' . $AphidCount . ',\n';
		echo '\t\t"MummyCount" : ' . $MummyCount . ',\n';
		echo '\t\t"Notes" : "' . $Notes . '",\n';
		echo '\t\t"OtherPests" : [\n';
		for($i = 0; $i < count($OtherPests); $i++)
		{
			echo '\t\t\t"' . $OtherPests[i] . '"';
			
			if($i < count($OtherPests) - 1)
			{
				echo ',\n';
			}
			else
			{
				echo '\n';
			}
		}
		
		echo '\t\t]\n';
		echo '\t}';
	}
}

?>