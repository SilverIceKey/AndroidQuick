package com.tckx.tckx_demo.common.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.sdwfqin.quicklib.mvvm.BaseViewModel;
import com.tckx.tckx_demo.R;
import com.tckx.tckx_demo.common.features.feature_fileupload.FileUploadPathEnum;
import com.tckx.tckx_demo.common.features.feature_fileupload.FileUploadResponse;
import com.tckx.tckx_demo.common.features.feature_fileupload.FileUploadUtils;
import com.tckx.tckx_demo.common.features.feature_loading.GlobalDialogManager;
import com.tckx.tckx_demo.common.features.feature_local_media.LocalMediaModel;
import com.tckx.tckx_demo.common.retrofit.ResponseCallback;
import com.tckx.tckx_demo.common.utils.picture.PictureSelectUtils;
import com.tckx.tckx_demo.common.utils.string.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaseVM<V extends ViewBinding> extends BaseViewModel {
    protected BaseActivity activity;
    protected BaseFragment fragment;
    protected List<LocalMedia> mSelectedImage = new ArrayList<>();
    protected List<LocalMedia> mSelectedVideo = new ArrayList<>();
    protected List<LocalMedia> mSelected = new ArrayList<>();
    private BaseQuickAdapter adapter;
    protected String imagePath;
    protected String videoPaths;
    protected V mBinding;

    public void setBinding(V mBinding) {
        this.mBinding = mBinding;
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    public void finish() {
        if (activity != null) {
            activity.finish();
        }
    }

    public void setLocalMedia(BaseQuickAdapter adapter) {
        setLocalMedia(adapter, null);
    }

    public void setLocalMedia(BaseQuickAdapter adapter, OnItemChildClickListener listener) {
        mSelected.add(new LocalMedia());
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                LocalMediaModel model = (LocalMediaModel) adapter.getItem(position);
                Bundle bundle = new Bundle();
                switch (view.getId()) {
                    case R.id.local_img:
                        if (model.getType() == LocalMediaModel.SELECT_ALL) {
                            selectLocalMedia();
                        } else {
                            bundle.putSerializable("data", (Serializable) LocalMediaModel.getImageList(mSelectedImage));
                            bundle.putInt("position", position);
                            bundle.putInt("all_nums", mSelected.size() - 1);
                            ARouter.getInstance().build(ArouterConstants.COMMON_MEDIA_VIEW)
                                    .with(bundle).navigation();
                        }
                        break;
                    case R.id.media_mask:
                        bundle.putSerializable("data", (Serializable) LocalMediaModel.getImageList(mSelectedImage));
                        bundle.putInt("position", position);
                        bundle.putInt("all_nums", mSelected.size() - 1);
                        ARouter.getInstance().build(ArouterConstants.COMMON_MEDIA_VIEW)
                                .with(bundle).navigation();
                        break;
                    case R.id.media_play:
                        bundle.putSerializable("data", (Serializable) LocalMediaModel.getImageList(mSelectedImage));
                        bundle.putInt("position", position);
                        bundle.putInt("all_nums", mSelected.size() - 1);
                        ARouter.getInstance().build(ArouterConstants.COMMON_MEDIA_VIEW)
                                .with(bundle).navigation();
                        break;
                    case R.id.delete:
                        mSelected.remove(position);
                        break;
                }
            }
        });
        adapter.setNewInstance(LocalMediaModel.getLocalMediaModels(mSelected, LocalMediaModel.SELECT_ALL));
        this.adapter = adapter;
    }

    public void selectLocalMedia() {
        if (mSelected.size() > 0) {
            mSelected.remove(mSelected.size() - 1);
        }
        new PictureSelectUtils.Builder()
                .setPictureMimeType(PictureMimeType.ofAll())
                .setSignle(false)
                .setCrop(false)
                .setSelectedImage(mSelectedImage)
                .setSelectedVideo(mSelectedVideo)
                .setMaxLocalMedia(9)
                .build()
                .pictureSelect(activity, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        mSelectedImage = result;
                        mSelected.clear();
                        mSelected.addAll(mSelectedImage);
                        mSelected.addAll(mSelectedVideo);
                        if (mSelected.size() < 9) {
                            mSelected.add(new LocalMedia());
                        }
                        adapter.setNewInstance(LocalMediaModel.getLocalMediaModels(mSelected, LocalMediaModel.SELECT_ALL));
                    }

                    @Override
                    public void onCancel() {
                        if (mSelectedImage.size() != 9) {
                            mSelectedImage.add(new LocalMedia());
                        }
                    }
                }, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        mSelectedImage = result;
                        mSelected.clear();
                        mSelected.addAll(mSelectedImage);
                        mSelected.addAll(mSelectedVideo);
                        if (mSelected.size() < 9) {
                            mSelected.add(new LocalMedia());
                        }
                        adapter.setNewInstance(LocalMediaModel.getLocalMediaModels(mSelected, LocalMediaModel.SELECT_ALL));
                    }

                    @Override
                    public void onCancel() {
                        if (mSelected.size() != 9) {
                            mSelected.add(new LocalMedia());
                        }
                    }
                }, new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        mSelectedVideo = result;
                        mSelected.clear();
                        mSelected.addAll(mSelectedImage);
                        mSelected.addAll(mSelectedVideo);
                        if (mSelected.size() < 9) {
                            mSelected.add(new LocalMedia());
                        }
                        adapter.setNewInstance(LocalMediaModel.getLocalMediaModels(mSelected, LocalMediaModel.SELECT_ALL));
                    }

                    @Override
                    public void onCancel() {
                        if (mSelected.size() != 9) {
                            mSelected.add(new LocalMedia());
                        }
                    }
                });
    }
    public void upload(){
        GlobalDialogManager.getInstance().show(activity.getSupportFragmentManager());
        if (mSelected.size() != 0 &&
                TextUtils.isEmpty(videoPaths) && TextUtils.isEmpty(imagePath)) {
            uploadImg();
        } else {
            uploadInfo();
        }
    }
    protected void uploadImg() {
        FileUploadUtils.FileUpload(FileUploadPathEnum.BBS.name(), new ResponseCallback<FileUploadResponse>() {
            @Override
            public void onSuccess(FileUploadResponse responsBody) {
                imagePath = StringUtils.List2String(responsBody.getData());
                videoPaths = StringUtils.ListGetVideo(responsBody.getData());
                uploadInfo();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        }, getImagePaths());
    }

    protected void uploadInfo(){

    }

    protected String[] getImagePaths() {
        if (mSelected.size()!=9){
            mSelected.remove(mSelected.size()-1);
        }
        String[] imagePaths = new String[mSelected.size()];
        for (int i = 0; i < mSelected.size(); i++) {
            imagePaths[i] = TextUtils.isEmpty(mSelected.get(i).getCompressPath())?
                    mSelected.get(i).getRealPath(): mSelected.get(i).getCompressPath();
        }
        return imagePaths;
    }

    public interface onGetDataCallback<T> {
        void onSuccess(T response);
    }
}
