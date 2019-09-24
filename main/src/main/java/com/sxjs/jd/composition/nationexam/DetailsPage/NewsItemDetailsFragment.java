package com.sxjs.jd.composition.nationexam.DetailsPage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.composition.html.messagedetails.MessageWebViewActivity;
import com.sxjs.jd.composition.message.notification.DaggerNotificationFragmentComponent;
import com.sxjs.jd.entities.MessageNotificationResponse;
import com.sxjs.jd.entities.NewsListResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: xp
 * @Date: 2019/9/15 16:50
 * @Description: 国考快讯
 */
public class NewsItemDetailsFragment extends BaseFragment implements NewsItemDetailsFragmentContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {


    @Inject
    NewsItemDetailsFragmentPresenter mPresenter;

    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    @BindView(R2.id.find_recyclerview)
    RecyclerView findRecyclerview;
    private Handler mHandler;

    private static final String                      TAG      = "NationExamActivity";
    private static final String                      newsId   = "newsDetailsId";
    private              String                      mSession_id;
    private              MessageNotificationResponse messageNotificationResponse;
    private              Dialog                      dialog;
    private              NewsItemDetailsAdapter      adapter;
    private              View                        mView;
    private              Bundle                      mArguments;
    private              String                      mNewsItemId;
    private              NewsListResponse            newsListResponse;
    private              int                         mPAGE_no = 0;
    private              int                         mCount;
    private              int                         mPage_no;
    private              int                         mPage_size;
    private              int                         mTotal_page;
    ;

    //    @Nullable
    //    @Override
    //    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //        if (null == mView) {
    //            mView = inflater.inflate(R.layout.fragment_notification, container, false);
    //            unbinder = ButterKnife.bind(this, mView);
    //            Bundle arguments = getArguments();
    //
    //            initView();
    //            initData();
    //        }else {
    //            unbinder = ButterKnife.bind(this, mView);
    //        }
    //
    //        return mView;
    //
    //    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, mView);
        mArguments = getArguments();
        mNewsItemId = mArguments.getString(newsId);


        return mView;
    }

    @Override
    public void initEvent() {


    }

    @Override
    public void onLazyLoad() {
        initView();
        initData(mNewsItemId, mPAGE_no);
    }
//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        JkxExamNewsListResponse.DataBean.PAGEBean newsResponse = (JkxExamNewsListResponse.DataBean.PAGEBean) adapter.getItem(position);
//        Bundle bundle = new Bundle();
//        bundle.putString("url", newsResponse.getCONTENT_URI());
//        bundle.putString("title", newsResponse.getTITLE());
//        mEventCallBack.EventClick(JkxExamNewsFragment.EVENT_GONEWSDETAIL, bundle);
//
//    }

    public static NewsItemDetailsFragment newInstance(String newsDetailsId) {
        NewsItemDetailsFragment newsItemDetailsFragment = new NewsItemDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(newsId, newsDetailsId);
        newsItemDetailsFragment.setArguments(bundle);
        return newsItemDetailsFragment;
    }

    public void initView() {
        mHandler = new Handler();
        mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerNewsItemDetailsFragmentComponent.builder()
                .appComponent(getAppComponent())
                .newsItemDetailsFragmentModule(new NewsItemDetailsFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new NewsItemDetailsAdapter(R.layout.item_news_details_page);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(true);
//        adapter.setOnItemClickListener(this);
        findRecyclerview.setAdapter(adapter);


    }

    public void initData(String mNewsItemId, int PAGE_NO) {

        LogUtil.e(TAG, "==mNewsItemId==" + mNewsItemId + "==PAGE_NO==" + PAGE_NO);
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("COLUMN_ID", mNewsItemId);
        mapParameters.put("PAGE_NO", PAGE_NO);
        mapParameters.put("PAGE_SIZE", 20);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I006");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }

    public void initDataMorw(String mNewsItemId, int PAGE_NO) {

        LogUtil.e(TAG, "==mNewsItemId==" + mNewsItemId + "==PAGE_NO==" + PAGE_NO);
        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("COLUMN_ID", mNewsItemId);
        mapParameters.put("PAGE_NO", PAGE_NO + "");
        mapParameters.put("PAGE_SIZE", 20 + "");

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I006");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getMoreFindData(mapHeaders, mapParameters);
    }

    @Override
    public void setResponseData(NewsListResponse newsListResponse) {

        this.newsListResponse = newsListResponse;

        try {
            String code = newsListResponse.getCode();
            String msg = newsListResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                NewsListResponse.DataBean dataBean = newsListResponse.getData();
                if (dataBean != null) {
                    mCount = dataBean.getCOUNT();
                    mPage_no = dataBean.getPAGE_NO();
                    mPage_size = dataBean.getPAGE_SIZE();
                    mTotal_page = dataBean.getTOTAL_PAGE();
                    List<NewsListResponse.DataBean.PAGEBean> page = dataBean.getPAGE();
                    if (page != null) {
                        List<NewsListResponse.DataBean.PAGEBean> data = adapter.getData();
                        if (page.size() == mCount) {
                            adapter.setEnableLoadMore(false);
                        } else {
                            adapter.setEnableLoadMore(true);
                        }
                        data.clear();


                        adapter.addData(page);
                    }
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        } finally {
            adapter.loadMoreComplete();
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
    public void setMoreData(NewsListResponse moreDate) {
        try {
            String code = moreDate.getCode();
            String msg = moreDate.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                NewsListResponse.DataBean data = moreDate.getData();
                if (data != null) {
                    List<NewsListResponse.DataBean.PAGEBean> page = data.getPAGE();

                    List<NewsListResponse.DataBean.PAGEBean> dataList = adapter.getData();

                    int sizeDataList = dataList.size();
                    int sizePage = page.size();
                    int listTotalSize = sizeDataList + sizePage;
                    if (listTotalSize >= mCount) {
                        adapter.setEnableLoadMore(false);
                    } else {
                        adapter.setEnableLoadMore(true);
                    }

                    adapter.getData().addAll(page);
                    //                    for (int i = 0; i < page.size(); i++) {
                    //                        adapter.getData().add(page.get(i));
                    //                    }

                }


                //                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
                //                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                mActivity.finish();
            } else {

                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {

            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        } finally {
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPAGE_no = 0;
                initData(mNewsItemId, mPAGE_no);

                frame.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void onLoadMoreRequested() {


        findRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {

                mPAGE_no = mPAGE_no + 1;
                initDataMorw(mNewsItemId, mPAGE_no);
            }
        }, 2000);


    }


}
