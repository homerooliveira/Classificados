package com.oliveira.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveira.classificados.R;
import com.oliveira.classificados.database.MyStore;
import com.oliveira.classificados.database.model.ItemAd;

public class DetailActivity extends BaseActivity {

    public static final String ITEM_KEY = "ITEM_KEY";
    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvDescription;
    private TextView mTvTotal;
    private ItemAd mItemAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        setupToolbar(R.string.detail_activity_title);

        init();

        final Intent intent = getIntent();
        if (intent != null) {
            mItemAd = (ItemAd) intent.getSerializableExtra(ITEM_KEY);

            mIvImage.setImageResource(R.drawable.google_pixel);
            mTvTitle.setText(mItemAd.getTitle());
            mTvDescription.setText(mItemAd.getDescription());
            mTvTotal.setText(getString(R.string.total_label, "3.800,90"));
        }

    }

    private void init() {
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvDescription = (TextView) findViewById(R.id.tv_description);
        mTvTotal = (TextView) findViewById(R.id.tv_total);

        Button btnBuy = (Button) findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Comprou!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void favor(View view) {
        Toast.makeText(this, "Favoritou!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                final Intent intent = new Intent(this, FormItemActivity.class);
                intent.putExtra(MyStore.ItemAdTable.GUID, mItemAd.getGuid());
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
