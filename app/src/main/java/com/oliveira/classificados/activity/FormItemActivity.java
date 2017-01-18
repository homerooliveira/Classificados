package com.oliveira.classificados.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.oliveira.classificados.App;
import com.oliveira.classificados.R;
import com.oliveira.classificados.database.MyStore;

import java.util.UUID;

public class FormItemActivity extends BaseActivity {


    private EditText mEtTitle;
    private EditText mEtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_item);
        setupToolbar(R.string.form_title_activity);


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        init();

    }

    private void init() {
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtDescription = (EditText) findViewById(R.id.et_description);
    }

    public void save(View view) {
        String title = mEtTitle.getText().toString();
        String description = mEtDescription.getText().toString();

        ContentValues values = new ContentValues();
        values.put(MyStore.ItemAdTable.GUID, UUID.randomUUID().toString());
        values.put(MyStore.ItemAdTable.TITLE, title);
        values.put(MyStore.ItemAdTable.DESCRIPTION, description);

        SQLiteDatabase db = App.getInstance(this).getDbHelper().getWritableDatabase();
        db.insert(MyStore.ItemAdTable.TABLE_NAME, null, values);

        startActivity(new Intent(this, FormItemActivity.class));
        finish();
    }
}
