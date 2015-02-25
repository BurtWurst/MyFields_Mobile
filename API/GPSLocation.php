<?php

class GPSLocation implements JsonSerializable
{
	private $Latitude;
	private $Longitude;
	public function __construct($Lat, $Lon)
	{
		$this->Latitude =  $Lat;
		$this->Longitude =  $Lon;
	}
	
	public function jsonSerialize()
	{
	
		return [
				'Latitude'  => $this->Latitude,
				'Longitude' => $this->Longitude
				];
	}
}

?>