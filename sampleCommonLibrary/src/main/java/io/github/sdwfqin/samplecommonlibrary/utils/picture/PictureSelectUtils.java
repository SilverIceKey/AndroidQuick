package com.tckx.tckx_demo.common.utils.picture;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.tckx.tckx_demo.common.utils.view.QMUIBottomSheetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：图片选择封装
 * <p>
 * 开源库地址：https://github.com/zhihu/Matisse
 *
 * @author 张钦
 * @date 2018/7/17
 */
public class PictureSelectUtils {
    private Builder builder;

    public PictureSelectUtils(Builder builder) {
        this.builder = builder;
    }

    /**
     * PictureSelector拍照
     */
    public void photo(Fragment fragment, boolean isCrop, OnResultCallbackListener<LocalMedia> listener, Builder builder) {
        PictureSelector.create(fragment)
                .openCamera(builder.pictureMimeType)
                .imageEngine(new PictureSelectorGlideEngine())
                .isEnableCrop(isCrop)
                .compressSavePath(fragment.getContext().getExternalFilesDir("picture").getAbsolutePath())
                .circleDimmedLayer(true)
                .isAndroidQTransform(false)
                .selectionData(builder.selectedImage)
                .withAspectRatio(builder.cropWidth, builder.cropHeight)
                .isCompress(true)
                .forResult(listener);
    }

    /**
     * PictureSelector拍照
     */
    public void photo(Activity activity, boolean isCrop, OnResultCallbackListener<LocalMedia> listener, Builder builder) {
        PictureSelector.create(activity)
                .openCamera(builder.pictureMimeType)
                .imageEngine(new PictureSelectorGlideEngine())
                .isEnableCrop(isCrop)
                .circleDimmedLayer(true)
                .isAndroidQTransform(false)
                .compressSavePath(activity.getExternalFilesDir("picture").getAbsolutePath())
                .selectionData(builder.selectedImage)
                .withAspectRatio(builder.cropWidth, builder.cropHeight)
                .isCompress(true)
                .forResult(listener);
    }

