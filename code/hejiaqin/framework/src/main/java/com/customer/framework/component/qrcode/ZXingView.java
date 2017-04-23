package com.customer.framework.component.qrcode;

import android.content.Context;
import android.util.AttributeSet;

import com.customer.framework.component.qrcode.core.QRCodeView;
import com.customer.framework.utils.LogUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
/***/
public class ZXingView extends QRCodeView {
    private MultiFormatReader mMultiFormatReader;

    public ZXingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZXingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initMultiFormatReader();
    }

    private void initMultiFormatReader() {
        mMultiFormatReader = new MultiFormatReader();
        mMultiFormatReader.setHints(QRCodeDecoder.HINTS);
    }

    @Override
    public String processData(byte[] data, int width, int height) {
        String result = null;
        Result rawResult = null;

        try {
            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
            rawResult = mMultiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source)));
        } catch (Exception e) {
//            try {
//                PlanarYUVLuminanceSource source2 = new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
//                rawResult = mMultiFormatReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(source2)));
//            }catch (Exception e1){}
            LogUtil.i("ZxinView","Unable to decode the qr code");
        } finally {
            mMultiFormatReader.reset();
        }

        if (rawResult != null) {
            result = rawResult.getText();
        }
        return result;
    }
}