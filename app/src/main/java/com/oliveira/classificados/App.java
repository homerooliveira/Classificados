package com.oliveira.classificados;


import android.app.Activity;
import android.app.Application;

import com.oliveira.classificados.activity.BaseActivity;
import com.oliveira.classificados.database.DbHelper;

public class App extends Application {

    private Long mCurrentTime;
    private DbHelper mDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        mDbHelper = new DbHelper(getApplicationContext());
    }


    public DbHelper getDbHelper() {
        return mDbHelper;
    }
    public static App getInstance(BaseActivity activity){
        return (App) activity.getApplication();
    }

    public Long getCurrentTime() {
        if (mCurrentTime == null) {
            mCurrentTime = 0L;
        }
        return mCurrentTime;
    }

    public void setCurrentTime(Long currentTime) {
        mCurrentTime = currentTime;
    }


}
