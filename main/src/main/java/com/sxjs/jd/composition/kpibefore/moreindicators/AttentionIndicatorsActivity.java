package com.sxjs.jd.composition.kpibefore.moreindicators;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseActivity;
import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.base.baseadapter.BaseViewHolder;
import com.sxjs.common.layoutmanager.MyLinearLayoutManager;
import com.sxjs.common.util.LogUtil;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ResponseCode;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.common.util.statusbar.StatusBarUtil;
import com.sxjs.common.widget.headerview.JDHeaderView;
import com.sxjs.common.widget.pulltorefresh.PtrFrameLayout;
import com.sxjs.common.widget.pulltorefresh.PtrHandler;
import com.sxjs.jd.MainDataManager;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.sxjs.jd.entities.AttentionIndicatorsResponse;
import com.sxjs.jd.entities.JkxNameIdBean;

import java.util.ArrayList;
import java.util.Calendar;
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
@Route(path = "/attentionIndicators/attentionIndicators")
public class AttentionIndicatorsActivity extends BaseActivity implements AttentionIndicatorsContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    AttentionIndicatorsPresenter mPresenter;
    @BindView(R2.id.attention_recycler_view)
    RecyclerView                 findRecyclerview;
    @BindView(R2.id.attention_pull_refresh_header)
    JDHeaderView                 findPullRefreshHeader;
    @BindView(R2.id.no_data_img)
    ImageView                    noDataImg;
    @BindView(R2.id.rl_no_data)
    RelativeLayout               rlNoData;


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
    @BindView(R2.id.jkx_text_right_select)
    TextView       jkxTitleRightSelect;

    private static final String TAG = "AttentionIndicatorsActivity";


    private AttentionIndicatorsPreviewsAdapter adapter;
    private AttentionIndicatorsResponse        attentionIndicatorsResponse;
    private List<JkxNameIdBean>                leftList;
    private List<JkxNameIdBean>                rightList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_indicators);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        initTitle();
        initView();

    }

    /**
     * 初始化title
     */

    public void initTitle() {
        String title = getIntent().getStringExtra("title");
        //返回按钮
        jkxTitleLeft.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText(title);


        //
        jkxTitleRightSelect.setVisibility(View.VISIBLE);

        jkxTitleRightSelect.setText("2019年07月");

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        leftList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            JkxNameIdBean bean = new JkxNameIdBean();
            bean.setName(year - i + "");
            bean.setId(year - i + "");
            leftList.add(bean);
        }
        rightList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            JkxNameIdBean bean = new JkxNameIdBean();
            if (i < 9) {
                bean.setId("0" + (i + 1));
            } else {
                bean.setId("" + (i + 1));
            }
            bean.setName((i + 1) + "月");
            rightList.add(bean);
        }

        jkxTitleRightSelect.setText(year + "年" + (month + 1) + "月");
        jkxTitleRightSelect.setTag(year + rightList.get(month).getId());
        rightSelect = month;


    }

    private void initView() {
        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerAttentionIndicatorsActivityComponent.builder()
                .appComponent(getAppComponent())
                .attentionIndicatorsPresenterModule(new AttentionIndicatorsPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        findRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AttentionIndicatorsPreviewsAdapter(R.layout.item_attention_indicators_recyclerview);
        adapter.setOnLoadMoreListener(this);
        adapter.setEnableLoadMore(false);
        adapter.loadMoreComplete();
        findRecyclerview.setAdapter(adapter);

        initData(jkxTitleRightSelect.getTag().toString());
    }

    private void initData(String monthly) {

        String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("MONTHLY", monthly);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "Y002");
        mapHeaders.put("SESSION_ID", session_id);

        mPresenter.getRequestNationalData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseNationalData(AttentionIndicatorsResponse attentionIndicatorsResponse) {
        this.attentionIndicatorsResponse = attentionIndicatorsResponse;
        try {
            String code = attentionIndicatorsResponse.getCode();
            String msg = attentionIndicatorsResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                List<AttentionIndicatorsResponse.DataBean> messageDate = attentionIndicatorsResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        List<AttentionIndicatorsResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        List<AttentionIndicatorsResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.VISIBLE);
                    }

                } else {
                    rlNoData.setVisibility(View.VISIBLE);
                }
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
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
    public void setMoreData(AttentionIndicatorsResponse moreDate) {
        try {
            String code = moreDate.getCode();
            String msg = moreDate.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<AttentionIndicatorsResponse.DataBean> data = moreDate.getData();
                if (data != null) {

                    for (int i = 0; i < data.size(); i++) {
                        adapter.getData().add(data.get(i));
                    }
                }


                adapter.loadMoreComplete();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
                adapter.loadMoreComplete();
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();
            } else {
                adapter.loadMoreComplete();
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            adapter.loadMoreComplete();
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destory();
        }
    }

    @Override
    public void onLoadMoreRequested() {


        findRecyclerview.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.loadMoreComplete();
                //                String session_id = PrefUtils.readSESSION_ID(mContext);
                //
                //                Map<String, Object> mapParameters = new HashMap<>(1);
                //                mapParameters.put("MESSAGE_TYPE", "1");
                //
                //                Map<String, String> mapHeaders = new HashMap<>(2);
                //                mapHeaders.put("ACTION", "I001");
                //                mapHeaders.put("SESSION_ID", session_id);
                //
                //                mPresenter.getMoreFindData(mapHeaders, mapParameters);
            }
        }, 500);
    }

    //    AttentionIndicators
    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData(jkxTitleRightSelect.getTag().toString());
                frame.refreshComplete();
            }
        }, 500);
    }

    @OnClick({R2.id.jkx_title_left, R2.id.jkx_text_right_select})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {
            finish();
        } else if (i == R.id.jkx_text_right_select) {
            if (popupWindow == null)
                initPop();
            setWindowAlhpa(0.97f);
            popupWindow.showAsDropDown(jkxTitleRightSelect, 0, 0);
        }
    }

    private void setWindowAlhpa(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);
    }

    PopupWindow popupWindow;
    int         leftSelect;
    int         rightSelect;
    private LeftAdapter  leftAdapter;
    private RightAdapter rightAdapter;

    public void initPop() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.jkx_focus_zhibiao_pop_view, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RecyclerView left_recycleView = view.findViewById(R.id.left_recycleView);
        leftAdapter = new LeftAdapter(R.layout.jkx_middle_exam_year_item, leftList);
        left_recycleView.setAdapter(leftAdapter);
        left_recycleView.setLayoutManager(new MyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                leftSelect = position;
                leftAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView right_recycleView = view.findViewById(R.id.right_recycleView);
        rightAdapter = new RightAdapter(R.layout.jkx_middle_exam_year_item, rightList);
        right_recycleView.setAdapter(rightAdapter);
        right_recycleView.setLayoutManager(new MyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                rightSelect = position;
                rightAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                jkxTitleRightSelect.setText(leftList.get(leftSelect).getName() + "年" + rightList.get(rightSelect).getName());
                jkxTitleRightSelect.setTag(leftList.get(leftSelect).getId() + rightList.get(rightSelect).getId());
                getFollowIndicator();
            }
        });

        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlhpa(1f);
            }
        });
    }

    private void getFollowIndicator() {

        LogUtil.e(TAG, "-------jkxTitleRightSelect----" + jkxTitleRightSelect.getTag().toString());
        initData(jkxTitleRightSelect.getTag().toString());
    }

    private class LeftAdapter extends BaseQuickAdapter<JkxNameIdBean, BaseViewHolder> {

        public LeftAdapter(int layoutResId, @Nullable List<JkxNameIdBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, JkxNameIdBean item, int position) {
            helper.setText(R.id.tv_year, item.getName());
            if (helper.getAdapterPosition() == leftSelect) {
                helper.getView(R.id.tv_year).setBackgroundColor(mContext.getResources().getColor(R.color.pop_right));
            } else {
                helper.getView(R.id.tv_year).setBackgroundColor(mContext.getResources().getColor(R.color.pop_left));
            }
        }


    }

    private class RightAdapter extends BaseQuickAdapter<JkxNameIdBean, BaseViewHolder> {

        public RightAdapter(int layoutResId, @Nullable List<JkxNameIdBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, JkxNameIdBean item, int position) {
            helper.setText(R.id.tv_year, item.getName());
            if (helper.getAdapterPosition() == rightSelect) {
                helper.getView(R.id.tv_year).setBackgroundColor(mContext.getResources().getColor(R.color.pop_left));
            } else {
                helper.getView(R.id.tv_year).setBackgroundColor(mContext.getResources().getColor(R.color.pop_right));
            }
        }


    }
}
