package com.udelvr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings;

public class AuthStore
{
    // The SharedPreferences object in which auth are stored
    private final SharedPreferences mPrefs;

    // The name of the resulting SharedPreferences
    private static final String SHARED_PREFERENCE_NAME =
            AuthStore.class.getSimpleName();

    // Create the SharedPreferences storage with private access only
    public AuthStore(Context context)
    {
        mPrefs =
                context.getSharedPreferences(
                        SHARED_PREFERENCE_NAME,
                        Context.MODE_PRIVATE);
    }

    public void setAuthTocken(String b)
    {
        Editor editor = mPrefs.edit();
        editor.putString("auth",b);
        editor.commit();
    }
    public String getAuthTocken()
    {
        return mPrefs.getString("auth",null);
    }
    public void setEmail(String email)
    {
        Editor editor = mPrefs.edit();
        editor.putString("email",email);
        editor.commit();
    }
    public String getEmail()
    {
        return mPrefs.getString("email",null);
    }
    public void setPassword(String password)
    {
        Editor editor = mPrefs.edit();
        editor.putString("password",password);
        editor.commit();
    }
    public String getPassword()
    {
        return mPrefs.getString("password",null);
    }


    public String getDeviceid() {

        String id=mPrefs.getString("deviceid",null);
         if(id == null){
             return Settings.Secure.getString(ApplicationContextProvider.getContext().getContentResolver(),
                     Settings.Secure.ANDROID_ID);
         }
        return id;
    }
    public void setDeviceid(String deviceid)
    {
        Editor editor = mPrefs.edit();
        editor.putString("deviceid",deviceid);
        editor.commit();
    }
    public String getUserId() {

        return mPrefs.getString("userid",null);

    }
    public void setUserid(String userid)
    {
        Editor editor = mPrefs.edit();
        editor.putString("userid",userid);
        editor.commit();
    }

    public String getProfilePicUrl() {

        return mPrefs.getString("profilepicurl",null);

    }
    public void setProfilePicUrl(String profilePicUrl)
    {
        Editor editor = mPrefs.edit();
        editor.putString("profilepicurl",profilePicUrl);
        editor.commit();
    }

    public String getDriverLicenceNo() {
        return mPrefs.getString("driverLicenceNo",null);

    }
    public void setDriverLicenceNo(String driverLicenceNo)
    {
        if(driverLicenceNo!=null && driverLicenceNo.trim()!="") {
            Editor editor = mPrefs.edit();
            editor.putString("driverLicenceNo", driverLicenceNo);
            editor.commit();
        }
        else{
            Editor editor = mPrefs.edit();
            editor.putString("driverLicenceNo", null);
            editor.commit();
        }
    }
    public void clearAllSharedPrefs()
    {
        Editor editor = mPrefs.edit();
        editor.clear();
        editor.commit();
    }
}
