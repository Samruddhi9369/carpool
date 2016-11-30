<?php

/*
 * Following code will create a new request row
 * All request details are read from HTTP Post Request
 */

// array for JSON response
$response = array();

// check for required fields
if (isset($_POST['source']) && isset($_POST['dest']) && isset($_POST['date'])&& isset($_POST['email'])  && isset($_POST['time'])&& isset($_POST['seats'])) {
    
    $source = $_POST['source'];
    $dest = $_POST['dest'];
    $date = $_POST['date'];
    $email = $_POST['email'];
    $time = $_POST['time'];
    $seats = $_POST['seats'];
	 $parts = explode(':',$time);
	$str = (int)$parts[0];
	$str1=(int)$parts[1];


	//$yyyy_mm_dd =strtotime($yyyy_mm_dd );


    // include db connect class
    require_once __DIR__ . '/db_connect.php';

    // connecting to db
    $db = new DB_CONNECT();

    // mysql inserting a new row
    $result = mysql_query("INSERT INTO request(source, dest, date,email,time,seats,hr,min) VALUES('$source', '$dest', '$date', '$email', '$time', '$seats', '$str', '$str1')");

    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Request successfully created.";

        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        
        // echoing JSON response
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
