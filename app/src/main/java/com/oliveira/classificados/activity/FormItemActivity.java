package com.oliveira.classificados.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.oliveira.classificados.R;

public class FormItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_item);
        setupToolbar(R.string.form_title_activity);


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

    }

    public void save(View view) {

    }
}
