package org.base.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import org.base.example.adapter.TheOneAdapter;
import org.base.example.adapter.touchhelper.HorizontalTouchHelperCallback;
import org.base.example.adapter.touchhelper.TouchListener;
import org.base.example.adapter.viewtype.DayViewType;
import org.base.example.entity.DayEntity;
import org.base.example.entity.LandscapeEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements DayViewType.ContentViewTypeListener, TouchListener {



    private TheOneAdapter adapter;
    private List<DayEntity> dayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bindContentRecyclerView();
    }

    private void init() {
        dayList = generateDayList(5);
    }

    private void bindContentRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.activityMain_contentRecyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new HorizontalTouchHelperCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<TheOneAdapter.ViewTypeInterface> itemTypeList = new ArrayList<>();

        itemTypeList.add(new DayViewType(this, dayList));
        adapter = new TheOneAdapter(this, itemTypeList);
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    public List<DayEntity> generateDayList(int size) {
        List<DayEntity> dayList = new ArrayList<>();
        for(int i = 0; i < size; i ++) {
            DayEntity dayEntity = new DayEntity();
            dayEntity.setTitle("Day: " + i);
            List<LandscapeEntity> landscapeList = generateLandScapeList(i + 1);
            dayEntity.setLandscapeList(landscapeList);
            dayList.add(dayEntity);
        }
        return dayList;
    }

    public List<LandscapeEntity> generateLandScapeList(int size) {
        List<LandscapeEntity> landscapeList = new ArrayList<>();
        for(int i = 0; i < size; i ++) {
            LandscapeEntity landscapeEntity = new LandscapeEntity();
            landscapeEntity.setTitle("Landscape: " + i);
            landscapeList.add(landscapeEntity);
        }
        return landscapeList;
    }

    @Override
    public void onContentItemClick(int index) {
        Toast.makeText(this, "onContentItemClick index: " + index, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContentItemLongClick(int index) {
        Toast.makeText(this, "onContentItemLongClick index: " + index, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecyclerViewItemMove(int fromPosition, int toPosition) {
        Collections.swap(dayList, fromPosition, toPosition);
        adapter.notifyItemMoved(fromPosition, toPosition);
    }
}
