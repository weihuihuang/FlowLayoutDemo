package com.example.utils.view.flowlayout.model;

public class SimpleFlowItem implements FlowItem {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return getName();
    }
}
