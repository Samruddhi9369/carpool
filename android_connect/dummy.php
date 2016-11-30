<?php
    if(isset($GLOBALS['HTTP_RAW_POST_DATA']) == TRUE){
        echo $GLOBALS['HTTP_RAW_POST_DATA'];
    }else{
        echo $HTTP_RAW_POST_DATA;
    }
?>