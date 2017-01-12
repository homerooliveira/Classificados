package com.oliveira.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliveira.classificados.R;
import com.oliveira.classificados.bean.ItemAd;

import java.io.Serializable;

public class DetailActivity extends BaseActivity {

    public static final String ITEM_KEY = "ITEM_KEY";
    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvDescription;
    private TextView mTvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        init();

        final Intent intent = getIntent();
        if (intent != null) {
            final ItemAd item = (ItemAd) intent.getSerializableExtra(ITEM_KEY);

            mIvImage.setImageResource(R.drawable.google_pixel);
            mTvTitle.setText(item.getTitle());
            mTvDescription.setText(item.getDescription());
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
}
