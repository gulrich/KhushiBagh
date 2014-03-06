<?php

mysql_connect("localhost","user","****");
mysql_select_db("database");

$element = addslashes(ucfirst(utf8_decode($_GET['element'])));
$qtty = addslashes($_GET['qtty']);

if(isset($_GET['cid'])) {
	$cid = addslashes($_GET['cid']);
	
} else if(isset($_GET['cname'])) {
	$cname = addslashes(ucfirst(utf8_decode($_GET['cname'])));
	$result = mysql_query("select id from category where category = '$cname'") or die(mysql_error());
	if($data = mysql_fetch_array($result)) {
		$cid = $data['id'];
	} else {
		mysql_query("insert into category values('','$cname')") or die(mysql_error());
		$cid = mysql_insert_id();
	}
}

if(isset($cid)) {
	mysql_query("insert into current values('','$element','$cid','$qtty')") or die(mysql_error());
}

echo "Added successfully!";
mysql_close();


?>
