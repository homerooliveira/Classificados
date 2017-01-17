package com.oliveira.classificados.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

        final Handler handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                String msgTxt = (String) msg.obj;
                Toast.makeText(ToastService.this, msgTxt, Toast.LENGTH_SHORT).show();
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {

                int i = 0;
                while (!Thread.interrupted()) {
                    i++;

                    try {
                        Thread.sleep(5 * 1000); // 5 seg
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Esta versão utiliza a fila do android
                    Message message = new Message();
                    message.obj = String.format("%s: %s", msg, i);
                    // Este método envia para fila do android
                    handler.sendMessage(message);

                    //Local Broadcast
//
//                    Intent intentBrod = new Intent(ACTION_FILTER);
//                    intentBrod.putExtra(KEY_MSG, msg);
//
//                    LocalBroadcastManager.getInstance(ToastService.this)
//                            .sendBroadcast(intentBrod);
                }
            }
        }).start();

    }
}
