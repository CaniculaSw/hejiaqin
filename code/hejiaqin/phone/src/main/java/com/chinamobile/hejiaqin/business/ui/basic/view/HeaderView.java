package com.chinamobile.hejiaqin.business.ui.basic.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.hejiaqin.R;

/**
 * desc:
 * project:Kangxi
 * version 001
 * author: zhanggj
 * Created: 2016/4/18.
 */
public class HeaderView extends RelativeLayout
{
    public View headerView;
    public RelativeLayout headerLayout;
    public LinearLayout middleLayout;
    public ImageView rightBtn;
    public ImageView rightImageView;
    public ImageView backImageView;
    public LinearLayout setadd;
    public TextView title;
    public TextView tvRight;

    public HeaderView(Context paramContext)
    {
        super(paramContext);
        initView();
    }

    public HeaderView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        initView();
    }

    public HeaderView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
    }

    private void initView()
    {
        this.headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_view, this);
        this.headerLayout = ((RelativeLayout)this.headerView.findViewById(R.id.headerLayout));
        this.rightBtn = ((ImageView)this.headerView.findViewById(R.id.right_btn));
        this.middleLayout = ((LinearLayout)this.headerView.findViewById(R.id.middleLayout));
        this.title = ((TextView)this.headerView.findViewById(R.id.headertitle));
        this.setadd = ((LinearLayout)this.headerView.findViewById(R.id.setadd));
        this.tvRight = ((TextView)this.headerView.findViewById(R.id.tvRight));
        this.rightImageView = ((ImageView)this.headerView.findViewById(R.id.right_imageView));
        this.backImageView = (ImageView)this.headerView.findViewById(R.id.back_btn);
    }
}
