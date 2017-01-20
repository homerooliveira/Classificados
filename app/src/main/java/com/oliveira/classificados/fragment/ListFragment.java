package com.oliveira.classificados.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oliveira.classificados.R;
import com.oliveira.classificados.activity.BaseActivity;
import com.oliveira.classificados.activity.FilterActivity;
import com.oliveira.classificados.activity.FormItemActivity;
import com.oliveira.classificados.activity.ListActivity;
import com.oliveira.classificados.adapter.ListAdapter;
import com.oliveira.classificados.database.model.ItemAd;
import com.oliveira.classificados.task.LoadDataTask;

import java.util.ArrayList;
import java.util.List;

import static com.oliveira.classificados.activity.ListActivity.IS_LOCAL;

public class ListFragment extends Fragment {

    private RecyclerView mRvList;
    private ListAdapter mAdapter;
    private List<ItemAd> mItems;
    private ProgressBar mSpinner;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mTvProgress;

    private boolean mIsLocal;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        init(view);

        // TODO: 19/01/2017 Remover IS_LOCAL da activity e colocar no fragment
        mIsLocal = getArguments().getBoolean(IS_LOCAL);

        mItems = new ArrayList<>();
        mAdapter = new ListAdapter(getActivity(), mItems);
        mRvList.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new AddItemTask().execute("Novo Item");

            }
        });

        loadData();

        return view;
    }


    private void init(View v) {
        mRvList = (RecyclerView) v.findViewById(R.id.rv_list);
        mSpinner = (ProgressBar) v.findViewById(R.id.spinner);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        mTvProgress = (TextView) v.findViewById(R.id.tv_progress);
    }

    public void loadData() {
        mItems.clear();

        mRvList.setVisibility(View.INVISIBLE);
        mSpinner.setVisibility(View.VISIBLE);

        if (mIsLocal) {
            final LoadDataTask loadDataTask =
                    new LoadDataTask(mItems, mAdapter, ((BaseActivity) getActivity()), mSpinner, mRvList, mTvProgress);
            loadDataTask.execute();
        } else {
            loadServerData();
        }

    }

    private void loadServerData() {

    }

    public void filter() {
        final Intent intent = new Intent(getActivity(), FilterActivity.class);
        startActivityForResult(intent, 0);
    }


    class AddItemTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                Thread.sleep(2 * 1000);// 2 segs

            } catch (InterruptedException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }

            final String title = strings[0];
            mItems.add(0, new ItemAd(null, title, "Minha descrição " +
                    "do meu segundo item adicionado no meu layout da minha aplicação"));

            return Boolean.TRUE;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);

            if (success) {
                mAdapter.notifyItemRangeChanged(0, mItems.size());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    public void newItem() {
        startActivity(new Intent(getActivity(), FormItemActivity.class));
    }

}
