package com.sdwfqin.quickseed.ui.components;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.Gson;
import com.sdwfqin.quicklib.utils.tangram.TangramHelper;
import com.sdwfqin.quicklib.utils.tangram.TangramListModel;
import com.sdwfqin.quicklib.utils.tangram.TangramListType;
import com.sdwfqin.quickseed.constants.ArouterConstants;
import com.sdwfqin.quickseed.databinding.ActivityVlayoutSampleBinding;
import com.sdwfqin.quickseed.model.TicketModel;
import com.sdwfqin.quickseed.ui.tangramview.HeaderList;
import com.sdwfqin.quickseed.ui.tangramview.HistoryList;
import com.sdwfqin.quickseed.ui.tangramview.HistoryTitle;
import com.sdwfqin.quickseed.ui.tangramview.OrderList;
import com.sdwfqin.quickseed.ui.tangramview.OrderTitle;

import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.support.async.AsyncLoader;
import com.tmall.wireless.tangram.support.async.AsyncPageLoader;
import com.tmall.wireless.tangram.support.async.CardLoadSupport;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.sdwfqin.samplecommonlibrary.base.SampleBaseActivity;
import io.github.sdwfqin.samplecommonlibrary.utils.assets.AssetsUtils;

/**
 * VLayoutSample
 * <p>
 *
 * @author 张钦
 * @date 2019-06-28
 */
@Route(path = ArouterConstants.COMPONENTS_VLAYOUTSAMPLE)
public class VLayoutSampleActivity extends SampleBaseActivity<ActivityVlayoutSampleBinding> {
    private TangramEngine mEngine;
    private String TAG = VLayoutSampleActivity.class.getSimpleName();

    @Override
    protected ActivityVlayoutSampleBinding getViewBinding() {
        return ActivityVlayoutSampleBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initEventAndData() {
        mTopBar.setTitle("VLayoutSample");
        mTopBar.addLeftBackImageButton()
                .setOnClickListener(v -> finish());
        TangramHelper.init(this);
        TangramBuilder.InnerBuilder builder = TangramHelper.get(this);
        builder.registerCell("HeaderList", HeaderList.class);
        builder.registerCell("HistoryTitle", HistoryTitle.class);
        builder.registerCell("HistoryList", HistoryList.class);
        builder.registerCell("OrderTitle", OrderTitle.class);
        builder.registerCell("OrderList", OrderList.class);
        mEngine = builder.build();
        mEngine.setVirtualViewTemplate(AssetsUtils.getAssetsFile(this, "VVTest.out"));
        CardLoadSupport cardLoadSupport = new CardLoadSupport(new AsyncLoader() {
            @Override
            public void loadData(Card card, @NonNull LoadedCallback callback) {
                Log.d(TAG, "loadData: cardType=" + card.stringType);
            }
        }, new AsyncPageLoader() {
            @Override
            public void loadData(int page, @NonNull Card card, @NonNull LoadedCallback callback) {
                Log.d(TAG, "loadData: page=" + page + ", cardType=" + card.stringType);
            }
        });
        CardLoadSupport.setInitialPage(1);
        mEngine.addCardLoadSupport(cardLoadSupport);
        mEngine.bindView(mBinding.rvList);
        datas.clear();
        initHeader();
        initHistoryTitle();
        initHistoryList();
        initOrderTitle();
        initOrderList();

//        mDelegateAdapter.setAdapters(mAdapters);

        initData();
        TicketModel model = new TicketModel();
        model.from = "温州";
        model.to = "杭州";
        Log.d("Gson",new Gson().toJson(model));
    }

    @Override
    protected void initClickListener() {

    }

    private List<TangramListModel> datas = new ArrayList<>();

    private void initHeader() {
        List<TicketModel> models = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TicketModel model = new TicketModel();
            model.from = i+"";
            model.to = i+1+"";
            models.add(model);
        }
        datas.addAll(TangramHelper.getData(TangramListType.ContainerOneColumn,
                "HeaderList",
                models));
//                Arrays.asList(1, 2, 3, 4, 5)));
    }

    private void initHistoryTitle() {
        datas.addAll(TangramHelper.getData(TangramListType.ContainerOneColumn,
                "HistoryTitle",
                Arrays.asList(1)));

    }

    private void initHistoryList() {
        datas.addAll(TangramHelper.getData(TangramListType.ContainerOneColumn,
                "HistoryList",
                Arrays.asList(1, 2, 3, 4, 5)));
    }

    private void initOrderTitle() {
        datas.addAll(TangramHelper.getData(TangramListType.ContainerOneColumn,
                "OrderTitle",
                Arrays.asList(1)));
    }

    private void initOrderList() {
        datas.addAll(TangramHelper.getData(TangramListType.ContainerOneColumn,
                "OrderList",
                Arrays.asList(1, 2, 3, 4, 5)));
    }

    private void initData() {
        JSONArray array = null;
        try {
            array = new JSONArray(GsonUtils.toJson(datas));
            mEngine.setData(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        mEngine.destroy();
        super.onDestroy();
    }
}
