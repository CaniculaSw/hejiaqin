package com.chinamobile.hejiaqin.business;

import android.graphics.Rect;

import com.customer.framework.utils.LogUtil;
import com.huawei.rcs.call.CallApi;

public class CaaSSdkService
{

    private static boolean m_localViewOpen = false;

    public static boolean closeLocalView()
    {
        LogUtil.d(Const.TAG_CAAS, "closeLocalView Enter");

        if (!m_localViewOpen)
        {
            LogUtil.d(Const.TAG_CAAS, "LocalView not opened");
            return true;
        }

        int iResult = CallApi.closeLocalView();
        m_localViewOpen = false;

        LogUtil.d(Const.TAG_CAAS, "Leave closeCamera result is " + iResult);

        return 0 == iResult;
    }

    public static boolean isCameraOpen()
    {
        return m_localViewOpen;
    }

    public static boolean openLocalView()
    {

        if (m_localViewOpen)
        {
            LogUtil.d(Const.TAG_CAAS, "openLocalView Camera already opened");
            return true;
        }

        m_localViewOpen = true;

        int iResult = -1;
        iResult =  CallApi.openLocalView();
        if (0 != iResult)
        {
            m_localViewOpen = false;
        }

        LogUtil.d(Const.TAG_CAAS, "openLocalView Leave openCamera result is " + iResult);
        return m_localViewOpen;
    }

    public static void setLocalCamaraStatus(boolean status)
    {
        LogUtil.d(Const.TAG_CAAS, "Enter setLocalCamaraStatus status:" + status + " old status:" + m_localViewOpen);
        m_localViewOpen = status;
    }


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


    public static void setVoiceDelay(int audioDelay)
    {
        if (audioDelay == Const.AUDIO_DELAY_INITIAL_VALUE)
        {
            CallApi.setVoiceDelay(0);
        }
        else
        {
            CallApi.setVoiceDelay(audioDelay);
        }

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
