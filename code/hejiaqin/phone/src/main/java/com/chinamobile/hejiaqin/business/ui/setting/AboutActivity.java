package com.chinamobile.hejiaqin.business.ui.setting;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.setting.ISettingLogic;
import com.chinamobile.hejiaqin.business.model.setting.AppVersionInfo;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.CustomDialog;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.serviceAgreement.ServiceAgreementActivity;
import com.chinamobile.hejiaqin.business.utils.SysInfoUtil;
import com.customer.framework.utils.StringUtil;


/**
 * @param关于页面 Created by wubg on 2016/5/5.
 */
public class AboutActivity extends BasicActivity {

    private HeaderView headerView;
    private LinearLayout layout_about_kangxi;
    private LinearLayout layout_agree;
    private LinearLayout layout_version;
    private ISettingLogic settingLogic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_me;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.header_view);
        headerView.title.setText(R.string.about);
        headerView.backImageView.setImageResource(R.mipmap.back);
        layout_about_kangxi = (LinearLayout) findViewById(R.id.layout_about_kangxi);
        layout_agree = (LinearLayout) findViewById(R.id.layout_agree);
        layout_version = (LinearLayout) findViewById(R.id.layout_version);
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //回退
            }
        });
        layout_about_kangxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, AboutKanxiActivity.class));
            }
        });
        layout_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutActivity.this, ServiceAgreementActivity.class));
            }
        });
        layout_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitDailog();
                settingLogic.getLastVersion(null);
            }
        });
    }

    @Override
    protected void handleStateMessage(Message msg) {
        super.handleStateMessage(msg);
        dismissWaitDailog();
        switch (msg.what) {
            case BussinessConstants.SettingMsgID.GET_VERSION_SUCCESS_MSG_ID:
                if (msg.obj == null) {
                    return;
                }
                AppVersionInfo info = (AppVersionInfo) msg.obj;
                if (info.getVersionCode() > SysInfoUtil.getVersionCode(this.getApplication())) {
                    if (StringUtil.equals(info.getMustUpdate(), "1")) {
                        //强制更新
                        showForcedUpdateDialog(info);
                    } else {
                        //可选更新
                        showNoticeDialog(info);
                    }
                }
                break;
            case BussinessConstants.SettingMsgID.GET_VERSION_FAIL_MSG_ID:
                //TODO:停止等待的动画
                showToast(R.string.about_data_fail, Toast.LENGTH_SHORT, null);
                break;
            default:
                break;
        }
        dismissWaitDailog();
    }


    private void showNoticeDialog(final AppVersionInfo info) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle(R.string.about_version_update);
        builder.setMessage(R.string.about_version_new_to_update);
        builder.setPositiveButton(R.string.about_kanxi_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String path = info.getDownloadpath();
                try {
                    if (!StringUtil.isNullOrEmpty(path)) {
                        //打开浏览器跟新
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                        startActivity(intent);
                    } else {
                        showToast(R.string.about_kanxi_no_address_to_update, 1, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(R.string.about_kanxi_exception_update, 1, null);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.about_kanxi_later_to_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //打开浏览器跟新
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    //强制更新下载窗口
    private void showForcedUpdateDialog(final AppVersionInfo info) {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle(R.string.about_version_update);
        builder.setMessage(R.string.about_kanxi_version_warming);
        builder.setPositiveButton(R.string.about_kanxi_update_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String path = info.getDownloadpath();
                try {
                    if (!StringUtil.isNullOrEmpty(path)) {
                        //打开浏览器跟新
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
                        startActivity(intent);
                    } else {
                        showToast(R.string.about_kanxi_no_address_to_update, 1, null);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(R.string.about_kanxi_exception_update, 1, null);
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.about_kanxi_progress_quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //关闭所有Activity
                finishAllActivity(null);
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 初始化logic的方法，由子类实现<BR>
     * 在该方法里通过getLogicByInterfaceClass获取logic对象
     */
    @Override
    protected void initLogics() {
        settingLogic = (ISettingLogic) this.getLogicByInterfaceClass(ISettingLogic.class);
    }

}
