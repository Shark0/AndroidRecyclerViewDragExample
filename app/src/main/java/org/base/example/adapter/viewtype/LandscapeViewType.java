package org.base.example.adapter.viewtype;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.base.example.R;
import org.base.example.adapter.TheOneAdapter;
import org.base.example.entity.LandscapeEntity;

import java.util.List;

public class LandscapeViewType implements TheOneAdapter.ViewTypeInterface {

    private LandScapeViewTypeListener listener;
    private List<LandscapeEntity> landscapeList;

    public LandscapeViewType(LandScapeViewTypeListener listener, List<LandscapeEntity> landscapeList) {
        this.listener = listener;
        this.landscapeList = landscapeList;
    }

    @Override
    public int getItemCount() {
        return landscapeList.size();
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_landscape_item;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View itemView) {
        return new LandscapeViewHolder(itemView, listener);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder viewHolder, int index) {
        bindContentViewHolder((LandscapeViewHolder) viewHolder, index);
    }

    private void bindContentViewHolder(LandscapeViewHolder viewHolder, int index) {
        viewHolder.itemView.setTag(index);
        viewHolder.itemTextView.setText(landscapeList.get(index).getTitle());
    }

    private class LandscapeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private LandScapeViewTypeListener listener;
        private TextView itemTextView;

        public LandscapeViewHolder(View itemView, LandScapeViewTypeListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemTextView = itemView.findViewById(R.id.adapterLandscapeItem_titleTextView);
        }

        @Override
        public void onClick(View view) {
            listener.onContentItemClick((Integer) view.getTag());
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onContentItemLongClick((Integer) view.getTag());
            return false;
        }
    }

    public interface LandScapeViewTypeListener {
        public void onContentItemClick(int index);
        public void onContentItemLongClick(int index);
    }
}
