package com.sxjs.jd.composition.nationexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.antiless.support.widget.TabLayout;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.html.exammiddle.ExamMiddleFragment;
import com.sxjs.jd.composition.message.attention.AttentionFragment;
import com.sxjs.jd.composition.message.notification.NotificationFragment;
import com.sxjs.jd.composition.message.warn.WarnFragment;
import com.sxjs.jd.entities.NewsItemListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/messageActivity/messageActivity")
public class NationExamActivity extends BaseActivity implements NationExamContract.View {
    @Inject
    NationExamPresenter presenter;
    @BindView(R2.id.fake_status_bar)
    View                fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView            jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button              jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView            jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView            jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView            newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout      rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView            jkxTitleRight;
    @BindView(R2.id.tabLayout)
    TabLayout           tabLayout;
    @BindView(R2.id.jkx_viewpage)
    ViewPager           jkxViewpage;
    @BindView(R2.id.rl_no_data)
    RelativeLayout      rlNoData;


    private              String mXinGeToken;
    private static final String TAG = "NationExamActivity";
    private              Button mLoginEntry;

    List<Fragment> fragments;
    private NationExamAdapter adapter;
    private Intent               mIntent;
    private int                  mTz;
    private int                  mTx;
    private int                  mGz;
    private NewsItemListResponse newsItemListResponse;
    private List<String>         mTabTitle;
    private List<String>         mTabId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nation_exam);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);


        initTitle();
        initView();



    }

    private void initDataFragment(List<String> mTabTitle, List<String> mTabId) {

        fragments = new ArrayList<>();
        fragments.clear();

        for (int i = 0; i < mTabTitle.size(); i++) {
            ExamMiddleFragment examMiddleFragment = ExamMiddleFragment.newInstance(mTabId.get(i));
            fragments.add(examMiddleFragment);
        }

        adapter = new NationExamAdapter(getSupportFragmentManager(), fragments, mTabTitle);
        jkxViewpage.setAdapter(adapter);
        jkxViewpage.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(jkxViewpage);
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //左侧返回按钮
        jkxTitleLeft.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText("国考快讯");

        //消息
        //        jkxTitleRightBtn.setVisibility(View.VISIBLE);


    }

    private void initView() {


        DaggerNationExamActivityComponent.builder()
                .appComponent(getAppComponent())
                .nationExamPresenterModule(new NationExamPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        responseNewsItemDataMethod();

    }


    @Override
    protected void onResume() {
        super.onResume();


    }


    private void responseNewsItemDataMethod() {


        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("MOBILE", lAccount);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I005");
        mapHeaders.put("SESSION_ID", session_id);

        initData(mapHeaders, mapParameters);
    }

    public void initData(Map<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getRequestData(mapHeaders, mapParameters);
    }

    @Override
    public void setResponseData(NewsItemListResponse newsItemListResponse) {
        this.newsItemListResponse = newsItemListResponse;
        try {
            String code = newsItemListResponse.getCode();
            String msg = newsItemListResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + newsItemListResponse.getData());


                List<NewsItemListResponse.DataBean> data = newsItemListResponse.getData();


                mTabTitle = new ArrayList<>();
                mTabId = new ArrayList<>();
                if (data != null) {
                    if (data.size() > 0) {
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < data.size(); i++) {
                        String name = data.get(i).getNAME();
                        mTabTitle.add(name);
                        String id = data.get(i).getID();
                        mTabId.add(id);
                    }
                } else {
                    rlNoData.setVisibility(View.VISIBLE);
                }

                initDataFragment(mTabTitle, mTabId);

            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void showProgressDialogView() {
        showProgressDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hiddenProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
        }


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
