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
 $user = mysql_fetch_assoc(mysql_query("select * from `ly_users` where `uid`='$uid'"));

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


 $name = json_encode($data->name);
 $email = json_encode($data->email);
 $password = json_encode($data->password);
 $profile = json_encode($data->profile);
 $login_token = "jhjsdvjd";
 $user = get_email($email);

 if($user)
 {
   return array("err-code"=>300,"message"=>"This email is already exist. Please try with another email.");
 }
 else
 {
   $query = "insert  into `blog_users` (`name`,`email`,`password`,`device_token`,`profile`,`date_of_creation`) values('$name','$email',md5('$password'),'$login_token','$profile',NOW())";
   if(mysql_query($query))
   {
     $u_id = mysql_insert_id();
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

 $email = mysql_real_escape_string($data->email);


 $password = md5(mysql_real_escape_string($data->password));

 $query = "select lu.*, lup.city, lup.phone_no,lup.name,lup.gender,lup.image,lup.thumbnail    from ly_users as lu join ly_user_profile as lup on lup.uid=lu.uid where lu.username='$email' and password='$password' ";


 if($user = mysql_fetch_assoc(mysql_query($query)))
 {
   return array("err-code"=>300,"message"=>"This account is banned by the Lyker admin.");
 }

 else
 {
   return array("message" => "Login failed. Please check email or password." , "err-code" => "300");
 }
}
$req = @file_get_contents('php://input');

$name = $_POST['name'];
$email = $_POST['email'];
$password = $_POST['password'];
$profile = $_POST['profile'];
$method = $_POST['method'];

$input = array($name, $email, $password, $profile);
$result = json_encode($input);

if(isset($method))
{
 if (function_exists($method))
 {
   echo json_encode($method($result));

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
?>
