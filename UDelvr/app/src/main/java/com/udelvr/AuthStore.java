package com.udelvr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

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
    public void setUsername(String email)
    {
        Editor editor = mPrefs.edit();
        editor.putString("email",email);
        editor.commit();
    }
    public String getUsername()
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
        return mPrefs.getString("deviceid",null);

    }
    public void setDeviceid(String deviceid)
    {
        Editor editor = mPrefs.edit();
        editor.putString("deviceid",deviceid);
        editor.commit();
    }
}
