package com.chinamobile.hejiaqin.business.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.test.AndroidTestCase;
import android.text.TextUtils;

import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.R;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.customer.framework.component.storage.StorageMgr;
import com.customer.framework.utils.StringUtil;

import junit.framework.TestCase;

import java.io.File;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class CommonUtilsTest extends AndroidTestCase {

    public void testHasSdcard() throws Exception {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            assertTrue(CommonUtils.hasSdcard());
        } else {
            assertFalse(CommonUtils.hasSdcard());
        }

    }

    public void testIsNetworkAvailable() throws Exception {
        ConnectivityManager cm = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isNetworkAvailable = false;
        if (info != null) {
            isNetworkAvailable = info.isAvailable();
        }

        if (isNetworkAvailable) {
            assertTrue(CommonUtils.isNetworkAvailable(getContext()));
        } else {
            assertFalse(CommonUtils.isNetworkAvailable(getContext()));
        }
    }

    public void testIsWifi() throws Exception {
        ConnectivityManager cm = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isWifi = false;
        if (info != null) {
            isWifi = (info.getType() == ConnectivityManager.TYPE_WIFI);
        }

        if (isWifi) {
            assertTrue(CommonUtils.isWifi(getContext()));
        } else {
            assertFalse(CommonUtils.isWifi(getContext()));
        }
    }

    public void testIsMobile() throws Exception {
        ConnectivityManager cm = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean isMobile = false;
        if (info != null) {
            isMobile = (info.getType() == ConnectivityManager.TYPE_MOBILE);
        }

        if (isMobile) {
            assertTrue(CommonUtils.isMobile(getContext()));
        } else {
            assertFalse(CommonUtils.isMobile(getContext()));
        }
    }

    public void testSaveBitmap() throws Exception {
        File imageFile = new File(Environment.getDataDirectory(), "aaa.bitmap");
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.mipmap.ic_launcher);

        if (imageFile.exists()) {
            imageFile.delete();
        }

        CommonUtils.saveBitmap(imageFile.getAbsolutePath(), bitmap);
        assertTrue(imageFile.exists());
    }

    public void testGetAvailableInternalMemorySize() throws Exception {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long size = availableBlocks * blockSize;
        assertTrue(size == CommonUtils.getAvailableInternalMemorySize());
    }

    public void testGetLocalUserInfo() throws Exception {
        UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage()
                .getObject(BussinessConstants.Login.USER_INFO_KEY);
        assertTrue(userInfo == CommonUtils.getLocalUserInfo());
    }

    public void testGetTvNumber() throws Exception {
        String testName1 = "028123456";
        assertTrue("28123456".equals(CommonUtils.getTvNumber(testName1)));

        String testName2 = "28123456";
        assertTrue("28123456".equals(CommonUtils.getTvNumber(testName2)));

        String testName3 = "123456";
        assertTrue("123456".equals(CommonUtils.getTvNumber(testName3)));
    }

    public void testGetTvNumberWithZero() throws Exception {
        String testName1 = "028123456";
        assertTrue("028123456".equals(CommonUtils.getTvNumber(testName1)));

        String testName2 = "28123456";
        assertTrue("028123456".equals(CommonUtils.getTvNumber(testName2)));

        String testName3 = "123456";
        assertTrue("123456".equals(CommonUtils.getTvNumber(testName3)));
    }

    public void testGetCountryPhoneNumber() throws Exception {
        String testName1 = "0086123456";
        assertTrue("0086123456".equals(CommonUtils.getTvNumber(testName1)));

        String testName2 = "+86123456";
        assertTrue("+86123456".equals(CommonUtils.getTvNumber(testName2)));

        String testName3 = "123456";
        assertTrue("+86123456".equals(CommonUtils.getTvNumber(testName3)));
    }

    public void testGetPhoneNumber() throws Exception {
        String testName1 = "+86123456";
        assertTrue("123456".equals(CommonUtils.getTvNumber(testName1)));

        String testName2 = "0086123456";
        assertTrue("123456".equals(CommonUtils.getTvNumber(testName2)));

        String testName3 = "123456";
        assertTrue("123456".equals(CommonUtils.getTvNumber(testName3)));
    }

    public void testIsSamePhoneNumber() throws Exception {
        assertTrue(CommonUtils.isSamePhoneNumber("123456", "123456"));
        assertTrue(CommonUtils.isSamePhoneNumber("+86123456", "123456"));
        assertTrue(CommonUtils.isSamePhoneNumber("0086123456", "123456"));
        assertTrue(CommonUtils.isSamePhoneNumber("+86123456", "0086123456"));
    }

    public void testIsPhoneNumber() throws Exception {
        assertFalse(CommonUtils.isPhoneNumber(null));
        assertFalse(CommonUtils.isPhoneNumber(""));
        assertFalse(CommonUtils.isPhoneNumber("accde"));
        assertFalse(CommonUtils.isPhoneNumber("+as(i"));
        assertTrue(CommonUtils.isPhoneNumber("13912341234"));
        assertTrue(CommonUtils.isPhoneNumber("+8613912341234"));
        assertTrue(CommonUtils.isPhoneNumber("008613912341234"));
    }

    public void testIsAutoAnswer() throws Exception {

    }

    public void testFormatTvNum() throws Exception {
        assertTrue(CommonUtils.formatTvNum("").equals(""));
        assertTrue(CommonUtils.formatTvNum("123").equals("0123"));
        assertTrue(CommonUtils.formatTvNum("0123").equals("0123"));
    }

    public void testGetTvNum() throws Exception {
        assertTrue(CommonUtils.getTvNum("").equals(""));
        assertTrue(CommonUtils.getTvNum("123").equals("123"));
        assertTrue(CommonUtils.getTvNum("0123").equals("123"));
    }
}
