<?php

mysql_connect("localhost","user","****");
mysql_select_db("database");

$json = '{"list": [';

$result = mysql_query("select * from category order by id") or die(mysql_error());
while($data = mysql_fetch_array($result)) {
	
	$json .= '{"id": "'.$data['id'].'", "category": "'.stripslashes($data['category']).'"},';

}

$json = rtrim($json, ",")."]}";

echo $json;

mysql_close();


?>
