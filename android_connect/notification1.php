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

// get all request from products table

$result = mysql_query("SELECT * FROM request ORDER BY pid DESC LIMIT 1") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) 
{
    // looping through all results
    // offer node
    $response["request"] = array();
    
    while ($row = mysql_fetch_array($result)) 
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
    $response["message"] = "No requests found";

    // echo no users JSON
    echo json_encode($response);
}
?>

