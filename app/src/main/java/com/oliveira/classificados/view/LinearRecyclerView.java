package com.oliveira.classificados.view;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.oliveira.classificados.R;

public class LinearRecyclerView extends RecyclerView {


    public LinearRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setLayoutManager(new LinearLayoutManager(context));
        final int padding = (int) context.getResources().getDimension(R.dimen.list_padding);
        Log.d("PADDING", "Padding " + padding);
        setPadding(padding, padding, padding, padding);
    }

}
