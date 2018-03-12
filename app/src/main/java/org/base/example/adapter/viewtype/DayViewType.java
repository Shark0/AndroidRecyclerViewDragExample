package org.base.example.adapter.viewtype;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import org.base.example.R;
import org.base.example.adapter.TheOneAdapter;
import org.base.example.adapter.touchhelper.TouchListener;
import org.base.example.adapter.touchhelper.VerticalTouchHelperCallback;
import org.base.example.entity.DayEntity;
import org.base.example.entity.LandscapeEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayViewType implements TheOneAdapter.ViewTypeInterface, LandscapeViewType.LandScapeViewTypeListener {

    private ContentViewTypeListener listener;
    private List<DayEntity> dayList;

    public DayViewType(ContentViewTypeListener listener, List<DayEntity> dayList) {
        this.listener = listener;
        this.dayList = dayList;
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_day_item;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(View itemView) {
        return new ContentViewHolder(itemView, listener);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder viewHolder, int index) {
        bindContentViewHolder((ContentViewHolder) viewHolder, index);
    }

    private void bindContentViewHolder(ContentViewHolder viewHolder, int index) {
        viewHolder.itemView.setTag(index);
        DayEntity dayEntity = dayList.get(index);
        bindTextView(viewHolder, dayEntity);
        bindLandScapeRecyclerView(viewHolder, dayEntity);
    }

    private void bindTextView(ContentViewHolder viewHolder, DayEntity dayEntity) {
        viewHolder.itemTextView.setText(dayEntity.getTitle());
    }

    private void bindLandScapeRecyclerView(ContentViewHolder viewHolder, DayEntity dayEntity) {
        viewHolder.setLandscapeList(dayEntity.getLandscapeList());
    }

    @Override
    public void onContentItemClick(int index) {

    }

    @Override
    public void onContentItemLongClick(int index) {

    }

    private class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, LandscapeViewType.LandScapeViewTypeListener, TouchListener {
        private ContentViewTypeListener listener;
        private TextView itemTextView;
        private RecyclerView landscapeRecyclerView;
        private TheOneAdapter adapter;
        private List<LandscapeEntity> landscapeList = new ArrayList<>();

        public ContentViewHolder(View itemView, ContentViewTypeListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemTextView = itemView.findViewById(R.id.adapterDayItem_titleTextView);

            landscapeRecyclerView = itemView.findViewById(R.id.adapterDayItem_landscapeRecyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            landscapeRecyclerView.setLayoutManager(linearLayoutManager);

            List<TheOneAdapter.ViewTypeInterface> itemTypeList = new ArrayList<>();
            itemTypeList.add(new LandscapeViewType(this, landscapeList));
            adapter = new TheOneAdapter(itemView.getContext(), itemTypeList);
            landscapeRecyclerView.setAdapter(adapter);

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new VerticalTouchHelperCallback(this));
            itemTouchHelper.attachToRecyclerView(landscapeRecyclerView);
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

        public void setLandscapeList(List<LandscapeEntity> landscapeList) {
            this.landscapeList.clear();
            this.landscapeList.addAll(landscapeList);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onRecyclerViewItemMove(int fromPosition, int toPosition) {
            Collections.swap(landscapeList, fromPosition, toPosition);
            adapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onContentItemClick(int index) {

        }

        @Override
        public void onContentItemLongClick(int index) {

        }
    }

    public interface ContentViewTypeListener {
        public void onContentItemClick(int index);
        public void onContentItemLongClick(int index);
    }
}
