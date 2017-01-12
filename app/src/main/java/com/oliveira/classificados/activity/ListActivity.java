package com.oliveira.classificados.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.oliveira.classificados.adapter.ListAdapter;
import com.oliveira.classificados.R;
import com.oliveira.classificados.bean.Category;
import com.oliveira.classificados.bean.ItemAd;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity {

    private RecyclerView mRvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activiy_list);
        setpToolbar(R.string.list_activity_title);
        init();

        final List<ItemAd> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            final String title = String.format("Item %s", i);
            final String description =
                    String.format("Descrição do meu item da minha lista de Recyclerview do Curso de Android da Pucrs %s", i);
            final ItemAd itemAd = new ItemAd(null, title, description);
            items.add(itemAd);
        }

        final ListAdapter adapter = new ListAdapter(this, items);
        mRvList.setAdapter(adapter);
    }

    private void init() {
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Category category = (Category) data.getSerializableExtra(FilterActivity.CATEGORY_KEY);
        Toast.makeText(this, category.getDescription(), Toast.LENGTH_LONG).show();
    }

    public void filter(View view) {
        final Intent intent = new Intent(this, FilterActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_view:
                if (mRvList.getLayoutManager() instanceof GridLayoutManager) {
                    mRvList.setLayoutManager(new LinearLayoutManager(this));
                } else {
                    mRvList.setLayoutManager(new GridLayoutManager(this, 2));
                }

                mRvList.getAdapter().notifyItemRangeChanged(0, mRvList.getAdapter().getItemCount());
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
