<?php

class PestSample 
{
	
	private static $SampleNumber = 1;			
	
	private $Location;
	private $Site;
	private $ControlCost;
	private $CropValue;	
	
	private $Notes;
	private $OtherPests;
	
	
	public function __construct()
	{
		
		
	}
	
	public function JSONize()
	{
		return json_encode(get_object_vars($this));
	}
}


?>