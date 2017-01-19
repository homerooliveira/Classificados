package com.oliveira.classificados.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.oliveira.classificados.App;
import com.oliveira.classificados.R;
import com.oliveira.classificados.database.MyStore;
import com.oliveira.classificados.database.model.ItemAd;

import java.util.UUID;

public class FormItemActivity extends BaseActivity {


    private EditText mEtTitle;
    private EditText mEtDescription;
    private EditText mEtPrice;
    private ItemAd mItemAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form_item);
        setupToolbar(R.string.form_title_activity);


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        init();

        final Intent intent = getIntent();
        if (intent != null) {
            String itemGuid = intent.getStringExtra(MyStore.ItemAdTable.GUID);

            mItemAd = ItemAd.getByGuid(this, itemGuid);

            if (mItemAd != null) {
                Log.d(TAG, itemGuid);
                getSupportActionBar().setTitle(mItemAd.getTitle());


                mEtTitle.setText(mItemAd.getTitle());
                mEtDescription.setText(mItemAd.getDescription());
                mEtPrice.setText(mItemAd.getPrice().toString());
            }

        }

    }


    private void init() {
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtDescription = (EditText) findViewById(R.id.et_description);
        mEtPrice = (EditText) findViewById(R.id.et_price);
    }

    public void save(View view) {
        String title = mEtTitle.getText().toString();
        String description = mEtDescription.getText().toString();
        String price = mEtPrice.getText().toString();

        ContentValues values = new ContentValues();
        values.put(MyStore.ItemAdTable.TITLE, title);
        values.put(MyStore.ItemAdTable.DESCRIPTION, description);
        values.put(MyStore.ItemAdTable.PRICE, price);

        SQLiteDatabase db = App.getInstance(this).getDbHelper().getWritableDatabase();

        if (mItemAd == null) {
            values.put(MyStore.ItemAdTable.GUID, UUID.randomUUID().toString());
            db.insert(MyStore.ItemAdTable.TABLE_NAME, null, values);
        } else {
            db.update(MyStore.ItemAdTable.TABLE_NAME, values,
                    MyStore.ItemAdTable.GUID + " = ?", new String[]{mItemAd.getGuid()});

            ItemAd itemAd = ItemAd.getByGuid(this, mItemAd.getGuid());
            final Intent intent = new Intent();
            intent.putExtra(DetailActivity.ITEM_KEY, itemAd);

            setResult(RESULT_OK, intent);
        }

        finish();
    }
}
