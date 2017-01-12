package com.oliveira.classificados.fragment;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.oliveira.classificados.App;
import com.oliveira.classificados.R;

public class ChronometerFragment extends Fragment {

    private Chronometer mChronometer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chronometer, container);

        mChronometer = (Chronometer) view.findViewById(R.id.chronometer);

        return view;
    }

//    Não precisa mais pq o onResume já dá start no mCrhonometer.
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        mChronometer.start();
//    }

    private App getApp() {
        return (App) getActivity().getApplication();
    }

    @Override
    public void onResume() {
        super.onResume();

        mChronometer.setBase(getApp().getCurrentTime() + SystemClock.elapsedRealtime());
        mChronometer.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        getApp().setCurrentTime(mChronometer.getBase() - SystemClock.elapsedRealtime());
    }
}
