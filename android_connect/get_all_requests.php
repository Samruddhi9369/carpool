<?php

/*
 * Following code will list all the requests
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all offers from offer table
$sql = mysql_query("SELECT * FROM offer ORDER BY pid DESC LIMIT 1");
if (mysql_num_rows($sql) > 0) 
{
    // looping through all results
    // offer node
    $response1["offer"] = array();
    
    while ($row = mysql_fetch_array($sql)) 
    {
        extract($row); 
        $str=$dest;

    }
}
$result = mysql_query("SELECT *FROM request where dest like '$str'") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) 
{
    // looping through all results
    // request node
    $response["request"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $product = array();
        $product["pid"] = $row["pid"];
        $product["source"] = $row["source"];
        $product["dest"] = $row["dest"];
        $product["date"] = $row["date"];
	$product["email"] = $row["email"];
        $product["created_at"] = $row["created_at"];
        $product["updated_at"] = $row["updated_at"];
        $product["gid"] = $row["gid"];
        $product["time"] = $row["time"];
        $product["seats"] = $row["seats"];



        // push single request into final response array
        array_push($response["request"], $product);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No requests found";

    // echo no users JSON
    echo json_encode($response);
}
?>

