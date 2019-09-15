package com.sxjs.jd.composition.main.home;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.view.MediumBoldTextView;
import com.sxjs.common.view.TextViewScrollVertical;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.main.findfragment.DaggerFindFragmentComponent;
import com.sxjs.jd.composition.main.findfragment.FindPresenterModule;
import com.sxjs.jd.composition.main.findfragment.FindsAdapter;
import com.sxjs.jd.composition.main.homefragment.HomePresenter;
import com.sxjs.jd.entities.ForgetPasswordResponse;
import com.youth.banner.Banner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description:
 */
public class HomePageFragment extends BaseFragment implements HomePageContract.View, PtrHandler {


    @BindView(R2.id.fake_status_bar)
    View                   fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView               jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button                 jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView               jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView               jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView               newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout         rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView               jkxTitleRight;
    @BindView(R2.id.banner)
    Banner                 banner;
    @BindView(R2.id.tv_examName)
    MediumBoldTextView     tvExamName;
    @BindView(R2.id.tv_statusNameAndDate)
    TextView               tvStatusNameAndDate;
    @BindView(R2.id.tv_day1)
    TextView               tvDay1;
    @BindView(R2.id.tv_day2)
    TextView               tvDay2;
    @BindView(R2.id.tv_hour1)
    TextView               tvHour1;
    @BindView(R2.id.tv_hour2)
    TextView               tvHour2;
    @BindView(R2.id.ll_examSchedule)
    LinearLayout           llExamSchedule;
    @BindView(R2.id.ll_examFinsh)
    LinearLayout           llExamFinsh;
    @BindView(R2.id.yichang_img)
    ImageView              yichangImg;
    @BindView(R2.id.tv_totleExceptions)
    TextView               tvTotleExceptions;
    @BindView(R2.id.tv_exceptiopDesc)
    TextViewScrollVertical tvExceptiopDesc;
    @BindView(R2.id.rl_examWarn)
    RelativeLayout         rlExamWarn;
    @BindView(R2.id.tv_rank)
    MediumBoldTextView     tvRank;
    @BindView(R2.id.tv_changeTotle)
    TextView               tvChangeTotle;
    @BindView(R2.id.tv_finshRank)
    MediumBoldTextView     tvFinshRank;
    @BindView(R2.id.tv_name)
    MediumBoldTextView     tvName;
    @BindView(R2.id.tv_value)
    MediumBoldTextView     tvValue;
    @BindView(R2.id.tv_change1)
    TextView               tvChange1;
    @BindView(R2.id.tv_old1)
    TextView               tvOld1;
    @BindView(R2.id.tv_finshOld1)
    TextView               tvFinshOld1;
    @BindView(R2.id.tv_change2)
    TextView               tvChange2;
    @BindView(R2.id.tv_old2)
    TextView               tvOld2;
    @BindView(R2.id.tv_finshOld2)
    TextView               tvFinshOld2;
    @BindView(R2.id.zhibiao_sort)
    LinearLayout           zhibiaoSort;
    @BindView(R2.id.zhibiao_check)
    ImageView              zhibiaoCheck;
    @BindView(R2.id.consult)
    LinearLayout           consult;
    @BindView(R2.id.zcjd)
    ImageView              zcjd;
    @BindView(R2.id.tv_news_more)
    TextView               tvNewsMore;
    @BindView(R2.id.iv_imgUrl)
    ImageView              ivImgUrl;
    @BindView(R2.id.tv_title)
    TextView               tvTitle;
    @BindView(R2.id.tv_count)
    TextView               tvCount;
    @BindView(R2.id.tv_time)
    TextView               tvTime;
    @BindView(R2.id.ll_newsItem)
    LinearLayout           llNewsItem;
    @BindView(R2.id.iv_imgUrl1)
    ImageView              ivImgUrl1;
    @BindView(R2.id.tv_title1)
    TextView               tvTitle1;
    @BindView(R2.id.tv_count1)
    TextView               tvCount1;
    @BindView(R2.id.tv_time1)
    TextView               tvTime1;
    @BindView(R2.id.ll_newsItem1)
    LinearLayout           llNewsItem1;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView           findPullRefreshHeader;

    @Inject
    HomePagePresenter mPresenter;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();

        return view;

    }


    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    public void initView() {

        DaggerHomePageComponent.builder()
                .appComponent(getAppComponent())
                .homePageModule(new HomePageModule(this, MainDataManager.getInstance(mDataManager)))
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

    @OnClick({R2.id.jkx_title_left, R2.id.jkx_title_left_btn, R2.id.jkx_title_right_btn, R2.id.ll_examSchedule,
            R2.id.rl_examWarn, R2.id.zhibiao_sort, R2.id.zhibiao_check, R2.id.zcjd, R2.id.tv_news_more,
            R2.id.ll_newsItem, R2.id.ll_newsItem1})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {

        } else if (i == R.id.jkx_title_left_btn) {

        } else if (i == R.id.jkx_title_right_btn) {

        } else if (i == R.id.ll_examSchedule) {

        } else if (i == R.id.rl_examWarn) {

        } else if (i == R.id.zhibiao_sort) {

        } else if (i == R.id.zhibiao_check) {

        } else if (i == R.id.zcjd) {

        } else if (i == R.id.tv_news_more) {

        } else if (i == R.id.ll_newsItem) {

        } else if (i == R.id.ll_newsItem1) {

        }
    }
}
