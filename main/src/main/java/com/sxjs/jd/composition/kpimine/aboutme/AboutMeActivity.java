package com.sxjs.jd.composition.kpimine.aboutme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.constant.Constant;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutMeActivity extends BaseActivity {

    @BindView(R2.id.fake_status_bar)
    View           fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView       jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button         jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView       jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView       jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView       newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView       jkxTitleRight;
    private Intent mIntent;
    private String mStringExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder =ButterKnife.bind(this);

        mIntent = getIntent();
        mStringExtra = mIntent.getStringExtra(Constant.CONTENT_TITLE);
        initTitle();
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //返回按钮
        jkxTitleLeft.setVisibility(View.VISIBLE);
        //标题
        jkxTitleCenter.setText(mStringExtra);
    }

    @OnClick({R2.id.jkx_title_left, R2.id.jkx_title_left_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {
            finish();
        } else if (i == R.id.jkx_title_left_btn) {
        }
    }
}
