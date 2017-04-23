package com.chinamobile.hejiaqin.business.ui.more.manger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.model.more.VersionInfo;
import com.chinamobile.hejiaqin.business.ui.basic.MyActivityManager;
import com.chinamobile.hejiaqin.business.ui.basic.view.CustomDialog;
import com.chinamobile.hejiaqin.business.utils.DirUtil;
import com.customer.framework.utils.LogUtil;
import com.customer.framework.utils.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/***/
public class UpdateManger {

    private static final String TAG = "UpdateManger";

    private Context mContext;
    private Dialog downloadDialog;// 下载对话框
    private String saveFileName;
    private String downLoadUrl;

    // 进度条与通知UI刷新的handler和msg常量
    private ProgressBar mProgress;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static final int DOWN_ERROR = 3;
    private int progress;// 当前进度
    private Thread downLoadThread; // 下载线程
    private boolean interceptFlag = false;// 用户取消下载
    // 通知处理刷新界面的handler
    private Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    if (progress < 0) {
                        mProgress.setProgress(-progress);
                    } else {
                        mProgress.setProgress(progress);
                    }
                    break;
                case DOWN_OVER:
                    if (downloadDialog != null && downloadDialog.isShowing()) {
                        downloadDialog.dismiss();
                    }
                    installApk();
                    break;
                case DOWN_ERROR:
                    if (downloadDialog != null && downloadDialog.isShowing()) {
                        downloadDialog.dismiss();
                    }
                    Toast.makeText(UpdateManger.this.mContext,UpdateManger.this.mContext.getString(R.string.about_hejiaqin_download_error),Toast.LENGTH_SHORT);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public UpdateManger(Context context) {
        this.mContext = context;
    }
    /***/
    public void showNoticeDialog(final VersionInfo info) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
        builder.setCancelable(true);
//        builder.setTitle(R.string.about_version_update);
        builder.setMessage(R.string.about_version_new_to_update);
        builder.setPositiveButton(R.string.about_hejiaqin_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update(info,false);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.about_hejiaqin_later_to_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    /**强制更新下载窗口*/
    public void showForcedUpdateDialog(final VersionInfo info) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
//        builder.setTitle(R.string.about_version_update);
        builder.setMessage(R.string.about_hejiaqin_version_warming);
        builder.setPositiveButton(R.string.about_hejiaqin_update_now, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update(info,true);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.about_hejiaqin_progress_quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //关闭所有Activity
                MyActivityManager.getInstance().finishAllActivity(null);
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
    /***/
    public void update(VersionInfo info, boolean showDownloadDialog){
        downLoadUrl = BussinessConstants.ServerInfo.HTTP_ADDRESS + "/" + info.getUrl();
        saveFileName = DirUtil.getExternalFileDir(mContext.getApplicationContext())
                + BussinessConstants.Setting.APP_SAVE_PATH + mContext.getString(R.string.app_name) + ".apk";
        showDownloadDialog(showDownloadDialog);
    }

    protected void showDownloadDialog(final boolean isExit) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mContext);
//        builder.setTitle("软件版本更新");
//        builder.setMessage("正在下载中，请稍后...");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        TextView tvTitle = (TextView) v.findViewById(R.id.progress_title);
        tvTitle.setText(R.string.about_hejiaqin_download);
        tvTitle.setVisibility(View.VISIBLE);
        builder.setContentView(v);
//        builder.setView(v);// 设置对话框的内容为一个View
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
                if (isExit) {
                    MyActivityManager.getInstance().finishAllActivity(null);
                }
            }
        });
        downloadDialog = builder.create();
        downloadDialog.setCancelable(false);
        downloadDialog.show();
        downloadApk();
    }

    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    protected void installApk() {

        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");// File.toString()会返回路径信息
        mContext.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            URL url;
            FileOutputStream outStream = null;
            InputStream ins = null;
            HttpURLConnection conn = null;
            try {
                if (!StringUtil.isNullOrEmpty(downLoadUrl)) {
                    String apkUrl = downLoadUrl;
                    url = new URL(apkUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    if (length <= 0) {
                        length = 1024 * 1024 * 10;
                    }
                    ins = conn.getInputStream();
                    File file = new File(DirUtil.getExternalFileDir(mContext.getApplicationContext()) + BussinessConstants.Setting.APP_SAVE_PATH);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String apkFileName = saveFileName + ".tmp";
                    File apkTmpFile = new File(apkFileName);
                    File apkFile = new File(saveFileName);
                    outStream = new FileOutputStream(apkTmpFile);
                    int count = 0;
                    byte[] buf = new byte[1024];
                    do {
                        int numread = ins.read(buf);
                        count += numread;

                        progress = (int) (((float) count / length) * 100);
                        // 下载进度
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                        if (numread <= 0) {
                            // 下载完成通知安装
                            mHandler.sendEmptyMessage(DOWN_OVER);
                            if (apkFile.exists()) {
                                apkFile.delete();
                            }
                            apkTmpFile.renameTo(apkFile);
                            break;
                        }
                        outStream.write(buf, 0, numread);
                    } while (!interceptFlag);// 点击取消停止下载
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e);
                mHandler.sendEmptyMessage(DOWN_ERROR);
            } finally {
                if (outStream != null) {
                    try {
                        outStream.close();
                    } catch (Exception ex) {

                    }
                }
                if (ins != null) {
                    try {
                        ins.close();
                    } catch (Exception ex) {

                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }

            }
        }
    };

}