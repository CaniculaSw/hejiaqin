package com.chinamobile.hejiaqin.business.ui.more;

import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;
import com.chinamobile.hejiaqin.business.BussinessConstants;
import com.chinamobile.hejiaqin.business.logic.login.ILoginLogic;
import com.chinamobile.hejiaqin.business.model.login.UserInfo;
import com.chinamobile.hejiaqin.business.model.login.req.FeedBackReq;
import com.chinamobile.hejiaqin.business.ui.basic.BasicActivity;
import com.chinamobile.hejiaqin.business.ui.basic.view.HeaderView;
import com.customer.framework.component.storage.StorageMgr;

/**
 * Created by eshaohu on 16/6/26.
 */
public class FeedBackActivity extends BasicActivity implements View.OnClickListener {
    private HeaderView headerView;
    private EditText feedBackContentEt;
    private TextView wordCountTv;
    private Button submitBtn;

    private ILoginLogic loginLogic;
    private static final int WORDLIMITED = 200;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        headerView = (HeaderView) findViewById(R.id.feedback_header);
        headerView.title.setText(R.string.use_help_feed_back);
        headerView.backImageView.setImageResource(R.mipmap.title_icon_back_nor);
        feedBackContentEt = (EditText) findViewById(R.id.feeback_content);
        feedBackContentEt.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        feedBackContentEt.setSingleLine(false);
        feedBackContentEt.setHorizontallyScrolling(false);
        feedBackContentEt.setGravity(Gravity.TOP);
        wordCountTv = (TextView) findViewById(R.id.feedback_word_count);
        submitBtn = (Button) findViewById(R.id.feedback_submit_btn);
        wordCountTv.setText(getString(R.string.feed_back_word_count, 0));
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initListener() {
        headerView.backImageView.setClickable(true);
        headerView.backImageView.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        feedBackContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = feedBackContentEt.getText();
                int len = editable.length();

                if (len > WORDLIMITED) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    String newStr = str.substring(0, WORDLIMITED);
                    feedBackContentEt.setText(newStr);
                    editable = feedBackContentEt.getText();

                    int newLen = editable.length();
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    Selection.setSelection(editable, selEndIndex);
                }

                wordCountTv.setText(getString(R.string.feed_back_word_count, editable.length()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initLogics() {
        loginLogic = (ILoginLogic) getLogicByInterfaceClass(ILoginLogic.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                doBack();
                break;
            case R.id.feedback_submit_btn:
                feedback();
            default:
                break;
        }
    }

    private void feedback() {
        if (feedBackContentEt.getText().length() > 0) {
            FeedBackReq reqBody = new FeedBackReq();
            UserInfo userInfo = (UserInfo) StorageMgr.getInstance().getMemStorage().getObject(BussinessConstants.Login.USER_INFO_KEY);
            reqBody.setToken(userInfo.getToken());
            reqBody.setContent(feedBackContentEt.getText().toString());
            loginLogic.feedBack(reqBody);
        }
    }
}
