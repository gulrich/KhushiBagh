<?php

mysql_connect("localhost","user","****");
mysql_select_db("database");

$json = '{"list": [';

$result = mysql_query("select current.id, current.name, category.category, current.quantity from current, category where category.id=current.cid  order by current.cid") or die(mysql_error());
while($data = mysql_fetch_array($result)) {
	
	$json .= '{"id": "'.$data['id'].'", "name": "'.stripslashes($data['name']).'", "category": "'.stripslashes($data['category']).'", "quantity": "'.stripslashes($data['quantity']).'"},';

}

$json = rtrim($json, ",")."]}";

echo $json;

mysql_close();


?>
