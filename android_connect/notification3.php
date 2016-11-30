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


$result = mysql_query("select * from users where email like( select email from request order by pid desc limit 1)") or die(mysql_error());;
// check for empty result
if (mysql_num_rows($result) > 0) 
{
    // looping through all results
    // offer node
    $response["users"] = array();
    
    while ($row = mysql_fetch_array($result)) 
    {
        
// temp user array
        	$product = array();
	$product["uid"] = $row["uid"];
	  $product["unique_id"] = $row["unique_id"];
	  $product["name"] = $row["name"];
	  $product["email"] = $row["email"];
	  $product["phno"] = $row["phno"];

	$product["address"] = $row["address"];
	  $product["ipString"] = $row["ipString"];

	$product["encrypted_password"] = $row["encrypted_password"];
	  $product["salt"] = $row["salt"];
	$product["created_at"] = $row["created_at"];
	  $product["updated_at"] = $row["updated_at"];


        // push single product into final response array
        array_push($response["users"], $product);
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
    $response["message"] = "No users found";

    // echo no users JSON
    echo json_encode($response);
}
?>










