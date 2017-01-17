package com.oliveira.classificados.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.oliveira.classificados.R;
import com.oliveira.classificados.service.ToastService;


public class BaseActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    protected Toolbar mToolbar;
    private BroadcastReceiver receiverToast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(ToastService.KEY_MSG);
            Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");
//        Log.i(TAG, "Info");
//        Log.w(TAG, "Warnning");
//        Log.e(TAG, "Error" );


    }

    protected void setupToolbar(@StringRes int title) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void hideView(View view) {
        final Animation animOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        view.setVisibility(View.INVISIBLE);
        view.startAnimation(animOut);

    }

    public void replaceView(View oldView, View newView) {
        final Animation animIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);

        hideView(oldView);

        newView.setVisibility(View.VISIBLE);
        newView.startAnimation(animIn);
    }


    protected SharedPreferences getPref() {
        return getPreferences(MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiverToast, new IntentFilter(ToastService.ACTION_FILTER));
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");

        // desregistrar o receiverToast boa pratica do android
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(receiverToast);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.d(TAG, "onRestart");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.d(TAG, "onBackPressed");
    }
}
