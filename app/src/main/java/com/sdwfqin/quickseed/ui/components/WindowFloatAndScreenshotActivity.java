package com.sdwfqin.quickseed.ui.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.SystemClock;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.permission.PermissionUtils;
import com.sdwfqin.imageloader.util.DisplayUtil;
import com.sdwfqin.quickseed.R;
import com.sdwfqin.quickseed.constants.ArouterConstants;
import com.sdwfqin.quickseed.databinding.ActivityWindowFloatAndScreenshotBinding;

import java.nio.ByteBuffer;

import github.nisrulz.screenshott.ScreenShott;
import io.github.sdwfqin.samplecommonlibrary.base.SampleBaseActivity;

/**
 * 悬浮窗与截图Demo
 * <p>
 *
 * @author 张钦
 * @date 2020/4/10
 */
@Route(path = ArouterConstants.COMPONENTS_WINDOWFLOATANDSCREENSHOT)
public class WindowFloatAndScreenshotActivity extends SampleBaseActivity<ActivityWindowFloatAndScreenshotBinding> {

    /**
     * 截图权限
     */
    public static final int REQUEST_MEDIA_PROJECTION = 18;
    /**
     * 悬浮窗
     */
    public static final int REQUEST_ALERT = 19;

    private MediaProjectionManager mMediaProjectionManager;

    @Override
    protected ActivityWindowFloatAndScreenshotBinding getViewBinding() {
        return ActivityWindowFloatAndScreenshotBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initEventAndData() {
        mTopBar.setTitle("悬浮窗与截图");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
    }

    @Override
    protected void initClickListener() {
        mBinding.btnScreenshot.setOnClickListener(v -> {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!Settings.canDrawOverlays(getApplicationContext())) {
//                    //启动Activity让用户授权
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                    intent.setData(Uri.parse("package:" + getPackageName()));
//                    startActivityForResult(intent, REQUEST_ALERT);
//                } else {
//                    requestCapturePermission();
//                }
//            } else {
//                requestCapturePermission();
//            }
            if (PermissionUtils.checkPermission(this)) {
                showFloat();
            } else {
                PermissionUtils.requestPermission(this, new OnPermissionResult() {
                    @Override
                    public void permissionResult(boolean b) {
                        showFloat();
                    }
                });
            }

        });
    }

    private void requestCapturePermission() {
        //获取截屏的管理器
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }

    private void showFloat() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            ToastUtils.showShort("当前功能尚未适配Android 10，后续空闲会修改！");
//            return;
//        }
        EasyFloat.with(this)
                .setLayout(R.layout.layout_float_quick, view -> {
                    ImageView ivImg = view.findViewById(R.id.iv_img);
                    view.findViewById(R.id.btn_close).setOnClickListener(clickView -> {
                        EasyFloat.dismiss();
                    });
                    view.findViewById(R.id.btn_screenshot).setOnClickListener(clickView -> {
                        EasyFloat.hide();
                        ivImg.setImageBitmap(ScreenShott.getInstance().takeScreenShotOfRootView(getWindow().getDecorView().getRootView()));
                        EasyFloat.show();
                    });
                })
                .setMatchParent(true, true)
                .setDragEnable(true)
                .show();
    }

    private Bitmap getImage(ImageReader mImageReader) {
        Image image = null;
        image = mImageReader.acquireLatestImage();
        while (image == null) {
            SystemClock.sleep(10);
            image = mImageReader.acquireLatestImage();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
//每个像素的间距
        int pixelStride = planes[0].getPixelStride();
//总的间距
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        image.close();
        return bitmap;
    }
}
