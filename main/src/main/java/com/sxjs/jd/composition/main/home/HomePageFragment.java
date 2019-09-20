package com.sxjs.jd.composition.main.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.view.MediumBoldTextView;
import com.sxjs.common.view.TextViewScrollVertical;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.html.homeweb.HomeWebActivity;
import com.sxjs.jd.composition.html.homeweb.HomeWebViewActivity;
import com.sxjs.jd.composition.message.MessageActivity;
import com.sxjs.jd.entities.AppUpdateResponse;
import com.sxjs.jd.entities.HomePageResponse;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constacne.UiType;
import listener.Md5CheckResultListener;
import listener.UpdateDownloadListener;
import model.UiConfig;
import model.UpdateConfig;
import update.UpdateAppUtils;

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
    private              Handler                            mHandler;
    private static final String                             TAG = "HomePageFragment";
    private              HomePageResponse                   homePageResponse;
    private              HomePageResponse.DataBean.I002Bean i002;
    private              AppUpdateResponse                  appUpdateResponse;

    //    @Nullable
    //    @Override
    //    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
    //        unbinder = ButterKnife.bind(this, view);
    //        initTitle();
    //        initView();
    //        initData();
    //
    //        return view;
    //
    //    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {

        initTitle();
        initView();
        initData();
        initUpdateData();
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
        jkxTitleCenter.setText("绩时查");

        //消息
        jkxTitleRightBtn.setVisibility(View.VISIBLE);


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

        tvExceptiopDesc.setTextStillTime(3000);
        tvExceptiopDesc.setAnimTime(300);
        tvExceptiopDesc.setOnItemClickListener(new TextViewScrollVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                /**
                 * 跳转到指标异常详情
                 */
                //                Bundle bundle = new Bundle();
                //                String id = JkxHomeView.this.targetExceptionResponse.getINDICATION().get(position).getID();
                //                bundle.putString("id", id);
                //                mEventCallBack.EventClick(JkxHomeFragment.EVENT_EXAMWARN, bundle);
            }
        });

    }

    public void initData() {
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "H000");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);

        //        mHandler.postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //
        //                hideJDLoadingDialog();
        //            }
        //        }, 2000);
    }

    public void initUpdateData() {
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "CM004");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestUpdateData(mapHeaders, mapParameters);


    }

    @Override
    public void setResponseData(HomePageResponse homePageResponse) {

        this.homePageResponse = homePageResponse;

        try {
            String code = homePageResponse.getCode();
            String msg = homePageResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                HomePageResponse.DataBean data = homePageResponse.getData();
                if (data != null) {

                    //未读消息

                    HomePageResponse.DataBean.I002Bean i002 = data.getI002();
                    this.i002 = i002;
                    setUnReadMessage(data.getI002());

                    //轮播图
                    initNewBannerLoader(data.getI004());
                    //考核状态
                    setPerformanceStatus(data.getH001());

                    //指标异常
                    setTargetExceptionData(data.getH003());

                    //指标排行
                    HomePageResponse.DataBean.H006Bean bean = null;
                    if (data.getH006() != null) {
                        bean = data.getH006().get(0);
                    }
                    setZhiBiao(data.getH005(), bean);

                    //国考新闻
                    setNewsData(data.getI006().getPAGE());

                } else {
                    ToastUtil.showToast(mContext, "响应数据缺少主参数");
                }


                //                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
                //                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext, msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext, "解析数据失败:");
            LogUtil.e(TAG, "解析数据失败:" + e.getMessage());
        }

    }

    @Override
    public void setResponseUpdateData(AppUpdateResponse appUpdateResponse) {
        this.appUpdateResponse = appUpdateResponse;

        try {
            String code = appUpdateResponse.getCode();
            String msg = appUpdateResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                isUpDataVersion(appUpdateResponse);


                //                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
                //                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext, msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext, "解析数据失败:");
            LogUtil.e(TAG, "解析数据失败:" + e.getMessage());
        }
    }


    /**
     * 判断是否更新
     */
    public void isUpDataVersion(AppUpdateResponse data) {

        if (data == null || data.getData().getVersionCode() == null) {

            return;
        }


        int newVerCode = Integer.parseInt(data.getData().getVersionCode()); // 检测是否有新的安装包更新
        int oldVerCode = getVerCode();
        if (newVerCode > oldVerCode) {
            int lAvIsMajor = Integer.parseInt(data.getData().getFlag());
            openDownLoadDialog(data.getData().getUrl(), data.getData().getUpdateTip(), lAvIsMajor);
            //            return;
        }


    }

    public int getVerCode() {


        int versioncode;
        try {
            // ---get the package info---
            PackageManager pm = mActivity.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mActivity.getPackageName(), 0);
            versioncode = pi.versionCode;

        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
            versioncode = -1;
        }
        return versioncode;


    }

    /**
     * 弹出更新Dialog
     */
    private void openDownLoadDialog(String apkUrl, String updateContent, int flag) {
        UpdateConfig updateConfig = new UpdateConfig();
        updateConfig.setCheckWifi(true);
        updateConfig.setNeedCheckMd5(true);
        if (flag == 1) {
            updateConfig.setForce(true);   //flag == 1 强制更新
        } else {
            updateConfig.setForce(false);  //flag == 1 非强制更新
        }

        updateConfig.setNotifyImgRes(R.drawable.notification_logo_72);

        UiConfig uiConfig = new UiConfig();
        uiConfig.setUiType(UiType.PLENTIFUL);

        UpdateAppUtils
                .getInstance()
                .apkUrl(apkUrl)
                .updateTitle("系统提示")
                .updateContent(updateContent + "\n          点击立即更新会在后台下载,下载完成会自动提示安装!")
                .uiConfig(uiConfig)
                .updateConfig(updateConfig)

                .setMd5CheckResultListener(new Md5CheckResultListener() {
                    @Override
                    public void onResult(boolean result) {

                    }
                })
                .setUpdateDownloadListener(new UpdateDownloadListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onDownload(int progress) {

                    }

                    @Override
                    public void onFinish() {
                        ToastUtil.showToast(mContext, "下载完成");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })

                .update();

    }


    /**
     * 未读消息设置
     *
     * @param unReadMessage 未读消息响应数据
     */
    @SuppressLint("SetTextI18n")
    public void setUnReadMessage(HomePageResponse.DataBean.I002Bean unReadMessage) {
        if (unReadMessage == null)
            return;
        int tz = unReadMessage.getWD();
        int tx = unReadMessage.getTX();
        int gz = unReadMessage.getYJ();
        if (tz + tx + gz != 0) {
            rlNewMessage.setVisibility(View.VISIBLE);
            newMessage.setText(tz + tx + gz + "");
            if (tz + tx + gz > 99) {
                newMessage.setText("99+");
            }
        } else {
            rlNewMessage.setVisibility(View.GONE);
        }

    }


    /**
     * 轮播图
     *
     * @param bannerResponseList 轮播图响应数据
     */
    public void initNewBannerLoader(final List<HomePageResponse.DataBean.I004Bean> bannerResponseList) {
        List<String> lists = new ArrayList<>();

        for (int i = 0; i < bannerResponseList.size(); i++) {
            lists.add(bannerResponseList.get(i).getIMAGE_URI());
        }

        banner.setmIndicatorResId(R.drawable.icon_lbd, R.drawable.icon_lbd1)
                .setImages(lists)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.drawable.home_banner)
                                .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                                .dontAnimate()
                                .fallback(R.drawable.home_banner);
                        Glide.with(mContext)
                                .load(path)
                                .apply(options)
                                .into(imageView);


                    }
                })
                .setDelayTime(3000)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {

                        /**
                         * 跳转到进入消息详情页
                         */
                        //进入轮播详情页
                        String url = bannerResponseList.get(position).getNOTIFY_URI();
                        Intent intent = new Intent(mActivity, HomeWebViewActivity.class);
                        intent.putExtra("title", "详情");
                        intent.putExtra("url", url);
                        startActivity(intent);

                    }
                }).start();
    }

    /**
     * 考核状态
     *
     * @param performanceStatusResponse 考核状态响应数据
     */
    private boolean isFinish;

    @SuppressLint("SetTextI18n")
    public void setPerformanceStatus(HomePageResponse.DataBean.H001Bean performanceStatusResponse) {

        tvExamName.setText(performanceStatusResponse.getEXAM_NAME());
        tvStatusNameAndDate.setText(performanceStatusResponse.getEXAM_STAGE_NAME() + "(截止" + performanceStatusResponse.getEXAM_STAGE_END_DATE() + ")");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String time = "0";
        if (performanceStatusResponse.getEXAM_STAGE_END_DATE() != null) {
            time = performanceStatusResponse.getEXAM_STAGE_END_DATE();
        }
        Date endDate = TimeUtils.string2Date(time, dateFormat);
        //        if (endDate == null) {
        //            endDate = TimeUtils.getNowDate();
        //        }
        Date nowDate = TimeUtils.getNowDate();
        if (endDate == null || endDate.before(nowDate)) {
            isFinish = true;
            llExamFinsh.setVisibility(View.VISIBLE);
            llExamSchedule.setVisibility(View.GONE);
        } else {
            isFinish = false;
            llExamFinsh.setVisibility(View.GONE);
            llExamSchedule.setVisibility(View.VISIBLE);

        }
        if (!isFinish) {
            long hours = TimeUtils.getTimeSpanByNow(endDate, TimeConstants.HOUR);
            String day = (hours / 24) + "";
            String hour = (hours % 24) + "";
            if (day.length() > 1) {
                String day1 = day.substring(0, 1);
                tvDay1.setText(day1);
                String day2 = day.substring(1, 2);
                tvDay2.setText(day2);
            } else {
                tvDay1.setText("0");
                tvDay2.setText(day);
            }

            if (hour.length() > 1) {
                String hour1 = hour.substring(0, 1);
                tvHour1.setText(hour1);
                String hour2 = hour.substring(1, 2);
                tvHour2.setText(hour2);
            } else {
                tvHour1.setText("0");
                tvHour2.setText(hour);
            }

        }

    }

    private HomePageResponse.DataBean.H003Bean targetExceptionResponse;

    /**
     * 指标异常
     *
     * @param response 指标异常响应数据
     */
    @SuppressLint("SetTextI18n")
    public void setTargetExceptionData(HomePageResponse.DataBean.H003Bean response) {


        if (response != null && response.getINDICATION() != null) {
            this.targetExceptionResponse = response;
            rlExamWarn.setVisibility(View.VISIBLE);
            tvTotleExceptions.setText("指标 " + response.getINDICATION().size() + " 项异常");
            ArrayList<String> descList = new ArrayList<>();
            for (int i = 0; i < response.getINDICATION().size(); i++) {
                descList.add(response.getINDICATION().get(i).getDESCRIBE());
            }
            tvExceptiopDesc.setTextList(descList);
            tvExceptiopDesc.startAutoScroll();
        } else {
            //rl_examWarn.setVisibility(View.GONE);
        }


    }


    /**
     * 指标排行
     *
     * @param h005Bean 指标排行
     * @param h006Bean 指标排行
     */
    public void setZhiBiao(HomePageResponse.DataBean.H005Bean h005Bean, HomePageResponse.DataBean.H006Bean h006Bean) {
        if (h005Bean == null && h006Bean == null) {
            // zhibiao_sort.setVisibility(View.GONE);
        } else {
            //zhibiao_sort.setVisibility(View.VISIBLE);
        }
        if (isFinish) {
            if (h006Bean != null && h006Bean.getINDICATION_NAME() != null) {

                //            tv_finshRank.setVisibility(View.VISIBLE);


                tvFinshOld1.setVisibility(View.VISIBLE);

                tvFinshOld2.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(h006Bean.getINDICATION_NAME().get(3))) {
                    tvName.setText(h006Bean.getINDICATION_NAME().get(3));
                }
                if (!TextUtils.isEmpty(h006Bean.getINDICATION_VALUE())) {
                    tvValue.setText(h006Bean.getINDICATION_VALUE());
                }
                if (!TextUtils.isEmpty(h006Bean.getSAMETYPE_HOSPITAL_RANK())) {
                    tvFinshOld1.setText(h006Bean.getSAMETYPE_HOSPITAL_RANK());
                }
                if (!TextUtils.isEmpty(h006Bean.getPREFECTURE_HOSPITAL_RANK())) {
                    tvFinshOld2.setText(h006Bean.getPREFECTURE_HOSPITAL_RANK());
                }


            }
            //            tv_rank.setVisibility(View.GONE);


            tvChangeTotle.setVisibility(View.GONE);
            tvChange1.setVisibility(View.GONE);
            tvChange2.setVisibility(View.GONE);
            tvOld1.setVisibility(View.GONE);
            tvOld2.setVisibility(View.GONE);


        } else {
            if (h005Bean != null && h005Bean.getINDICATION() != null) {


                //            tv_finshRank.setVisibility(View.GONE);
                tvFinshOld1.setVisibility(View.GONE);
                tvFinshOld2.setVisibility(View.GONE);

                //            tv_rank.setVisibility(View.VISIBLE);
                tvChangeTotle.setVisibility(View.VISIBLE);
                tvChange1.setVisibility(View.VISIBLE);
                tvChange2.setVisibility(View.VISIBLE);
                tvOld1.setVisibility(View.VISIBLE);
                tvOld2.setVisibility(View.VISIBLE);


                if (!TextUtils.isEmpty(h005Bean.getINDICATION().get(0).getINDICATION_NAME().get(3))) {
                    tvName.setText(h005Bean.getINDICATION().get(0).getINDICATION_NAME().get(3));
                }
                if (!TextUtils.isEmpty(h005Bean.getINDICATION().get(0).getINDICATION_VALUE())) {
                    tvValue.setText(h005Bean.getINDICATION().get(0).getINDICATION_VALUE());
                }
                if (!TextUtils.isEmpty(h005Bean.getRANK_DECLINE())) {
                    tvChangeTotle.setText(h005Bean.getRANK_DECLINE());
                }
                if (!TextUtils.isEmpty(h005Bean.getINDICATION().get(0).getSAMETYPE_HOSPITAL_RANK())) {
                    tvChange1.setText(h005Bean.getINDICATION().get(0).getSAMETYPE_HOSPITAL_RANK());
                }
                if (!TextUtils.isEmpty(h005Bean.getINDICATION().get(0).getSAMETYPE_HOSPITAL_OLD_RANK())) {
                    tvOld1.setText(h005Bean.getINDICATION().get(0).getSAMETYPE_HOSPITAL_OLD_RANK());
                }
                if (!TextUtils.isEmpty(h005Bean.getINDICATION().get(0).getPREFECTURE_HOSPITAL_RANK())) {
                    tvChange2.setText(h005Bean.getINDICATION().get(0).getPREFECTURE_HOSPITAL_RANK());
                }
                if (!TextUtils.isEmpty(h005Bean.getINDICATION().get(0).getPREFECTURE_HOSPITAL_OLD_RANK())) {
                    tvOld2.setText(h005Bean.getINDICATION().get(0).getPREFECTURE_HOSPITAL_OLD_RANK());
                }
            }
        }


    }

    @SuppressLint("SetTextI18n")
    public void setNewsData(List<HomePageResponse.DataBean.I006Bean.PAGEBean> list) {

        RequestOptions options = new RequestOptions()
                .error(R.drawable.home_pic1);

        if (list.size() > 0) {
            llNewsItem.setTag(list.get(0).getCONTENT_URI());
            Glide.with(mContext)
                    .load(list.get(0).getIMAGE_URI())
                    .apply(options)
                    .into(ivImgUrl);
            tvTitle.setText(list.get(0).getTITLE());
            tvCount.setText("666阅读");
            tvTime.setText(list.get(0).getDATE());
        }

        if (list.size() > 1) {
            llNewsItem1.setTag(list.get(1).getCONTENT_URI());
            Glide.with(mContext)
                    .load(list.get(1).getIMAGE_URI())
                    .apply(options)
                    .into(ivImgUrl1);
            tvTitle1.setText(list.get(1).getTITLE());
            tvCount1.setText("666阅读");
            tvTime1.setText(list.get(1).getDATE());
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        startAutoScroll();
    }


    @Override
    public void onPause() {
        super.onPause();
        stopAutoScroll();
    }

    public void startAutoScroll() {
        banner.startAutoPlay();  //新版bannner
        if ((this.targetExceptionResponse != null) && (this.targetExceptionResponse.getINDICATION() != null)
                && (this.targetExceptionResponse.getINDICATION() != null)) {


            //指标异常 轮播开始
            tvExceptiopDesc.startAutoScroll();//垂直滚动的textview
        }
    }

    public void stopAutoScroll() {
        banner.stopAutoPlay();//新版bannner
        if ((this.targetExceptionResponse != null) && (this.targetExceptionResponse.getINDICATION() != null)
                && (this.targetExceptionResponse.getINDICATION() != null)) {

            //指标异常 轮播停止
            tvExceptiopDesc.stopAutoScroll();//垂直滚动的textview
        }
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
        //指标异常 轮播停止
        tvExceptiopDesc.stopAutoScroll();
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                //指标异常 轮播开始
                tvExceptiopDesc.startAutoScroll();
                frame.refreshComplete();
            }
        }, 800);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R2.id.jkx_title_left, R2.id.jkx_title_left_btn, R2.id.jkx_title_right_btn, R2.id.ll_examSchedule,
            R2.id.rl_examWarn, R2.id.zhibiao_sort, R2.id.zhibiao_check, R2.id.zcjd, R2.id.tv_news_more,
            R2.id.ll_newsItem, R2.id.ll_newsItem1})
    public void onViewClicked(View view) {
        Intent mIntent = null;
        int i = view.getId();
        if (i == R.id.jkx_title_left) {

        } else if (i == R.id.jkx_title_left_btn) {
            //开启扫一扫

        } else if (i == R.id.jkx_title_right_btn) {
            String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
            //我的消息


            mIntent = new Intent(mActivity, MessageActivity.class);
            if (i002 != null) {
                int tz = i002.getWD();
                mIntent.putExtra("tz", tz);
                int tx = i002.getTX();
                mIntent.putExtra("tx", tx);
                int gz = i002.getYJ();
                mIntent.putExtra("gz", gz);


            }

            startActivity(mIntent);


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
