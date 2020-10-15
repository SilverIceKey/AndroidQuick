package com.sdwfqin.quicklib.utils.tangram;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sdwfqin.imageloader.ImageLoader;
import com.sdwfqin.quicklib.utils.json.JSONUtil;
import com.sdwfqin.widget.recyclerview.NoBackgroundView;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.util.IInnerImageSetter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TangramHelper {
    public static void init(Context context) {
        TangramBuilder.init(context, new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view,
                                                                 @Nullable String url) {
                new ImageLoader.Builder().setImagePath(url).build(view).loadImage();
                //假设你使用 Picasso 加载图片                		Picasso.with(context).load(url).into(view);
            }
        }, ImageView.class);
    }

    public static TangramBuilder.InnerBuilder get(Context context) {
        // 初始化 TangramBuilder
        TangramBuilder.InnerBuilder builder = TangramBuilder.newInnerBuilder(context);
        // 注册自定义的卡片和组件
        builder.registerCell("NoBackground", NoBackgroundView.class);
        // 注册 VirtualView 版本的 Tangram 组件
//        builder.registerVirtualView("VVTest");
        return builder;
    }

    public static <T> List<TangramListModel> getData(String type, String itemType, List<T> items) {
        List<TangramListModel> datas = new ArrayList<>();
        TangramListModel listModel = new TangramListModel();
        listModel.type = type;
        List<TangramItemModel> tmpItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            TangramItemModel model = new TangramItemModel();
            model.type = itemType;
            model.params = items.get(i);
            tmpItems.add(model);
        }
        listModel.items = tmpItems;
        datas.add(listModel);
        return datas;
    }

    public static <T> JSONArray getJSONData(T data){
        String json = JSONUtil.toJson(data);
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(json);
            return jsonArray;
        }catch (Exception e){

        }
        return jsonArray;
    }
}
