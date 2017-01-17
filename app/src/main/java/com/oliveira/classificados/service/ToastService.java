package com.oliveira.classificados.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

// Se as tarefas demoram muito melhor usar IntenntService
public class ToastService extends IntentService {

    public static final String KEY_MSG = "key_msg";
    public static final String ACTION_FILTER = "action_filter";

    public ToastService() {
        super("ToastService");// Identificador do service
    }

    @Override
    protected void onHandleIntent(final Intent intent) {

        final String msg = intent.getStringExtra(KEY_MSG);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(5 * 1000); // 5 seg
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intentBrod = new Intent(ACTION_FILTER);
                    intentBrod.putExtra(KEY_MSG, msg);

                    LocalBroadcastManager.getInstance(ToastService.this)
                            .sendBroadcast(intentBrod);
                }
            }
        }).start();

    }
}
