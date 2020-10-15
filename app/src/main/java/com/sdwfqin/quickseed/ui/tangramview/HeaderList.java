package com.sdwfqin.quickseed.ui.tangramview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.sdwfqin.quickseed.R;
import com.sdwfqin.quickseed.model.TicketModel;
import com.sdwfqin.quicklib.utils.json.JSONUtil;
import com.sdwfqin.quicklib.utils.tangram.TangramItemModel;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;

public class HeaderList extends FrameLayout implements ITangramViewLifeCycle{
    private TextView mFrom, mTo;
    private TangramItemModel<TicketModel> model;
    public HeaderList(@NonNull Context context) {
        super(context);
        init();
    }

    public HeaderList(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_stroke_card_me, null);
        mFrom = view.findViewById(R.id.from);
        mTo = view.findViewById(R.id.to);
        addView(view);
    }

    @Override
    public void cellInited(BaseCell cell) {
        model = JSONUtil.from(cell.extras.toString()
                ,new TypeToken<TangramItemModel<TicketModel>>(){}.getType());
        Log.d("cell",model.params.toString());
    }

    @Override
    public void postBindView(BaseCell cell) {
        mFrom.setText(model.params.from);
        mTo.setText(model.params.to);
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
