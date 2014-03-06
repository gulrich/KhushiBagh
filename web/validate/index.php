<?php

mysql_connect("localhost","user","****");
mysql_select_db("database");

$elements = explode(',',addslashes($_GET['elements']));

foreach($elements as $eid) {
	$result = mysql_query("select name,cid from current where id=$eid") or die(mysql_error());
	if($data = mysql_fetch_array($result)) {
		$name = $data['name'];
		$cid = $data['cid'];

		$result = mysql_query("select name from archives where name='$name'") or die(mysql_error());
		if(!($data = mysql_fetch_array($result))) {
			mysql_query("insert into archives values('$name','$cid')") or die(mysql_error());
		}
	}
	mysql_query("delete from current where id=".$eid) or die(mysql_error());
}

echo "Validation successfull!";

mysql_close();


?>
