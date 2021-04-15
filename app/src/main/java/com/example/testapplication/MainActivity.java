package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.utils.view.flowlayout.MyFlowLayout;
import com.example.utils.view.flowlayout.adapter.SimpleFlowAdapter;
import com.example.utils.view.flowlayout.model.SimpleFlowItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyFlowLayout flowLayout = findViewById(R.id.flow_layout);
        List<SimpleFlowItem> dataSource = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            SimpleFlowItem item = new SimpleFlowItem();
            if(i % 2 == 0) {
                item.setName("短内容-->" + i);
            }else if(i % 3 == 0){
                item.setName("中等内容中等内容-->" + i);
            }else {
                item.setName("长内容长内容长内容长内容长内容-->" + i);
            }
            dataSource.add(item);
        }
        SimpleFlowAdapter adapter = new SimpleFlowAdapter(R.layout.flow_item_view, dataSource);
        flowLayout.setAdapter(adapter);
        adapter.notifyDataSetChange();
    }
}