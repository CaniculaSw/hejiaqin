package com.chinamobile.hejiaqin.business.ui.basic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chinamobile.hejiaqin.business.logic.contacts.IContactsLogic;
import com.chinamobile.hejiaqin.business.logic.voip.IVoipLogic;
import com.chinamobile.hejiaqin.tv.R;

/**
 * Created by  on 2016/6/25.
 */
public class VideoChooseDialog extends Dialog {

    public static final String TAG = VideoChooseDialog.class.getSimpleName();

    public String mCalleeNumber;
    private IVoipLogic mVoipLogic;
    private IContactsLogic mContactsLogic;
    private Context mContext;

    public VideoChooseDialog(Context context, int theme, String calleeNumber, IVoipLogic voipLogic,
                             IContactsLogic contactsLogic) {
        super(context, theme);
        this.mContext = context;
        this.mCalleeNumber = calleeNumber;
        this.mVoipLogic = voipLogic;
        this.mContactsLogic = contactsLogic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow_video_choose);
        findViewById(R.id.video_choose_app_layout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                VideoChooseDialog.this.dismiss();
                VideoOutDialog.show(getContext(), mCalleeNumber, mVoipLogic, mContactsLogic, true);
            }
        });
        findViewById(R.id.video_choose_volte_layout).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                VideoChooseDialog.this.dismiss();
                VideoOutDialog.show(getContext(), mCalleeNumber, mVoipLogic, mContactsLogic, false);
            }
        });
        findViewById(R.id.video_choose_cancel_layout).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        VideoChooseDialog.this.dismiss();
                    }
                });
    }

    /***/
    public static void show(Context context, String calleeNumber, IVoipLogic voipLogic,
                            IContactsLogic contactsLogic) {
        VideoChooseDialog videoChooseDialog = new VideoChooseDialog(context,
                R.style.CalendarDialog, calleeNumber, voipLogic, contactsLogic);
        Window window = videoChooseDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        videoChooseDialog.setCancelable(false);
        videoChooseDialog.show();
    }

}
