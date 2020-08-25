package com.sdwfqin.quickseed.ui.tangramview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sdwfqin.imageloader.util.DisplayUtil;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
import com.tmall.wireless.tangram.support.ExposureSupport;

import java.util.Locale;

public class HistoryTitle extends LinearLayout implements ITangramViewLifeCycle {
    private TextView mTextView;

    public HistoryTitle(Context context) {
        super(context);
        init();
    }

    public HistoryTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryTitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        int padding = DisplayUtil.dp2px(getContext(), 10);
        mTextView = new TextView(getContext());
        mTextView.setPadding(0, padding, 0, 0);
        addView(mTextView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
        if (cell.serviceManager != null) {
            ExposureSupport exposureSupport = cell.serviceManager.getService(ExposureSupport.class);
            if (exposureSupport != null) {
                exposureSupport.onTrace(this, cell, cell.type);
            }
        }
    }

    @Override
    public void postBindView(BaseCell cell) {
        mTextView.setText(cell.optParam("text").toString());
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
