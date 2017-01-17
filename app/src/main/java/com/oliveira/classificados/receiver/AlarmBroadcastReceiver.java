package com.oliveira.classificados.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.oliveira.classificados.activity.ListActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String MSG_KEY = "msg_key";

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra(MSG_KEY);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        final Intent intent2 = new Intent(context, ListActivity.class);
        context.startActivity(intent2);

    }
}
