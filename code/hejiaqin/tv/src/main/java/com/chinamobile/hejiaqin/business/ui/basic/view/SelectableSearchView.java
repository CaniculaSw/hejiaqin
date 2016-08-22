package com.chinamobile.hejiaqin.business.ui.basic.view;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chinamobile.hejiaqin.tv.R;
import com.chinamobile.hejiaqin.business.model.contacts.ContactsInfo;
import com.chinamobile.hejiaqin.business.ui.setting.adapter.SelectableSearchContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eshaohu on 16/6/9.
 */
public class SelectableSearchView extends RelativeLayout {
    public View searchView;
    private SelectableSearchContactAdapter adapter;
    private EditText searchInput;
    private View searchDelete;
    private ListView contactsListView;
    private View maskView;
    private ISearchListener listener;
    private Handler handler;

    public SelectableSearchView(Context context) {
        super(context);
        initView();
    }

    public SelectableSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SelectableSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        this.searchView = LayoutInflater.from(getContext()).inflate(R.layout.layout_contact_search, this);

        // 设置搜索显示的文字
        searchInput = (EditText) findViewById(R.id.search_input);
        searchDelete = findViewById(R.id.search_del);
        searchDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput.setText("");
            }
        });
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean hasInput = s.length() > 0;
                searchDelete.setVisibility(hasInput ? VISIBLE : INVISIBLE);
                if (hasInput) {
                    startSearch(s.toString().trim());
                } else {
                    setData(null);
                }
            }
        });

        contactsListView = (ListView) findViewById(R.id.list);
        //adapter = new SelectableSearchContactAdapter(getContext(), handler);
        //contactsListView.setAdapter(adapter);

        maskView = findViewById(R.id.mask_view);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();
                cancelSearch();
            }
        });
        //setData(null);
    }

    private void startSearch(String input) {
        if (null == listener) {
            return;
        }
        listener.search(input);
    }

    private void cancelSearch() {
        if (null == listener) {
            return;
        }
        listener.cancel();
    }

    public void setListener(ISearchListener searchListener) {
        listener = searchListener;
    }

    public void setAdapter(SelectableSearchContactAdapter adapter) {
        this.adapter = adapter;
        contactsListView.setAdapter(this.adapter);
    }

    public void setData(List<ContactsInfo> contactsInfoList) {
        List<ContactsInfo> tmpContactsInfoList = new ArrayList<>();
        if (null != contactsInfoList) {
            tmpContactsInfoList.addAll(contactsInfoList);
        }
        adapter.setData(tmpContactsInfoList);
        maskView.setVisibility(adapter.isEmpty() ? VISIBLE : GONE);
        contactsListView.setVisibility(adapter.isEmpty() ? GONE : VISIBLE);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            searchInput.requestFocus();
            showSoftInput();
        } else {
            hideSoftInput();
        }
    }

    public void setHint(String hint) {
        searchInput.setHint(hint);
    }

    public interface ISearchListener {
        void search(String input);

        void cancel();
    }

    private void showSoftInput() {

        InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchInput, InputMethodManager.SHOW_FORCED);
    }

    private void hideSoftInput() {

        InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
    }
}
