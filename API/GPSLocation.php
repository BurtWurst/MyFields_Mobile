<?php

class GPSLocation
{
	private $Latitude;
	private $Longitude;
	public function __construct($Lat, $Lon)
	{
		$this->Latitude =  $Lat;
		$this->Longitude =  $Lon;
	}
	
	public function JSONize($Tabs)
	{
		echo $Tabs . "{\n";
		$TabsPlusOne = $Tabs . "\t";
		echo $TabsPlusOne . "\"Latitude\" : " . $this->Latitude . ",\n";
		echo $TabsPlusOne . "\"Longitude\" : " . $this->Longitude . "\n";
		echo $Tabs . "}";
	}
}

?>