package com.oliveira.classificados.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.oliveira.classificados.R;

public class FormItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_item);
        setupToolbar(R.string.form_title_activity);


    }

    public void save(View view) {

    }
}
