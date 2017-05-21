package com.customer.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.test.AndroidTestCase;

import com.customer.framework.R;

import junit.framework.TestCase;

/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class ImageUtilTest extends AndroidTestCase {

    public void testZoom() throws Exception {
        Context context = getContext();
        assertNotNull(context);
        assertNotNull(ImageUtil.zoom(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher), 2));
    }

    public void testCrop() throws Exception {
        Context context = getContext();
        assertNotNull(context);
        assertNotNull(ImageUtil.crop(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher), 10, 80));
    }
}