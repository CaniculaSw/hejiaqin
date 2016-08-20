package com.chinamobile.hejiaqin.business.ui.basic;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.ui.basic.view.CustomDialog;
import com.customer.framework.utils.PermissionsChecker;


/**
 * desc:
 * version 001
 * author:
 * Created: 2016/6/13.
 */
public class PermissionsActivity extends Activity {


    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private boolean isRequireCheck; // 是否需要系统权限检测

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(BussinessConstants.CommonInfo.INTENT_EXTRA_PERMISSIONS)) {
            finish();
        }
        setContentView(R.layout.activity_permissions);
        isRequireCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            String[] permissions = getPermissions();
            if (PermissionsChecker.lacksPermissions(getApplicationContext(), permissions)) {
                requestPermissions(permissions); // 请求权限
            } else {
                allPermissionsGranted(); // 全部权限都已获取
            }
        } else {
            isRequireCheck = true;
        }
    }

    // 返回传递的权限参数
    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(BussinessConstants.CommonInfo.INTENT_EXTRA_PERMISSIONS);
    }

    // 请求权限兼容低版本
    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, BussinessConstants.ActivityRequestCode.PERMISSIONS_REQUEST_CODE);
    }

    // 全部权限均已获取
    private void allPermissionsGranted() {
        setResult(BussinessConstants.CommonInfo.PERMISSIONS_GRANTED);
        finish();
    }


    /**
     * 用户权限处理, * 如果全部获取, 则直接过. * 如果权限缺失, 则提示Dialog. * * @param requestCode 请求码 * @param permissions 权限 * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == BussinessConstants.ActivityRequestCode.PERMISSIONS_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
            allPermissionsGranted();
        } else {
            isRequireCheck = false;
            showMissingPermissionDialog();
        }
    }

// 含有全部的权限

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    private void showMissingPermissionDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.permission_help_title);
        builder.setMessage(R.string.permission_necessary_help_content);
        int negativeStr = R.string.permission_cancel;
        builder.setPositiveButton(R.string.permission_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setNegativeButton(negativeStr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(BussinessConstants.CommonInfo.PERMISSIONS_DENIED);
                finish();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }
}
