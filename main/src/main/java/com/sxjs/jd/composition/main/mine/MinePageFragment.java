package com.sxjs.jd.composition.main.mine;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxjs.common.base.BaseFragment;
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
public class MinePageFragment extends BaseFragment implements MinePageContract.View, PtrHandler {


    @Inject
    MinePagePresenter mPresenter;
    @BindView(R2.id.fake_status_bar)
    View              fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView          jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button            jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView          jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView          jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView          newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout    rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView          jkxTitleRight;
    @BindView(R2.id.iv_userPic)
    ImageView         ivUserPic;
    @BindView(R2.id.tv_name)
    TextView          tvName;
    @BindView(R2.id.iv_isVip)
    ImageView         ivIsVip;
    @BindView(R2.id.renzheng_no)
    TextView          renzhengNo;
    @BindView(R2.id.renzheng_shenhe)
    TextView          renzhengShenhe;
    @BindView(R2.id.renzheng_biangeng_shenhe)
    TextView          renzhengBiangengShenhe;
    @BindView(R2.id.renzheng_already)
    TextView          renzhengAlready;
    @BindView(R2.id.tv_isAuthentation)
    TextView          tvIsAuthentation;
    @BindView(R2.id.ll_userAuthentication)
    LinearLayout      llUserAuthentication;
    @BindView(R2.id.iv_image1)
    ImageView         ivImage1;
    @BindView(R2.id.rl_myFeedBack)
    RelativeLayout    rlMyFeedBack;
    @BindView(R2.id.iv_image2)
    ImageView         ivImage2;
    @BindView(R2.id.cert_download)
    RelativeLayout    certDownload;
    @BindView(R2.id.iv_image3)
    ImageView         ivImage3;
    @BindView(R2.id.rl_findCa)
    RelativeLayout    rlFindCa;
    @BindView(R2.id.iv_image6)
    ImageView         ivImage6;
    @BindView(R2.id.rl_modifyPassword)
    RelativeLayout    rlModifyPassword;
    @BindView(R2.id.iv_image10)
    ImageView         ivImage10;
    @BindView(R2.id.hospital_change)
    RelativeLayout    hospitalChange;
    @BindView(R2.id.iv_image7)
    ImageView         ivImage7;
    @BindView(R2.id.rl_rebindPhone)
    RelativeLayout    rlRebindPhone;
    @BindView(R2.id.iv_image4)
    ImageView         ivImage4;
    @BindView(R2.id.rl_about)
    RelativeLayout    rlAbout;
    @BindView(R2.id.tv_loginOut)
    TextView          tvLoginOut;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView      findPullRefreshHeader;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();

        return view;

    }


    public static MinePageFragment newInstance() {
        return new MinePageFragment();
    }

    public void initView() {

        DaggerMinePageComponent.builder()
                .appComponent(getAppComponent())
                .minePageModule(new MinePageModule(this, MainDataManager.getInstance(mDataManager)))
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


    @OnClick({R2.id.jkx_title_left, R2.id.jkx_title_left_btn, R2.id.jkx_title_right_btn,
            R2.id.renzheng_no, R2.id.renzheng_already, R2.id.ll_userAuthentication,
            R2.id.rl_myFeedBack, R2.id.cert_download, R2.id.rl_findCa, R2.id.rl_modifyPassword,
            R2.id.hospital_change, R2.id.rl_rebindPhone, R2.id.rl_about, R2.id.tv_loginOut})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {

        } else if (i == R.id.jkx_title_left_btn) {

        } else if (i == R.id.jkx_title_right_btn) {

        } else if (i == R.id.renzheng_no) {

        } else if (i == R.id.renzheng_already) {

        } else if (i == R.id.ll_userAuthentication) {

        } else if (i == R.id.rl_myFeedBack) {

        } else if (i == R.id.cert_download) {

        } else if (i == R.id.rl_findCa) {

        } else if (i == R.id.rl_modifyPassword) {

        } else if (i == R.id.hospital_change) {

        } else if (i == R.id.rl_rebindPhone) {

        } else if (i == R.id.rl_about) {

        } else if (i == R.id.tv_loginOut) {

        }
    }
}
