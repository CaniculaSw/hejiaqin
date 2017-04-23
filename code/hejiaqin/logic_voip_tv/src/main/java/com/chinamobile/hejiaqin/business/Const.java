package com.chinamobile.hejiaqin.business;
/***/
public class Const {
	public final static String TAG_CAAS = "TAG_CAAS";
	public final static int AUDIO_DELAY_INITIAL_VALUE = -1;
	public static String TAG_CALL = "TAG_CALL";
	public static String TAG_UI = "TAG_UI";
    public static final String CAMERA_PLUG = "android.intent.action.CAMERA_PLUG";
	public static final int TYPE_3719C = 0;
	public static final int TYPE_3719M = 1;
	public static final int TYPE_3798M = 2;
	public static final int TYPE_OTHER = 3;

	public static int getDeviceType() {
		return deviceType;
	}

	public static void setDeviceType(int deviceType) {
		Const.deviceType = deviceType;
	}

	private static int deviceType = TYPE_OTHER;

	/** 热插拔广播*/
	public static final String ACTION_USB_CAMERA_PLUG_IN_OUT = "android.hardware.usb.action.USB_CAMERA_PLUG_IN_OUT";
	/** A40获取热插拔状态*/
	public static final String USB_CAMERA_STATE = "UsbCameraState";
}
