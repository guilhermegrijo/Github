package com.example.pc.github.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.github.R;
import com.example.pc.github.domain.model.Item;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> mItemList;
    private OnItemClickListener listener;

    void setItemList(final List<Item> itemList) {
        mItemList = itemList;

        notifyDataSetChanged();
    }

    void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repositories_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).populateItemRows((ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.stars)
        TextView stars;
        @BindView(R.id.user)
        TextView owner;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void populateItemRows(ItemViewHolder viewHolder, int position) {
            viewHolder.description.setText(mItemList.get(position).description);
            viewHolder.title.setText(mItemList.get(position).name);
            viewHolder.stars.setText(Integer.toString(mItemList.get(position).stars));
            viewHolder.owner.setText(mItemList.get(position).owner.login);
            itemView.setOnClickListener(v -> listener.onItemClick(mItemList.get(position)));

        }
    }
}