<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// check for post data
if (isset($_GET["email"])) 
{
    $pid = $_GET['pid'];

    // get a request from offer table
    $result = mysql_query("SELECT *FROM request WHERE pid like '$pid' ");

    if (!empty($result)) 
	{
        // check for empty result
        if (mysql_num_rows($result) > 0) {

            $result = mysql_fetch_array($result);

            $product = array();
            $product["pid"] = $result["pid"];
            $product["source"] = $result["source"];
            $product["dest"] = $result["dest"];
            $product["date"] = $result["date"];
	      $product["email"] = $result["email"];
            $product["time"] = $result["time"];
            $product["seats"] = $result["seats"];
	      $product["hr"] = $result["hr"];
            $product["min"] = $result["min"];


            $product["created_at"] = $result["created_at"];
            $product["updated_at"] = $result["updated_at"];
            
            // success
            $response["success"] = 1;

            // user node
            $response["product"] = array();

            array_push($response["product"], $product);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no request found
            $response["success"] = 0;
            $response["message"] = "No requests found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no request found
        $response["success"] = 0;
        $response["message"] = "No requests found";

        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";

    // echoing JSON response
    echo json_encode($response);
}


?>
