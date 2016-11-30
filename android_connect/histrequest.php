<?php


/*
 * Following code will list all the offers
 */

// array for JSON response
$response = array();
$response1 = array();

// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET['email'])) 
{
    //$email = $_GET['email'];
    //	$email='a@b.com';

	// get all request from products table
	$sql = mysql_query("SELECT * FROM request where email like 'a@b.com' order BY pid DESC");

	if (mysql_num_rows($sql) > 0) 
	{
    // looping through all results
    // request node
    $response["request"] = array();
	
 
    while ($row = mysql_fetch_array($sql)) 
    {
        // temp user array
        $product = array();
        $product["pid"] = $row["pid"];
        $product["source"] = $row["source"];
        $product["dest"] = $row["dest"];
        $product["date"] = $row["date"];
	  $product["email"] = $row["email"];
      
        $product["time"] = $row["time"];
        $product["seats"] = $row["seats"];

	  $product["hr"] = $row["hr"];
        $product["min"] = $row["min"];

        $product["created_at"] = $row["created_at"];
        $product["updated_at"] = $row["updated_at"];


        // push single product into final response array
        array_push($response["request"], $product);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
	} 
	else 
	{
    	// no products found
    	$response["success"] = 0;
    	$response["message"] = "No Requests found";

    	// echo no users JSON
    	echo json_encode($response);
	}	
	
}
else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}


?>

