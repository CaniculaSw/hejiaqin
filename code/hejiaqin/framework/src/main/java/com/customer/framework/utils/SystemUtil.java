package com.customer.framework.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
/***/
public final class SystemUtil {
	private static final String TAG = "SystemUtil";
	private static final String RESP_CACHE_CLASS_NAME = "android.net.http.HttpResponseCache";

	private SystemUtil() {
	}

	public static String getStackTraceString(Throwable tr) {
		if (tr == null) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

	public static String getApkPackageName(Context context, String path) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(path,
				PackageManager.GET_ACTIVITIES);
		String packageName = null;
		if (null != info) {
			packageName = info.applicationInfo.packageName;
		}
		return packageName;
	}

	public static String getPseudoUIdOfPhone(Context context) {
		StringBuffer sb = new StringBuffer();
		sb.append("35");
		sb.append(Build.BOARD.length() % 10);
		sb.append(Build.BRAND.length() % 10);
		sb.append(Build.CPU_ABI.length() % 10);
		sb.append(Build.DEVICE.length() % 10);
		sb.append(Build.DISPLAY.length() % 10);
		sb.append(Build.HOST.length() % 10);
		sb.append(Build.ID.length() % 10);
		sb.append(Build.MANUFACTURER.length() % 10);
		sb.append(Build.MODEL.length() % 10);
		sb.append(Build.PRODUCT.length() % 10);
		sb.append(Build.TAGS.length() % 10);
		sb.append(Build.TYPE.length() % 10);
		sb.append(Build.USER.length() % 10);
		return sb.toString();
	}

	public static String getAndroidId(Context context) {
		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	public static boolean isApkInstalled(Context context, String appPackageName) {
		PackageInfo packageInfo;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(
					appPackageName, 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "pack name not found: " + appPackageName);
			return false;
		}
		return packageInfo != null;
	}

	public static String getPackageVersionName(Context context) {
		PackageInfo packageInfo = getPackageInfo(context);
		if (packageInfo != null) {
			return packageInfo.versionName;
		}
		return "1.0.0";
	}

	public static PackageInfo getPackageInfo(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getVersionInfo failed ", e);
		}
		return packageInfo;
	}
	/***/
	public static String getuserAgent(Context context) {
		WebView webview;
		webview = new WebView(context);
		webview.layout(0, 0, 0, 0);
		WebSettings settings = webview.getSettings();
		return settings.getUserAgentString();
	}
	/***/
	public static void vibrate(Context context) {
		Vibrator vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
		if (vibrator == null) {
			return;
		}
		long[] pattern = { 0, 1000 };
		vibrator.vibrate(pattern, -1);
	}
	/***/
	public static boolean openApk(Context context, String packageName) {
		try {
			PackageManager packageManager = context.getPackageManager();
			Intent intent = packageManager
					.getLaunchIntentForPackage(packageName);
			context.startActivity(intent);
			return true;
		} catch (Exception e) {
			Log.e(TAG,
					"open apk failed, the apk package is: " + packageName, e);
		}
		return false;
	}
	/***/
	public static void goHome(Context ctx) {
		Intent home = new Intent(Intent.ACTION_MAIN);
		home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		home.addCategory(Intent.CATEGORY_HOME);
		ctx.startActivity(home);
	}
	/***/
	public static void unInstallAPK(Context context, String packageName) {
		try {
			Uri packageURI = Uri.parse("package:" + packageName);
			Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		} catch (Exception e) {
			Log.e(TAG, "unInstallApk failed, apk package is : "
					+ packageName, e);
		}
	}

	public static int getAndroidSDKVersion() {
		return Build.VERSION.SDK_INT;
	}
	/***/
	public static void enableHttpResponseCache(Context context, long cacheSize) {
		try {
			File httpCacheDir = new File(context.getCacheDir(), "http");
			Class.forName(RESP_CACHE_CLASS_NAME)
					.getMethod("install", File.class, long.class)
					.invoke(null, httpCacheDir, cacheSize);
			Log.i(TAG, "enableHttpResponseCache:has cache");
		} catch (Exception httpResponseCacheNotAvailable) {
			Log.i(TAG, "enableHttpResponseCache:no cache");
		}
	}
	/***/
	public static void flushRespCache() {
		try {
			Object respCache = Class.forName(RESP_CACHE_CLASS_NAME)
					.getMethod("getInstalled").invoke(null);
			if (respCache == null) {
				return;
			}
			Class.forName(RESP_CACHE_CLASS_NAME).getMethod("flush")
					.invoke(respCache);
		} catch (Exception e) {
			Log.i(TAG, "flushRespCache:nocache");
		}
	}
	/***/
	public static void printCachehitCount() {
		try {
			Object respCache = Class.forName(RESP_CACHE_CLASS_NAME)
					.getMethod("getInstalled").invoke(null);
			if (respCache == null) {
				return;
			}
			Object returnValue = Class.forName(RESP_CACHE_CLASS_NAME)
					.getMethod("getHitCount").invoke(respCache);
			Log.i(
					TAG,
					"printRespCacheHitCount:"
							+ ((Integer) returnValue).intValue());
		} catch (Exception e) {
			Log.i(TAG, "printRespCacheHitCount:nocache");
		}
	}
}
