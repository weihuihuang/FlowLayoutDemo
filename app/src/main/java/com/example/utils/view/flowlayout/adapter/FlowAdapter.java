package com.example.utils.view.flowlayout.adapter;

import android.view.LayoutInflater;
import android.view.View;
import com.example.utils.view.flowlayout.MyFlowLayout;
import com.example.utils.view.flowlayout.model.FlowItem;
import java.util.List;

public abstract class FlowAdapter<T extends FlowItem> {

    private int tagLayoutId;

    private List<T> dataSource;

    private MyFlowLayout flowLayout;

    FlowAdapter(int tagLayoutId, List<T> flowItemList){
        this.tagLayoutId = tagLayoutId;
        dataSource = flowItemList;
    }

    public void setFlowLayout(MyFlowLayout flowLayout){
        this.flowLayout = flowLayout;
    }

    public void notifyDataSetChange(){
        if(flowLayout != null){
            flowLayout.removeAllViews();
        }
        if(dataSource != null && dataSource.size() != 0 && tagLayoutId != 0 && flowLayout != null){
            for(int i = 0 ; i < dataSource.size(); i ++){
                T item = dataSource.get(i);
                View childView = LayoutInflater.from(flowLayout.getContext()).inflate(tagLayoutId, flowLayout, false);
                flowLayout.addView(childView);
                onChildViewMeasure(i, childView, item);
            }
        }
    }

    public abstract void onChildViewMeasure(int position, View childView, T flowItem);
}
