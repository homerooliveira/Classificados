package com.oliveira.classificados.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.oliveira.classificados.App;
import com.oliveira.classificados.R;
import com.oliveira.classificados.database.MyStore;
import com.oliveira.classificados.database.model.ItemAd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.UUID;

public class FormItemActivity extends BaseActivity {


    private static final int CAPTURE_REQUEST = 0;
    private EditText mEtTitle;
    private EditText mEtDescription;
    private EditText mEtPrice;
    private ImageView mIvImage;
    private ItemAd mItemAd;
    private boolean mIsLocal;

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
            mIsLocal = intent.getBooleanExtra(ListActivity.IS_LOCAL, false);

            if (mIsLocal) {
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

    }


    private void init() {
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtDescription = (EditText) findViewById(R.id.et_description);
        mEtPrice = (EditText) findViewById(R.id.et_price);
        mIvImage = (ImageView) findViewById(R.id.iv_image);

        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, CAPTURE_REQUEST);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_REQUEST && resultCode == RESULT_OK) {
            Bitmap btm = (Bitmap) data.getExtras().get("data");

            if (btm == null) return;

            String imgName = UUID.randomUUID().toString() + ".jpg";

            File file = new File(getFilesDir().getAbsolutePath(), imgName);
            try {
                FileOutputStream out = new FileOutputStream(file);
                btm.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap btm2 = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + "/" + imgName);
            mIvImage.setImageBitmap(btm2);
        }
    }

    public void save(View view) {
        String title = mEtTitle.getText().toString();
        String description = mEtDescription.getText().toString();
        String price = mEtPrice.getText().toString();

        if (mIsLocal) {
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
        }else {
            saveServerData(title, description);
        }

    }

    private void saveServerData(String title, String description) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Salvando...");
        dialog.setCancelable(true);
        dialog.show();
    }
}
