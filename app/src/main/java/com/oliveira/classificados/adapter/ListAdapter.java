package com.oliveira.classificados.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oliveira.classificados.R;
import com.oliveira.classificados.activity.DetailActivity;
import com.oliveira.classificados.database.model.ItemAd;

import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemHolder> {

    private final Context mContext;
    private final List<ItemAd> mItems;

    public ListAdapter(Context context, List<ItemAd> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, null);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, int position) {
        final ItemAd item = mItems.get(position);
        holder.ivImage.setImageResource(R.mipmap.ic_launcher);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDescription.setText(item.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.ITEM_KEY, item);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDescription;
        ImageView ivImage;

        public ItemHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
        }
    }
}
