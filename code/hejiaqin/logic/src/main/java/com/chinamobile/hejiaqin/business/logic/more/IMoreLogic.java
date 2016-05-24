package com.chinamobile.hejiaqin.business.logic.more;

import android.graphics.Bitmap;

/**
 * Created by eshaohu on 16/5/24.
 */
public interface IMoreLogic {
    public Bitmap createQRImage(String url,int qrWidth, int qrHeight);
}
