package com.sdwfqin.quickseed.ui.tangramview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sdwfqin.imageloader.util.DisplayUtil;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
import com.tmall.wireless.tangram.support.ExposureSupport;

public class OrderTitle extends LinearLayout implements ITangramViewLifeCycle {
    private TextView mTextView;

    public OrderTitle(Context context) {
        super(context);
        init();
    }

    public OrderTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OrderTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        int padding = DisplayUtil.dp2px(getContext(), 10);
        mTextView = new TextView(getContext());
        mTextView.setPadding(padding, padding, padding, padding);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setText("推荐订单");
        addView(mTextView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
        if (cell.serviceManager!=null){
            ExposureSupport exposureSupport = cell.serviceManager.getService(ExposureSupport.class);
            if (exposureSupport != null) {
                exposureSupport.onTrace(this, cell, cell.type);
            }
        }
    }

    @Override
    public void postBindView(BaseCell cell) {

    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