    /**
     * PictureSelector选择图片
     */
    public void selectPhoto(Fragment fragment, boolean isSignle, boolean isCrop, OnResultCallbackListener<LocalMedia> listener, Builder builder) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .isEnableCrop(isCrop)
                .circleDimmedLayer(true)
                .withAspectRatio(builder.cropWidth, builder.cropHeight)
                .isCompress(true)
                .isAndroidQTransform(false)
                .compressSavePath(fragment.getContext().getExternalFilesDir("picture").getAbsolutePath())
                .selectionData(builder.selectedImage)
                .maxSelectNum(builder.maxLocalMedia-builder.selectedVideo.size())
                .imageEngine(new PictureSelectorGlideEngine())
                .selectionMode(isSignle ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)
                .forResult(listener);
    }

    public void selectPhoto(Activity activity, boolean isSignle, boolean isCrop, OnResultCallbackListener<LocalMedia> listener, Builder builder) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .isEnableCrop(isCrop)
                .withAspectRatio(builder.cropWidth, builder.cropHeight)
                .circleDimmedLayer(true)
                .isCompress(true)
                .isAndroidQTransform(false)
                .compressSavePath(activity.getExternalFilesDir("picture").getAbsolutePath())
                .selectionData(builder.selectedImage)
                .maxSelectNum(builder.maxLocalMedia-builder.selectedVideo.size())
                .imageEngine(new PictureSelectorGlideEngine())
                .selectionMode(isSignle ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)
                .forResult(listener);
    }

    /**
     * PictureSelector选择视频
     */
    public void selectVideo(Fragment fragment, boolean isSignle, boolean isCrop, OnResultCallbackListener<LocalMedia> listener, Builder builder) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofVideo())
                .isEnableCrop(isCrop)
                .circleDimmedLayer(true)
                .withAspectRatio(builder.cropWidth, builder.cropHeight)
                .isCompress(true)
                .isAndroidQTransform(false)
                .compressSavePath(fragment.getContext().getExternalFilesDir("picture").getAbsolutePath())
                .selectionData(builder.selectedVideo)
                .maxSelectNum(builder.maxLocalMedia-builder.selectedImage.size())
                .imageEngine(new PictureSelectorGlideEngine())
                .selectionMode(isSignle ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)
                .forResult(listener);
    }

    public void selectVideo(Activity activity, boolean isSignle, boolean isCrop, OnResultCallbackListener<LocalMedia> listener, Builder builder) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofVideo())
                .isEnableCrop(isCrop)
                .withAspectRatio(builder.cropWidth, builder.cropHeight)
                .circleDimmedLayer(true)
                .isCompress(true)
                .isAndroidQTransform(false)
                .compressSavePath(activity.getExternalFilesDir("picture").getAbsolutePath())
                .selectionData(builder.selectedVideo)
                .maxSelectNum(builder.maxLocalMedia-builder.selectedImage.size())
                .imageEngine(new PictureSelectorGlideEngine())
                .selectionMode(isSignle ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)
                .forResult(listener);
    }

    public void pictureSelect(Activity activity, OnResultCallbackListener<LocalMedia> fromPhoto, OnResultCallbackListener<LocalMedia> getImage, OnResultCallbackListener<LocalMedia> getVideo) {
        new QMUIBottomSheetUtil().showBottomSheet(new String[]{"拍照", "从相册中选择图片", "从相册中选择视频"}, new QMUIBottomSheetUtil.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        photo(activity, builder.isCrop, fromPhoto, builder);
                        break;
                    case 1:
                        selectPhoto(activity, builder.isSignle, builder.isCrop, getImage, builder);
                        break;
                    case 2:
                        selectVideo(activity, builder.isSignle, builder.isCrop, getVideo, builder);
                        break;
                }
            }
        });
    }

    public void pictureSelect(Fragment fragment, OnResultCallbackListener<LocalMedia> fromPhoto, OnResultCallbackListener<LocalMedia> getImage, OnResultCallbackListener<LocalMedia> getVideo) {
        new QMUIBottomSheetUtil().showBottomSheet(new String[]{"拍照", "从相册中选择图片", "从相册中选择视频"}, new QMUIBottomSheetUtil.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        photo(fragment, builder.isSignle, fromPhoto, builder);
                        break;
                    case 1:
                        selectPhoto(fragment, builder.isSignle, builder.isCrop, getImage, builder);
                        break;
                    case 2:
                        selectVideo(fragment, builder.isSignle, builder.isCrop, getVideo, builder);
                        break;
                }
            }
        });
    }

    public void pictureSelect(Activity activity, OnResultCallbackListener<LocalMedia> fromPhoto, OnResultCallbackListener<LocalMedia> getImage) {
        new QMUIBottomSheetUtil().showBottomSheet(new String[]{"拍照", "从相册中选择图片"}, new QMUIBottomSheetUtil.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        photo(activity, builder.isCrop, fromPhoto, builder);
                        break;
                    case 1:
                        selectPhoto(activity, builder.isSignle, builder.isCrop, getImage, builder);
                        break;
                }
            }
        });
    }

    public void pictureSelect(Fragment fragment, OnResultCallbackListener<LocalMedia> fromPhoto, OnResultCallbackListener<LocalMedia> getImage) {
        new QMUIBottomSheetUtil().showBottomSheet(new String[]{"拍照", "从相册中选择图片"}, new QMUIBottomSheetUtil.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        photo(fragment, builder.isSignle, fromPhoto, builder);
                        break;
                    case 1:
                        selectPhoto(fragment, builder.isSignle, builder.isCrop, getImage, builder);
                        break;
                }
            }
        });
    }

    public static class Builder {
        private boolean isSignle = true;
        private boolean isCrop = false;
        private int maxLocalMedia = 9;
        private int cropWidth = 1;
        private int cropHeight = 1;
        private int pictureMimeType = PictureMimeType.ofImage();
        private List<LocalMedia> selectedImage = new ArrayList<>();
        private List<LocalMedia> selectedVideo = new ArrayList<>();

        public Builder setSignle(boolean signle) {
            isSignle = signle;
            return this;
        }

        public Builder setCrop(boolean crop) {
            isCrop = crop;
            return this;
        }

        public Builder setMaxLocalMedia(int maxLocalMedia) {
            this.maxLocalMedia = maxLocalMedia;
            return this;
        }

        public Builder setPictureMimeType(int pictureMimeType) {
            this.pictureMimeType = pictureMimeType;
            return this;
        }

        public Builder setSelectedImage(List<LocalMedia> selectedImage) {
            this.selectedImage = selectedImage;
            return this;
        }
        public Builder setSelectedVideo(List<LocalMedia> selectedVideo) {
            this.selectedVideo = selectedVideo;
            return this;
        }

        public Builder setCropWidth(int cropWidth) {
            this.cropWidth = cropWidth;
            return this;
        }

        public Builder setCropHeight(int cropHeight) {
            this.cropHeight = cropHeight;
            return this;
        }

        public PictureSelectUtils build() {
            return new PictureSelectUtils(this);
        }
    }
}
