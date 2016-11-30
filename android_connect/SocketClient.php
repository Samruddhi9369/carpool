<?php
/*
Simple php udp socket client
*/
//Reduce errors
error_reporting(~E_WARNING);
sleep(1);

$server = '192.168.74.101';
$port = 5050;
if(!($sock = socket_create(AF_INET, SOCK_DGRAM, 0)))
{
$errorcode = socket_last_error();
$errormsg = socket_strerror($errorcode);
die("Couldn't create socket: [$errorcode] $errormsg \n");
}
echo "Socket created \n";
//Communication loop
//while(1)
{
//Take some input to send
echo 'Enter a message to send : ';
$input ="hello";
//Send the message to the server
if( ! socket_sendto($sock, $input , strlen($input) , 0 , $server , $port))
{
$errorcode = socket_last_error();
$errormsg = socket_strerror($errorcode);
die("Could not send data: [$errorcode] $errormsg \n");
}


//echo "Reply : $reply";
}
?>

