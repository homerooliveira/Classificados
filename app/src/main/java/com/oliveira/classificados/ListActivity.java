package com.oliveira.classificados;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends BaseActivity {

    private RecyclerView mRvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activiy_list);
        init();

        final List<ItemAd> items = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            final String title = String.format("Item %s", i);
            final String description =
                    String.format("Descrição do meu item da minha lista de Recyclerview do Curso de Android da Pucrs %s", i);
            final ItemAd itemAd = new ItemAd(null, title, description);
            items.add(itemAd);
        }

        final ListAdapter adapter = new ListAdapter(items);

        mRvList.setLayoutManager(new LinearLayoutManager(this));
        mRvList.setAdapter(adapter);
    }

    private void init() {
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
    }
}
