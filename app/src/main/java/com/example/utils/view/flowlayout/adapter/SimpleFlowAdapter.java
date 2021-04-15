package com.example.utils.view.flowlayout.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.testapplication.R;
import com.example.utils.view.flowlayout.model.SimpleFlowItem;
import java.util.List;

public class SimpleFlowAdapter extends FlowAdapter<SimpleFlowItem> {

    public SimpleFlowAdapter(int tagLayoutId, List<SimpleFlowItem> flowItemList) {
        super(tagLayoutId, flowItemList);
    }

    @Override
    public void onChildViewMeasure(int position, View childView, SimpleFlowItem flowItem) {
        TextView textView = childView.findViewById(R.id.tag_view);
        textView.setText(flowItem.getDisplayName());
    }
}
