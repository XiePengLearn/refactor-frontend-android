package com.sxjs.jd.composition.kpihome.previews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.common.base.BaseActivity;
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
import com.sxjs.jd.composition.kpihome.schedule.DaggerExamScheduleActivityComponent;
import com.sxjs.jd.entities.ExamScheduleResponse;
import com.sxjs.jd.entities.PreviewsScheduleResponse;

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
@Route(path = "/previewsScheduleActivity/previewsScheduleActivity")
public class PreviewsScheduleActivity extends BaseActivity implements PreviewsScheduleContract.View, PtrHandler {
    @Inject
    PreviewsSchedulePresenter mPresenter;
    @BindView(R2.id.fake_status_bar)
    View                      fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView                  jkxTitleLeft;
    @BindView(R2.id.jkx_title_left_btn)
    Button                    jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView                  jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView                  jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView                  newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout            rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView                  jkxTitleRight;
    @BindView(R2.id.lv_contain)
    ListView                  lvContain;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView              findPullRefreshHeader;


    private              String                                  mXinGeToken;
    private static final String                                  TAG = "NationExamActivity";
    private              Button                                  mLoginEntry;
    private              List<PreviewsScheduleResponse.DataBean> mListData;
    private              MyAdapter                               mAdapter;
    private              PreviewsScheduleResponse                previewsScheduleResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mListData = new ArrayList<>();

        initTitle();
        initView();
        initData();
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


    }

    private void initView() {
        DaggerPreviewsScheduleActivityComponent.builder()
                .appComponent(getAppComponent())
                .previewsSchedulePresenterModule(new PreviewsSchedulePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        findPullRefreshHeader.setPtrHandler(this);
        mAdapter = new MyAdapter(mListData);
        lvContain.setAdapter(mAdapter);
    }

    private void initData() {

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");
        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "Y005");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(PreviewsScheduleResponse previewsScheduleResponse) {
        this.previewsScheduleResponse = previewsScheduleResponse;
        try {
            String code = previewsScheduleResponse.getCode();
            String msg = previewsScheduleResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                List<PreviewsScheduleResponse.DataBean> data = previewsScheduleResponse.getData();
                mListData.clear();
                mListData.addAll(data);
                LogUtil.e(TAG, "-------mListData-------" + mListData);
                mAdapter.notifyDataSetChanged();

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
        if (mPresenter != null) {
            mPresenter.destory();
        }
    }

    @OnClick({R2.id.jkx_title_left, R2.id.jkx_title_right})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_title_left) {
            finish();

        } else if (i == R.id.jkx_title_right) {


        }
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {

                initData();
                frame.refreshComplete();
            }
        }, 500);
    }


    class MyAdapter extends BaseAdapter {

        private List<PreviewsScheduleResponse.DataBean> mListData;

        public MyAdapter(List<PreviewsScheduleResponse.DataBean> mListData) {
            this.mListData = mListData;
            LogUtil.e(TAG, "-------mListData-------" + mListData);
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.jkx_exam_schedule_item, null);
                holder = new ViewHolder();
                holder.img = convertView.findViewById(R.id.img);
                holder.img2 = convertView.findViewById(R.id.image2);
                holder.title = convertView.findViewById(R.id.tv_title);
                holder.time = convertView.findViewById(R.id.tv_time);
                holder.line1 = convertView.findViewById(R.id.line1);
                holder.line2 = convertView.findViewById(R.id.line2);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PreviewsScheduleResponse.DataBean dataBean = mListData.get(position);

            String status = dataBean.getPREVIEW_STAGE_STATUS();
            //            进行状态0未开始1-进行中2-已结束
            if (!TextUtils.isEmpty(status)) {
                if ("0".equals(status)) {
                    holder.img.setImageResource(R.drawable.exam_schedule_null);
                    holder.img2.setImageResource(R.drawable.exam_schedule_2_noselected);
                } else if ("1".equals(status)) {
                    holder.img.setImageResource(R.drawable.exam_schedule_3);
                    holder.img2.setImageResource(R.drawable.exam_schedule_3_selected);
                } else {
                    holder.img.setImageResource(R.drawable.exam_schedule_2);
                    holder.img2.setImageResource(R.drawable.exam_schedule_2_selected);
                }
            }
            if (position == 0) {
                holder.line1.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.GONE);
            } else if (position == mListData.size() - 1) {
                holder.line1.setVisibility(View.GONE);
                holder.line2.setVisibility(View.VISIBLE);
            } else {
                holder.line1.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
            }

            String exam_stage_name = dataBean.getPREVIEW_STAGE_NAME();
            if (!TextUtils.isEmpty(exam_stage_name)) {
                holder.title.setText(exam_stage_name);
            }
            String exam_stage_end_date = dataBean.getPREVIEW_STAGE_END_DATE();
            if (!TextUtils.isEmpty(exam_stage_end_date)) {
                holder.time.setText(exam_stage_end_date);
            }

            return convertView;
        }

        class ViewHolder {
            ImageView img;
            ImageView img2;
            TextView  title;
            TextView  time;
            View      line1;
            View      line2;
        }
    }
}
