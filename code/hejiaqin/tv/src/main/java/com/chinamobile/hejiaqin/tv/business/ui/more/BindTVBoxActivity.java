package com.chinamobile.hejiaqin.tv.business.ui.more;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.tv.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.tv.business.ui.basic.view.HeaderView;
import com.chinamobile.hejiaqin.tv.business.ui.more.dialog.BindTVDialog;

/**
 * Created by eshaohu on 16/5/30.
 */
public class BindTVBoxActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView mHeaderView;
    private Button mBindTVBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_tv;
    }

    @Override
    protected void initView() {
        mHeaderView = (HeaderView) findViewById(R.id.more_bind_tv_header);
        mBindTVBtn = (Button) findViewById(R.id.more_bind_tv_btn);
        mHeaderView.title.setText(getString(R.string.more_bind_tv_btn_text));
        mHeaderView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        mHeaderView.backImageView.setClickable(true);
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        mBindTVBtn.setOnClickListener(this);
        mHeaderView.backImageView.setOnClickListener(this);
    }

    @Override
    protected void initLogics() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
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
