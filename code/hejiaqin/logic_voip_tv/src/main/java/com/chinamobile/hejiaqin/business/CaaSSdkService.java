package com.chinamobile.hejiaqin.business;

import android.graphics.Rect;

import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.call.CallApi;

public class CaaSSdkService
{

    public static void setLocalRenderPos(Rect rectLocal, int layer)
    {
        if (rectLocal != null)
        {
            LogUtil.d(Const.TAG_CAAS,
                    "Enter setLocalRenderPos layer:" + layer + "rectLocal.width():" + rectLocal.width()
                            + "rectLocal.height()" + rectLocal.height() + "rectLocal.left" + rectLocal.left
                            + "rectLocal.top" + rectLocal.top);
            if (rectLocal.width() > 0 && rectLocal.height() > 0)
            {
                CallApi.setRegion(CallApi.VIDEO_TYPE_LOCAL, rectLocal.left, rectLocal.top, rectLocal.width(), rectLocal.height(), layer);
            }
        }
    }
        
    public static void setRemoteRenderPos(Rect rectRemote, int layer)
    {
        LogUtil.d(Const.TAG_CAAS, "Enter setRemoteRenderPos layer:" + layer);
        if (rectRemote != null && rectRemote.width() > 0 && rectRemote.height() > 0)
        {
            CallApi.setRegion(CallApi.VIDEO_TYPE_REMOTE, rectRemote.left, rectRemote.top, rectRemote.width(), rectRemote.height(), layer);
        }
    }
       
    public static void setVideoLevel(int video_level_id)
    {
        LogUtil.d(Const.TAG_CAAS, "Enter setVideoLevel: " + video_level_id);
      
        //在同一个profile level(如16，即VGA/4CIF)情况下分辨率可选情况下，设置优选4:3，与手机保持一致
        CallApi.setConfig(CallApi.CONFIG_MAJOR_TYPE_VIDEO_PREFER_SIZE, CallApi.CONFIG_MINOR_TYPE_DEFAULT, "2");
        
        switch (video_level_id)
        {
            case 0:
                CallApi.setVideoLevel(CallApi.VIDEO_LEVEL_720P_NOMAL);
                break;
            case 1:
                CallApi.setVideoLevel(CallApi.VIDEO_LEVEL_VGA_HIGH);
                break;
            case 2:
                CallApi.setVideoLevel(CallApi.VIDEO_LEVEL_VGA_NOMAL);
                break;
            default:
                LogUtil.e(Const.TAG_CAAS, "Invalid video level!");
                break;
        }
        
        LogUtil.d(Const.TAG_CAAS, "Leave setVideoLevel");
    }

      
    public static void showLocalVideoRender(boolean show)
    {
        CallApi.setVisible(CallApi.VIDEO_TYPE_LOCAL, show);
    }
    
    public static void showRemoteVideoRender(boolean show)
    {
       CallApi.setVisible(CallApi.VIDEO_TYPE_REMOTE, show);
    }
    
}
