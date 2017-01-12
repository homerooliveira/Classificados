package com.oliveira.classificados;


import android.app.Application;

public class App extends Application {

    private Long mCurrentTime;

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
