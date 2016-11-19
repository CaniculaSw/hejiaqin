package com.chinamobile.hejiaqin.business.ui.more;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.business.ui.more.dialog.BindTVDialog;

/**
 * Created by eshaohu on 16/11/19.
 */
public class BindTVBoxFirstActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView headerView;
    private Button bindTVBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_tv_no_binded;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.more_bind_tv_header);
        bindTVBtn = (Button) findViewById(R.id.more_bind_tv_btn);
        headerView.title.setText(R.string.more_choose_contact);
        headerView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        headerView.backImageView.setOnClickListener(this);
        bindTVBtn.setOnClickListener(this);
    }

    @Override
    protected void initLogics() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                doBack();
                break;
            case R.id.more_bind_tv_btn:
                showDialog();
                break;
            default:
                break;
        }

    }

    private void showDialog() {
        //创建一个AlertDialog对象
        final BindTVDialog dialog = new BindTVDialog(this, R.style.CalendarDialog);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.FILL_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        dialog.show();
    }
}
