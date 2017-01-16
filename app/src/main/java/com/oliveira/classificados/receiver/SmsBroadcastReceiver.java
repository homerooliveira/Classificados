package com.oliveira.classificados.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Objects;


public class SmsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.provider.Telephony.SMS_RECEIVED")) {
            SmsMessage sms = Telephony.Sms.Intents.getMessagesFromIntent(intent)[0];

            Toast.makeText(context,
                    String.format("FROM: %s \nMSG: %s", sms.getOriginatingAddress(), sms.getMessageBody()),
                    Toast.LENGTH_SHORT).show();

        }
    }
}
