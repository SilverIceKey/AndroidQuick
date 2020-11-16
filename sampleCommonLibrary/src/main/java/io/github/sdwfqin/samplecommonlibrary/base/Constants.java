package com.tckx.tckx_demo.common.base;

import com.blankj.utilcode.util.PathUtils;

/**
 * 描述：全局配置
 *
 * @author 张钦
 * @date 2017/9/25
 */
public class Constants {
    static {
        System.loadLibrary("constantsNative");
    }

    public static native String BASE_URL();
    public static native String IMAGELOADURL();
    public static native String VIDEOLOADURL();
    public static String DEFAULT_AVATOR = "upload/icon/default.png";

    /**
     * 支付宝
     */
    public static native String A_LI_PAY_ID();
    public static native String A_LI_PAY_URL();
    public static native String A_LI_PAY_RES();

    /**
     * 微信
     */
    public static native String WECHAT_ID();

    /**
     * ========================================
     * ********        sp文件key        ********
     * ========================================
     */

    public final static String IS_FIRST_CHECK_LOCAL = "IS_FIRST_CHECK_LOCAL";
    public final static String IS_LOGIN = "IS_LOGIN";
    public final static String IS_IN_LOGIN = "IS_IN_LOGIN";
    public final static String TOKEN = "TOKEN";
    public final static String LAT = "LAT";
    public final static String LON = "LON";

    /**
     * =======================================
     * ********      EventBus标识      ********
     * =======================================
     */

    /**
     * =======================================
     * ********        其他配置        ********
     * =======================================
     */
    /**
     * 图片保存位置
     */
    public static final String SAVE_REAL_PATH = PathUtils.getExternalStoragePath() + "/tckx";

    /**
     * 文件共享
     */
    public static final String FILE_PROVIDER = "com.tckx.tckx_demo.fileprovider";

    /**
     * 上传文件表单
     */
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";


    /**
     * =======================================
     * ********        REQUESTCODE        ********
     * =======================================
     */
    public final static int USER_SHOP_CONFIRM_ORDER_SELECT_ADDRESS = 0x0000001;
    public final static int USER_SHOP_CONFIRM_ORDER_SELECT_COUPONS = 0x0000002;
}
