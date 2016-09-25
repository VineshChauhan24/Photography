package com.infiniteloops.photographer;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by asna on 8/12/16.
 */
public class ReUseablity {

    public static String REGISTER_USER ="http://infiniteloops.info/blog/volley/index.php";

    public static String USER_NAME = "USER_NAME";
    public static String USER_EMAIL = "USER_EMAIL";
    public static String USER_PROFILE_PIC = "USER_PROFILE_PIC";
    public static String USER_GENDER = "USER_GENDER";
    public static String USER_DOB = "USER_DOB";
    public static String USER_ID = "USER_ID";
    public static String LOGIN_TOKEN = "LOGIN_TOKEN";


    public static SharedPreferences sharedpreferences;
    public static SharedPreferences.Editor editor;

    public static void createSharedPreference(Context cxt,String userName,String userEmail,String userProfilePic,String userGender,String userNameDob){
        try {
            sharedpreferences = cxt.getSharedPreferences("MyPreferences", cxt.MODE_PRIVATE);
            editor = sharedpreferences.edit();
            editor.putString(USER_NAME, userName);
            editor.putString(USER_EMAIL, userEmail);
            editor.putString(USER_PROFILE_PIC, userProfilePic);
            editor.putString(USER_GENDER, userGender);
            editor.putString(USER_DOB, userNameDob);
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static SharedPreferences getSharedPrefValue(Context cxt){
        sharedpreferences =  cxt.getSharedPreferences("MyPreferences", cxt.MODE_PRIVATE);
        return sharedpreferences;
    }
    public static SharedPreferences.Editor editSharedPrefValue(Context cxt){
        sharedpreferences =  cxt.getSharedPreferences("MyPreferences", cxt.MODE_PRIVATE);
        return sharedpreferences.edit();
    }

    public static void showToast(Context mContext, String str){
        Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();
    }
    public static String getYesterdayDateString() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        c.add(Calendar.DATE, -1);

        String formattedDate = df.format(c.getTime());
        System.out.print("y date"+formattedDate);

        return  formattedDate;

    }

    public static String getTodaysDateString() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        System.out.print("date"+formattedDate);
        return  formattedDate;

    }

}
