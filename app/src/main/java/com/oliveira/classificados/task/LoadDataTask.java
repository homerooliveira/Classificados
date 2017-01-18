package com.oliveira.classificados.task;


import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.oliveira.classificados.R;
import com.oliveira.classificados.activity.BaseActivity;
import com.oliveira.classificados.adapter.ListAdapter;
import com.oliveira.classificados.database.model.ItemAd;

import java.util.List;

// Parametro para passar no execute, valor que vai passar para progresso, o que retorna
public class LoadDataTask extends AsyncTask<Void, Integer, Boolean> {

    private TextView mTvProgress;
    private List<ItemAd> mItems;
    private ListAdapter mAdapter;
    private BaseActivity mContext;

    private View mSpinner;
    private View mRvList;


    public LoadDataTask(List<ItemAd> mItems,
                        ListAdapter mAdapter,
                        BaseActivity mContext,
                        View mSpinner,
                        View mRvList,
                        TextView tvProgress) {

        this.mItems = mItems;
        this.mAdapter = mAdapter;
        this.mContext = mContext;
        this.mSpinner = mSpinner;
        this.mRvList = mRvList;
        this.mTvProgress = tvProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mTvProgress.setText(R.string.preparing_data);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        mTvProgress.setText(mContext.getString(R.string.progress_data, values[0]));
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        try {
            Thread.sleep(1 * 1000);// 3 segs

        } catch (InterruptedException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }

        for (int i = 1; i < 2; i++) {

            try {

                Thread.sleep(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final String title = String.format("Item %s", i);
            final String description =
                    String.format("Descrição do meu item da minha lista de Recyclerview do Curso de Android da Pucrs %s", i);

            final ItemAd itemAd = new ItemAd(null, title, description);
            mItems.add(itemAd);

            int progress = (i * 100) / 50;
            publishProgress(progress);
        }


        return Boolean.TRUE;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        if (success) {
            mAdapter.notifyDataSetChanged();
            mContext.replaceView(mSpinner, mRvList);

            if (!mItems.isEmpty()) {
                mContext.hideView(mTvProgress);
            }else {
                mTvProgress.setText(R.string.no_data);
            }
        }

    }
}
