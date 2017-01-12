package com.oliveira.classificados.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.oliveira.classificados.R;
import com.oliveira.classificados.bean.Category;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends BaseActivity {

    public static final String CATEGORY_KEY = "CATEGORY_KEY";
    private Spinner mSpCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter);
        setpToolbar(R.string.filter_activity_title);
        init();

        List<Category> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Category category = new Category(String.valueOf(i), String.format("Categoria %s", i));
            items.add(category);
        }

        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mSpCategory.setAdapter(adapter);

        String categoryId = getPref().getString(CATEGORY_KEY, null);
        if (categoryId != null) {
            for (int i = 0; i < items.size(); i++) {
                if (categoryId.equals(items.get(i).getId())){
                    mSpCategory.setSelection(i);
                    break;
                }
            }
        }
    }

    private void init() {
        mSpCategory = (Spinner) findViewById(R.id.sp_category);
    }

    @Override
    protected void onStop() {
        Category category = (Category) mSpCategory.getSelectedItem();
        getPref().edit()
                .putString(CATEGORY_KEY, category.getId())
                .apply();

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent();
        final Category item = (Category) mSpCategory.getSelectedItem();
        intent.putExtra(CATEGORY_KEY, item);

        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }
}
