package com.chinamobile.hejiaqin.business.ui.setting;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;

/**
 * Created by wubg on 2016/5/9.
 */
public class KanxiIntroduceActivity extends BasicActivity {

    private HeaderView headerView;
    private WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kanxi_introduce;
    }

    @Override
    protected void initDate() {

    }

    @Override
    public void initView() {
        headerView = (HeaderView)findViewById(R.id.header_view);
        headerView.title.setText(R.string.about_kanxi);
        headerView.backImageView.setImageResource(R.mipmap.back);
        String url = getIntent().getStringExtra("url");
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initListener() {
        headerView.backLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();   //回退
            }
        });
    }

    @Override
    protected void initLogics() {

    }


}
