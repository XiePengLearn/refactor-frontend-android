package com.sxjs.jd.composition.main.before;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.util.CustomViewPager;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.ForgetPasswordResponse;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description:
 */
public class BeforePageFragment extends BaseFragment implements BeforePageContract.View, PtrHandler {


    @Inject
    BeforePagePresenter mPresenter;
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
    @BindView(R2.id.img)
    ImageView           img;
    @BindView(R2.id.tip)
    TextView            tip;
    @BindView(R2.id.empty_tip)
    RelativeLayout      emptyTip;
    @BindView(R2.id.tabs)
    TabLayout           tabs;
    @BindView(R2.id.jkx_viewpage)
    CustomViewPager     jkxViewpage;
    @BindView(R2.id.normal_display)
    LinearLayout        normalDisplay;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView        findPullRefreshHeader;

    private Handler mHandler;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_before_page, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        initView();
//        initData();
//
//        return view;
//
//    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_before_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {
        initTitle();
        initView();
        initData();
    }
    @Override
    public void onLazyLoad() {

    }
    /**
     * 初始化title
     */
    public void initTitle() {
        //扫一扫
        jkxTitleLeftBtn.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText("考中");

        //消息
        jkxTitleRightBtn.setVisibility(View.VISIBLE);


    }
    public static BeforePageFragment newInstance() {
        return new BeforePageFragment();
    }

    public void initView() {

        DaggerBeforePageComponent.builder()
                .appComponent(getAppComponent())
                .beforePageModule(new BeforePageModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        //                findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        //                adapter = new FindsAdapter(R.layout.item_finds_recyclerview);
        //                adapter.setOnLoadMoreListener(this);
        //                adapter.setEnableLoadMore(true);
        //                findRecyclerview.setAdapter(adapter);

        mHandler = new Handler();

    }

    public void initData() {
        showJDLoadingDialog();
        //                mPresenter.getFindData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                hideJDLoadingDialog();
            }
        }, 2000);
    }


    @Override
    public void setResponseData(ForgetPasswordResponse registerResponse) {

    }

    @Override
    public void showProgressDialogView() {
        showJDLoadingDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hideJDLoadingDialog();
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @OnClick({R2.id.jkx_title_left_btn, R2.id.jkx_title_right_btn})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left_btn) {
        } else if (i == R.id.jkx_title_right_btn) {
        }
    }
}
