package com.sxjs.jd.composition.main.unused.findfragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.common.base.BaseFragment;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.FindsBean;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author admin
 */
public class FindFragment extends BaseFragment implements FindContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    FindPresenter mPresenter;
    @BindView(R2.id.find_recyclerview)
    RecyclerView  findRecyclerview;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView  findPullRefreshHeader;
    private FindsAdapter adapter;
    private Handler      mHandler;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_find, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        initView();
//        initData();
//
//        return view;
//
//    }
    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {

        initView();
        initData();

    }
    @Override
    public void onLazyLoad() {

    }
    public static FindFragment newInstance() {
        return new FindFragment();
    }

    public void initView() {

        DaggerFindFragmentComponent.builder()
                .appComponent(getAppComponent())
                .findPresenterModule(new FindPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new FindsAdapter(R.layout.item_finds_recyclerview);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(true);
        findRecyclerview.setAdapter(adapter);

        mHandler = new Handler();

    }

    public void initData() {
        showJDLoadingDialog();
        mPresenter.getFindData();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                hideJDLoadingDialog();
            }
        }, 500);
    }


    private static final String TAG = "FindFragment";

    @Override
    public void setFindData(FindsBean find) {



        adapter.addData(find.content);
    }

    @Override
    public void setMoreData(FindsBean find) {
        adapter.getData().addAll(find.content);
        adapter.loadMoreComplete();

    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 500);

    }

    @Override
    public void onLoadMoreRequested() {
        findRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapter.getData().size() >= 90) {
                    adapter.loadMoreEnd(false);
                } else {
                    mPresenter.getMoreFindData();
                }
            }
        }, 500);

    }
}
