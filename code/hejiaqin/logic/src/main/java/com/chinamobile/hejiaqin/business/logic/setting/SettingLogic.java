package com.chinamobile.hejiaqin.business.logic.setting;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.net.IHttpCallBack;
import com.chinamobile.hejiaqin.business.net.setting.SettingHttpmanager;
import com.customer.framework.component.net.NetResponse;
import com.customer.framework.logic.LogicImp;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.SystemUtil;

/**
 * Created by eshaohu on 16/5/24.
 */
public class SettingLogic extends LogicImp implements ISettingLogic {
    private static final String TAG = "SettingLogic";
//    @Override
//    public Bitmap createQRImage(String url, int qrWidth, int qrHeight) {
//        try
//        {
//            //判断URL合法性
//            if (url == null || "".equals(url) || url.length() < 1)
//            {
//                return null;
//            }
//            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            //图像数据转换，使用了矩阵转换
//            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, qrWidth, qrHeight, hints);
//            int[] pixels = new int[qrWidth * qrHeight];
//            //下面这里按照二维码的算法，逐个生成二维码的图片，
//            //两个for循环是图片横列扫描的结果
//            for (int y = 0; y < qrHeight; y++)
//            {
//                for (int x = 0; x < qrWidth; x++)
//                {
//                    if (bitMatrix.get(x, y))
//                    {
//                        pixels[y * qrWidth + x] = 0xff000000;
//                    }
//                    else
//                    {
//                        pixels[y * qrWidth + x] = 0xffffffff;
//                    }
//                }
//            }
//            //生成二维码图片的格式，使用ARGB_8888
//            Bitmap bitmap = Bitmap.createBitmap(qrWidth, qrHeight, Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, qrWidth, 0, 0, qrWidth, qrHeight);
//           return  bitmap;
//        }
//        catch (WriterException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void checkVersion() {
        new SettingHttpmanager(getContext()).checkVersion(null, new IHttpCallBack() {
            @Override
            public void onSuccessful(Object invoker, Object obj) {
                SettingLogic.this.sendEmptyMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_SUCCESS_MSG_ID);
            }

            @Override
            public void onFailure(Object invoker, String code, String desc) {
                SettingLogic.this.sendMessage(BussinessConstants.LoginMsgID.UPDATE_PWD_FAIL_MSG_ID, desc);
            }

            @Override
            public void onNetWorkError(NetResponse.ResponseCode errorCode) {
                SettingLogic.this.sendMessage(BussinessConstants.CommonMsgId.NETWORK_ERROR_MSG_ID, errorCode);
            }
        });
    }

    private boolean isNewVersion(VersionInfo versionInfo){
        int currentVersioncode,versionCode;
        currentVersioncode = SystemUtil.getPackageInfo(getContext()).versionCode;
        try {
            versionCode = Integer.parseInt(versionInfo.getVersionCode());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }
        if (currentVersioncode < versionCode){
            return true;
        }
        return false;
    }
    private boolean isForceUpgrade(VersionInfo versionInfo) {
        if (versionInfo.getByForce() == 1) {
            return true;
        }
        int currentVersioncode, forceVersionCode;
        currentVersioncode = SystemUtil.getPackageInfo(getContext()).versionCode;
        try {
            forceVersionCode = Integer.parseInt(versionInfo.getForceVersionCode());
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }
        if (currentVersioncode < forceVersionCode){
            return true;
        }
        return false;
    }
    private void updateApp(VersionInfo versionInfo){

    }
}
