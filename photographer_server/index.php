<?php
error_reporting(E_ALL ^ E_DEPRECATED);

include("config.php");
function generateRandomString($length = 10)
{
 $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';

 $randomString = '';

 for ($i = 0; $i < $length; $i++)
 {

   $randomString .= $characters[rand(0, strlen($characters) - 1)];
 }

 return $randomString;
}
function get_user_data($uid) {
 $uid = mysql_real_escape_string($uid);
 if (empty($uid))

   return NULL;
 $user = mysql_fetch_assoc(mysql_query("select * from `blog_users` where `id`='$uid'"));

 return $user;
 //return  array("message" => $user,"login_token" => $login_token, "err-code" => "0");

}

function get_email($email) {
 $email = mysql_real_escape_string($email);
 if (empty($email))

   return NULL;
 $user = mysql_fetch_assoc(mysql_query("select * from `blog_users` where `email`='$email'"));

 return $user;
 //return  array("message" => $user,"login_token" => $login_token, "err-code" => "0");

}

function register_app_user($data)
{

 //$data = json_decode($data);

 $name = $data['name'];
 $email = $data['email'];
 $password = $data['password'];
 $profile = $data['profile'];
 $login_token = generateRandomString(10);
 $user = get_email($email);
 $id = getFileName();
 $path = "uploads/$id.png";
 $actualpath = "http://infiniteloops.info/blog/volley/$path";

 if($user)
 {
   return array("err-code"=>300,"message"=>"This email is already exist. Please try with another email.");
 }
 else
 {
   $query = "insert  into `blog_users` (`name`,`email`,`password`,`device_token`,`profile`,`date_of_creation`) values('$name','$email',md5('$password'),'$login_token','$actualpath',NOW())";
   if(mysql_query($query))
   {
     $u_id = mysql_insert_id();
     file_put_contents($path,base64_decode($profile));
     $result = mysql_fetch_assoc(mysql_query("select * from blog_users where id='$u_id'"));
     return array("err-code"=>0,"message"=>"Thank You! You are successfully signed-up.","user"=>$result);
   }
   else
   {
     return array("err-code"=>300,"message"=>"Please try again.");
   }
 }


}

function user_login($data)
{

 $email = $data['email'];
 $password = md5($data['password']);

 $query = "select * FROM blog_users where email='$email' and password='$password'";


 if($user = mysql_fetch_assoc(mysql_query($query)))
 {
   return array("err-code"=>0,"message"=>"Successfully login","user"=>$user);
 }

 else
 {
   return array("message" => "Login failed. Please check email or password." , "err-code" => "300");
 }
}
$req = @file_get_contents('php://input');
$method = $_POST['method'];
if(isset($method))
{
 if (function_exists($method))
 {
   echo json_encode($method($_POST));

 }

 else
 {
   echo "method not exist";
 }
}
else
{
 echo "method not set ".$method;
}
//ImagickPixel
/*
		We are generating the file name
		so this method will return a file name for the image to be upload
	*/
	function getFileName(){
		$sql = "SELECT max(id) as id FROM blog_users";
		$result = mysql_fetch_array(mysql_query($sql));

		mysql_close($con);
		if($result['id']==null)
			return 1;
		else
			return ++$result['id'];
	}
?>
