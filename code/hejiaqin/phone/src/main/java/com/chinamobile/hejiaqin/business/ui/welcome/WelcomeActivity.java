package com.chinamobile.hejiaqin.business.ui.welcome;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.login.LoginActivity;
import com.chinamobile.hejiaqin.business.ui.login.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * Created by huangzq on 2016/4/26.
 */
public class WelcomeActivity extends BasicActivity {

    private BGABanner mBGABanner;
    private int[] draws;
    private Button btn_login;
    private Button btn_enrol;
    private ClickListener mClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_enrol = (Button) findViewById(R.id.btn_enrol);
        mBGABanner = (BGABanner) findViewById(R.id.banner_splash_pager);
    }

    @Override
    protected void initDate() {
        draws = new int[]{R.mipmap.welcome1, R.mipmap.welcome2, R.mipmap.welcome3, R.mipmap.welcome4};
        setUpBGABanner();
    }

    @Override
    protected void initListener() {
        mClickListener = new ClickListener();
        btn_login.setOnClickListener(mClickListener);
        btn_enrol.setOnClickListener(mClickListener);
    }

    @Override
    protected void initLogics() {

    }

    /**
     *  设置轮播图片
     */
    private void setUpBGABanner() {
        try {
            //保存view集合
            List<View> views = new ArrayList<>();
            for (int i = 0; i < draws.length; i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.layout_view_image, null);
                ImageView iv_img = (ImageView) view.findViewById(R.id.iv_image);
                Picasso.with(this).load(draws[i]).into(iv_img);
                views.add(iv_img);
            }
            mBGABanner.setViews(views);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义监听
     */
    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login:
                    Intent intent1 = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.btn_enrol:
                    Intent intent2 = new Intent(WelcomeActivity.this, RegisterActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    }
}
