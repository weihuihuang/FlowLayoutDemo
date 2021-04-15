package com.example.utils.view.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.utils.view.flowlayout.adapter.FlowAdapter;

//流式布局，不支持单个layout多行,每行高度一样
public class MyFlowLayout extends ViewGroup {

    public MyFlowLayout(Context context) {
        this(context, null);
    }

    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if(childCount == 0) {
            setMeasuredDimension(0,0 );
        }else {
            int childWidthViewRecord = 0;
            int childContentWidthTotal = 0;
            int childContentHeightTotal = 0;
            int singleChildHeightRecord = 0;
            for(int i = 0; i < childCount; i++){
                View childView = getChildAt(i);
                if(childView.getVisibility() == GONE){
                    continue;
                }
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams)childView.getLayoutParams();
                int childWidthUsed = childView.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                int childHeightUsed = childView.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                singleChildHeightRecord = childHeightUsed;
                int addCurChildWidthUsed = childWidthViewRecord + childWidthUsed;
                if(addCurChildWidthUsed + getPaddingLeft() + getPaddingRight() > widthSize){
                    childWidthViewRecord = childWidthUsed;
                    childContentHeightTotal += childHeightUsed;
                    childContentWidthTotal = widthSize;
                }else {
                    childWidthViewRecord = addCurChildWidthUsed;
                    if(childWidthViewRecord + getPaddingLeft() + getPaddingRight() > childContentWidthTotal){
                        childContentWidthTotal = childWidthViewRecord;
                    }
                }
            }
            //仅有单行的情况
            if(childContentWidthTotal > 0){
                childContentHeightTotal += singleChildHeightRecord;
            }
            //支持父布局padding
            childContentHeightTotal += getPaddingTop() + getPaddingBottom();
            childContentWidthTotal += getPaddingLeft() + getPaddingRight();
            // 考虑 WrapContent的情况
            if(widthSpec == MeasureSpec.AT_MOST && heightSpec == MeasureSpec.AT_MOST){
                setMeasuredDimension(childContentWidthTotal, childContentHeightTotal);
            }else if(widthSpec == MeasureSpec.AT_MOST){
                setMeasuredDimension(childContentWidthTotal, heightSize);
            }else if(heightSpec == MeasureSpec.AT_MOST){
                setMeasuredDimension(widthSize, childContentHeightTotal);
            }else {
                setMeasuredDimension(widthSize, heightSize);
            }
        }
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View view = getChildAt(i);
            if (view != null && view.getVisibility() != GONE){
                measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, 0);
            }
        }
    }

    //只处理了单行的情况，每个tagView
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int childCount = getChildCount();
        int childWidthRecord = 0;
        int childHeightRecord = 0;
        //支持父布局padding
        int childCanDrawWidth = width - getPaddingLeft() - getPaddingRight();
        for(int i = 0; i < childCount; i++){
            View childView = getChildAt(i);
            if(childView.getVisibility() == GONE){
                continue;
            }
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams)childView.getLayoutParams();
            int childWidthUsed = childView.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            int childHeightUsed = childView.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            int addCurChildWidthUsed = childWidthRecord + childWidthUsed;
            int left;
            int top;
            int right;
            int bottom;
            if(addCurChildWidthUsed + getPaddingLeft() + getPaddingRight() > childCanDrawWidth){
                left = marginLayoutParams.leftMargin + getPaddingLeft();
                childHeightRecord += childHeightUsed;
                top = childHeightRecord + marginLayoutParams.topMargin + getPaddingTop();
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                childWidthRecord = childWidthUsed;
            }else {
                left = childWidthRecord + marginLayoutParams.leftMargin + getPaddingLeft();
                top = childHeightRecord + marginLayoutParams.topMargin + getPaddingTop();
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                childWidthRecord += childWidthUsed;
            }
            childView.layout(left, top, right, bottom);
        }
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FlowLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new FlowLayoutParams(lp);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new FlowLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public static class FlowLayoutParams extends MarginLayoutParams {

        public FlowLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public FlowLayoutParams(int width, int height) {
            super(width, height);
        }

        public FlowLayoutParams(LayoutParams lp) {
            super(lp);
        }
    }

    private FlowAdapter flowAdapter;

    public void setAdapter(FlowAdapter flowAdapter){
        this.flowAdapter = flowAdapter;
        flowAdapter.setFlowLayout(this);
    }

}
