package com.sdwfqin.quickseed.model;

import androidx.annotation.NonNull;

import com.sdwfqin.quickseed.ui.tangramview.HeaderList;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONException;
import org.json.JSONObject;

//public class TicketModel extends BaseCell<HeaderList> {
public class TicketModel {
    public String from;
    public String to;

//    @Override
//    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
//        super.parseWith(data, resolver);
//        try {
//            if (data.has("imageUrl")) {
//                from = data.getString("imageUrl");
//            }
//            if (data.has("text")) {
//                to = data.getString("text");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void postBindView(@NonNull HeaderList view) {
//        super.postBindView(view);
//        view.setFrom(from);
//        view.setTo(to);
//    }
}
