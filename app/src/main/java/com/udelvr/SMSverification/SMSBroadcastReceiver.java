package com.udelvr.SMSverification;

/**
 * Created by prasadshirsath on 5/11/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.udelvr.SplashScreen.SplashScreenFragmentRegisterNewUser;

public class SMSBroadcastReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    SplashScreenFragmentRegisterNewUser splashScreenFragmentRegisterNewUser=null;
    String verificationCode="";
    public SMSBroadcastReceiver(SplashScreenFragmentRegisterNewUser splashScreenFragmentRegisterNewUser)
    {
        this.splashScreenFragmentRegisterNewUser = splashScreenFragmentRegisterNewUser;
    }

    public void setVerificationCode(String code)
    {
        verificationCode=code;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Intent recieved: " + intent.getAction());

        if (intent.getAction() == SMS_RECEIVED) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[])bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                }
                if (messages.length > -1) {
                    Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                    if(messages[0].getMessageBody().contains("Udelvr")){
                        if(messages[0].getMessageBody().contains(verificationCode)){
                            splashScreenFragmentRegisterNewUser.onMobileNoVerificationSuccess();
                        }
                    }
                }
            }
        }



    }
}