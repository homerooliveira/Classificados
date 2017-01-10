package com.oliveira.classificados;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity {

    private ImageView mIvImage;
    private TextView mTvTitle;
    private TextView mTvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        init();

        mIvImage.setImageResource(R.drawable.google_pixel);
        mTvTitle.setText("Iphone 6 64GB");
        mTvDescription.setText("Muito Bom!!");
    }

    private void init() {
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvDescription = (TextView) findViewById(R.id.tv_description);

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
